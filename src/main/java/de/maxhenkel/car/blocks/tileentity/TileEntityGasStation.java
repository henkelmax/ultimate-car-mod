package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.BlockGasStation;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.net.MessageStartFuel;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.car.sounds.SoundLoopTileentity;
import de.maxhenkel.car.sounds.SoundLoopTileentity.ISoundLoopable;
import de.maxhenkel.tools.FluidUtils;
import de.maxhenkel.tools.ItemTools;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class TileEntityGasStation extends TileEntityBase implements ITickableTileEntity, IFluidHandler, ISoundLoopable, IInventory {

    private FluidStack storage;

    public int maxStorageAmount = 16000;

    private final int transferRate;

    private int fuelCounter;
    private boolean isFueling;
    private boolean wasFueling;

    private Inventory inventory;
    private Inventory trading;
    private int tradeAmount;

    private int freeAmountLeft;

    private UUID owner;

    public TileEntityGasStation() {
        super(Main.FUEL_STATION_TILE_ENTITY_TYPE);
        this.transferRate = Config.gasStationTransferRate.get();
        this.fuelCounter = 0;
        this.inventory = new Inventory(27);
        this.trading = new Inventory(2);
        this.owner = new UUID(0L, 0L);
        this.storage = FluidStack.EMPTY;
    }

    public final IIntArray FIELDS = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case 0:
                    return fuelCounter;
                case 1:
                    if (!FluidUtils.isEmpty(storage)) {
                        return storage.getAmount();
                    }
                    return 0;
                case 2:
                    return tradeAmount;
            }
            return 0;
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    fuelCounter = value;
                    break;
                case 1:
                    if (!FluidUtils.isEmpty(storage)) {
                        storage.setAmount(value);
                    }
                    break;
                case 2:
                    tradeAmount = value;
                    markDirty();
                    break;
            }
        }

        public int size() {
            return 3;
        }
    };

    @Override
    public ITextComponent getTranslatedName() {
        return new TranslationTextComponent("block.car.fuel_station");
    }

    private void fixTop() {
        BlockState top = world.getBlockState(pos.up());
        BlockState bottom = world.getBlockState(pos);
        Direction facing = bottom.get(ModBlocks.FUEL_STATION.FACING);
        if (top.getBlock().equals(ModBlocks.FUEL_STATION_TOP)) {
            if (!top.get(ModBlocks.FUEL_STATION_TOP.FACING).equals(facing)) {
                world.setBlockState(pos.up(), ModBlocks.FUEL_STATION_TOP.getDefaultState().with(ModBlocks.FUEL_STATION_TOP.FACING, facing));
            }
        } else if (world.isAirBlock(pos.up())) {
            world.setBlockState(pos.up(), ModBlocks.FUEL_STATION_TOP.getDefaultState().with(ModBlocks.FUEL_STATION_TOP.FACING, facing));
        }

    }

    @Override
    public void tick() {
        if (world.getGameTime() % 100 == 0) {
            fixTop();
        }

        IFluidHandler handler = getFluidHandlerInFront();

        if (handler == null) {
            if (fuelCounter > 0 || isFueling) {
                fuelCounter = 0;
                isFueling = false;
                synchronize();
                markDirty();
            }
            return;
        }

        if (!isFueling) {
            return;
        }

        if (FluidUtils.isEmpty(storage)) {
            return;
        }

        FluidStack s = FluidUtil.tryFluidTransfer(handler, this, transferRate, false);
        int amountCarCanTake = 0;
        if (!FluidUtils.isEmpty(s)) {
            amountCarCanTake = s.getAmount();
        }

        if (amountCarCanTake <= 0) {
            return;
        }

        if (freeAmountLeft <= 0) {
            if (tradeAmount <= 0) {
                freeAmountLeft = transferRate;
                markDirty();
            } else if (removeTradeItem()) {
                freeAmountLeft = tradeAmount;
                markDirty();
            } else {
                isFueling = false;
                synchronize();
                return;
            }
        }

        FluidStack result = FluidUtil.tryFluidTransfer(handler, this, Math.min(transferRate, freeAmountLeft), true);

        if (!FluidUtils.isEmpty(result)) {
            fuelCounter += result.getAmount();
            freeAmountLeft -= result.getAmount();
            synchronize(100);

            markDirty();
            if (!wasFueling) {
                synchronize();
            }
            wasFueling = true;
        } else {
            if (wasFueling) {
                synchronize();
            }
            wasFueling = false;
        }
    }

    /**
     * @return true if the item was successfully removed
     */
    public boolean removeTradeItem() {
        ItemStack tradeTemplate = trading.getStackInSlot(0);
        ItemStack tradingStack = trading.getStackInSlot(1);

        if (tradeTemplate.isEmpty()) {
            return true;
        }

        if (tradingStack.isEmpty()) {
            return false;
        }

        if (!tradeTemplate.getItem().equals(tradingStack.getItem())) {
            return false;
        }

        if (tradeTemplate.getDamage() != tradingStack.getDamage()) {
            return false;
        }

        if (tradingStack.getCount() < tradeTemplate.getCount()) {
            return false;
        }

        ItemStack addStack = tradingStack.copy();
        addStack.setCount(tradeTemplate.getCount());
        ItemStack add = inventory.addItem(addStack);
        if (add.getCount() > 0) {
            return false;
        }
        tradingStack.setCount(tradingStack.getCount() - tradeTemplate.getCount());
        trading.setInventorySlotContents(1, tradingStack);

        return true;
    }

    public IInventory getInventory() {
        return inventory;
    }

    public IInventory getTradingInventory() {
        return trading;
    }

    public boolean isValidFluid(Fluid f) {
        return Config.gasStationValidFuelList.contains(f);
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
        markDirty();
    }

    public void setOwner(PlayerEntity player) {
        this.owner = new UUID(player.getUniqueID().getMostSignificantBits(), player.getUniqueID().getLeastSignificantBits());
        markDirty();
    }

    /**
     * OPs are also owners
     */
    public boolean isOwner(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            ServerPlayerEntity p = (ServerPlayerEntity) player;

            boolean isOp = p.hasPermissionLevel(p.server.getOpPermissionLevel());
            if (isOp) {
                return true;
            }
        }
        return player.getUniqueID().equals(owner);
    }

    public boolean hasTrade() {
        return !trading.getStackInSlot(0).isEmpty();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("counter", fuelCounter);

        if (!FluidUtils.isEmpty(storage)) {
            CompoundNBT comp = new CompoundNBT();
            storage.writeToNBT(comp);
            compound.put("fluid", comp);
        }

        ItemTools.saveInventory(compound, "inventory", inventory);

        ItemTools.saveInventory(compound, "trading", trading);

        compound.putInt("trade_amount", tradeAmount);
        compound.putInt("free_amount", freeAmountLeft);

        compound.putUniqueId("owner", owner);

        return super.write(compound);
    }

    @Override
    public void func_230337_a_(BlockState blockState, CompoundNBT compound) {
        fuelCounter = compound.getInt("counter");

        if (compound.contains("fluid")) {
            CompoundNBT comp = compound.getCompound("fluid");
            storage = FluidStack.loadFluidStackFromNBT(comp);
        }

        ItemTools.readInventory(compound, "inventory", inventory);
        ItemTools.readInventory(compound, "trading", trading);

        tradeAmount = compound.getInt("trade_amount");
        freeAmountLeft = compound.getInt("free_amount");

        owner = compound.getUniqueId("owner");
        super.func_230337_a_(blockState, compound);
    }

    public boolean isFueling() {
        return isFueling;
    }

    public int getFuelCounter() {
        return this.fuelCounter;
    }

    public void setStorage(FluidStack storage) {
        this.storage = storage;
        markDirty();
        synchronize();
    }

    public FluidStack getStorage() {
        return storage;
    }

    public void setFuelCounter(int fuelCounter) {
        this.fuelCounter = fuelCounter;
        markDirty();
        synchronize();
    }

    public void setFueling(boolean isFueling) {
        if (getFluidHandlerInFront() == null) {
            return;
        }

        if (isFueling && !this.isFueling) {
            if (world.isRemote) {
                playSound();
            }
        }
        this.isFueling = isFueling;
        synchronize();
    }

    public String getRenderText() {
        IFluidHandler fluidHandler = getFluidHandlerInFront();
        if (fluidHandler == null) {
            return new TranslationTextComponent("gas_station.no_car").getString();
        } else if (fuelCounter <= 0) {
            return new TranslationTextComponent("gas_station.ready").getString();
        } else {
            return new TranslationTextComponent("gas_station.fuel_amount", fuelCounter).getString();
        }
    }

    public IFluidHandler getFluidHandlerInFront() {
        BlockState ownState = world.getBlockState(getPos());

        if (!ownState.getBlock().equals(ModBlocks.FUEL_STATION)) {
            return null;
        }

        Direction facing = ownState.get(BlockGasStation.FACING);

        BlockPos start = getPos().offset(facing);

        AxisAlignedBB aabb = new AxisAlignedBB(start.getX(), start.getY(), start.getZ(), start.getX() + 1D,
                start.getY() + 2.5D, start.getZ() + 1D).expand(facing.getXOffset(), 0D, facing.getZOffset());

        List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, aabb);

        if (entities.isEmpty()) {
            return null;
        }

        return entities.get(0).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(null);
    }

    public boolean canEntityBeFueled() {
        IFluidHandler handler = getFluidHandlerInFront();
        if (handler == null) {
            return false;
        }
        FluidStack result = FluidUtil.tryFluidTransfer(handler, this, transferRate, false);
        if (FluidUtils.isEmpty(result) || result.getAmount() <= 0) {
            return false;
        }
        return true;
    }

    public BlockState getBlockState() {
        BlockState ownState = world.getBlockState(getPos());

        if (!ownState.getBlock().equals(ModBlocks.FUEL_STATION)) {
            return null;
        }
        return ownState;
    }

    public Direction getDirection() {
        BlockState state = getBlockState();
        if (state == null) {
            return Direction.NORTH;
        }

        return state.get(BlockGasStation.FACING);
    }

    public void sendStartFuelPacket(boolean start) {
        if (world.isRemote) {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageStartFuel(pos, start));
        }
    }

    @Override
    public boolean shouldSoundBePlayed() {
        if (!isFueling) {
            return false;
        }

        return canEntityBeFueled();
    }

    @OnlyIn(Dist.CLIENT)
    public void playSound() {
        ModSounds.playSoundLoop(new SoundLoopTileentity(ModSounds.GAS_STATION, SoundCategory.BLOCKS, this), world);
    }

    @Override
    public void play() {

    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(pos, pos.add(1, 2, 1));
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {

    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void openInventory(PlayerEntity player) {

    }

    @Override
    public void closeInventory(PlayerEntity player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }

    @Override
    public void clear() {

    }

    public int getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(int tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public int getFuelAmount() {
        if (!FluidUtils.isEmpty(storage)) {
            return storage.getAmount();
        }
        return 0;
    }

    @Override
    public IIntArray getFields() {
        return FIELDS;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        return storage;
    }

    @Override
    public int getTankCapacity(int tank) {
        return maxStorageAmount;
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        return isValidFluid(stack.getFluid());
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (!isValidFluid(resource.getFluid())) {
            return 0;
        }

        if (FluidUtils.isEmpty(storage)) {
            int amount = Math.min(resource.getAmount(), maxStorageAmount);

            if (action.execute()) {
                storage = new FluidStack(resource.getFluid(), amount);
                synchronize();
                markDirty();
            }

            return amount;
        } else if (resource.getFluid().equals(storage.getFluid())) {
            int amount = Math.min(resource.getAmount(), maxStorageAmount - storage.getAmount());

            if (action.execute()) {
                storage.setAmount(storage.getAmount() + amount);
                markDirty();
            }

            return amount;
        }
        return 0;
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (FluidUtils.isEmpty(storage)) {
            return FluidStack.EMPTY;
        }

        if (storage.getFluid().equals(resource.getFluid())) {
            int amount = Math.min(resource.getAmount(), storage.getAmount());

            Fluid f = storage.getFluid();

            if (action.execute()) {
                storage.setAmount(storage.getAmount() - amount);
                if (storage.getAmount() <= 0) {
                    storage = FluidStack.EMPTY;
                    synchronize();
                }

                markDirty();
            }

            return new FluidStack(f, amount);
        }

        return FluidStack.EMPTY;
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if (FluidUtils.isEmpty(storage)) {
            return FluidStack.EMPTY;
        }

        int amount = Math.min(maxDrain, storage.getAmount());

        Fluid f = storage.getFluid();

        if (action.execute()) {
            storage.setAmount(storage.getAmount() - amount);
            if (storage.getAmount() <= 0) {
                storage = FluidStack.EMPTY;
                synchronize();
            }

            markDirty();
        }

        return new FluidStack(f, amount);
    }
}
