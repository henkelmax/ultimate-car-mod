package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.BlockGasStation;
import de.maxhenkel.car.blocks.BlockGasStationTop;
import de.maxhenkel.car.blocks.BlockOrientableHorizontal;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.net.MessageStartFuel;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.car.sounds.SoundLoopTileentity;
import de.maxhenkel.car.sounds.SoundLoopTileentity.ISoundLoopable;
import de.maxhenkel.corelib.CachedValue;
import de.maxhenkel.corelib.item.ItemUtils;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import java.util.*;

public class TileEntityGasStation extends TileEntityBase implements ITickableTileEntity, IFluidHandler, ISoundLoopable {

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
        super(Main.GAS_STATION_TILE_ENTITY_TYPE);
        this.transferRate = Main.SERVER_CONFIG.gasStationTransferRate.get();
        this.fuelCounter = 0;
        this.inventory = new Inventory(27);
        this.trading = new Inventory(2);
        this.owner = new UUID(0L, 0L);
        this.storage = FluidStack.EMPTY;
    }

    public final IIntArray FIELDS = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return fuelCounter;
                case 1:
                    if (!storage.isEmpty()) {
                        return storage.getAmount();
                    }
                    return 0;
                case 2:
                    return tradeAmount;
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    fuelCounter = value;
                    break;
                case 1:
                    if (!storage.isEmpty()) {
                        storage.setAmount(value);
                    }
                    break;
                case 2:
                    tradeAmount = value;
                    setChanged();
                    break;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };

    @Override
    public ITextComponent getTranslatedName() {
        return new TranslationTextComponent("block.car.fuel_station");
    }

    private void fixTop() {
        BlockState top = level.getBlockState(worldPosition.above());
        BlockState bottom = level.getBlockState(worldPosition);
        Direction facing = bottom.getValue(BlockOrientableHorizontal.FACING);
        if (top.getBlock().equals(ModBlocks.FUEL_STATION_TOP)) {
            if (!top.getValue(BlockGasStationTop.FACING).equals(facing)) {
                level.setBlockAndUpdate(worldPosition.above(), ModBlocks.FUEL_STATION_TOP.defaultBlockState().setValue(BlockGasStationTop.FACING, facing));
            }
        } else if (level.isEmptyBlock(worldPosition.above())) {
            level.setBlockAndUpdate(worldPosition.above(), ModBlocks.FUEL_STATION_TOP.defaultBlockState().setValue(BlockGasStationTop.FACING, facing));
        }

    }

    @Override
    public void tick() {
        if (level.getGameTime() % 100 == 0) {
            fixTop();
        }

        IFluidHandler handler = getFluidHandlerInFront();

        if (handler == null) {
            if (fuelCounter > 0 || isFueling) {
                fuelCounter = 0;
                isFueling = false;
                synchronize();
                setChanged();
            }
            return;
        }

        if (!isFueling) {
            return;
        }

        if (storage.isEmpty()) {
            return;
        }

        FluidStack s = FluidUtil.tryFluidTransfer(handler, this, transferRate, false);
        int amountCarCanTake = 0;
        if (!s.isEmpty()) {
            amountCarCanTake = s.getAmount();
        }

        if (amountCarCanTake <= 0) {
            return;
        }

        if (freeAmountLeft <= 0) {
            if (tradeAmount <= 0) {
                freeAmountLeft = transferRate;
                setChanged();
            } else if (removeTradeItem()) {
                freeAmountLeft = tradeAmount;
                setChanged();
            } else {
                isFueling = false;
                synchronize();
                return;
            }
        }

        FluidStack result = FluidUtil.tryFluidTransfer(handler, this, Math.min(transferRate, freeAmountLeft), true);

        if (!result.isEmpty()) {
            fuelCounter += result.getAmount();
            freeAmountLeft -= result.getAmount();
            synchronize(100);

            setChanged();
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
        ItemStack tradeTemplate = trading.getItem(0);
        ItemStack tradingStack = trading.getItem(1);

        if (tradeTemplate.isEmpty()) {
            return true;
        }

        if (tradingStack.isEmpty()) {
            return false;
        }

        if (!tradeTemplate.getItem().equals(tradingStack.getItem())) {
            return false;
        }

        if (tradeTemplate.getDamageValue() != tradingStack.getDamageValue()) {
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
        trading.setItem(1, tradingStack);

        return true;
    }

    public IInventory getInventory() {
        return inventory;
    }

    public IInventory getTradingInventory() {
        return trading;
    }

    public boolean isValidFluid(Fluid f) {
        return Main.SERVER_CONFIG.gasStationValidFuelList.stream().anyMatch(f::is);
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
        setChanged();
    }

    public void setOwner(PlayerEntity player) {
        this.owner = new UUID(player.getUUID().getMostSignificantBits(), player.getUUID().getLeastSignificantBits());
        setChanged();
    }

    /**
     * OPs are also owners
     */
    public boolean isOwner(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            ServerPlayerEntity p = (ServerPlayerEntity) player;

            boolean isOp = p.hasPermissions(p.server.getOperatorUserPermissionLevel());
            if (isOp) {
                return true;
            }
        }
        return player.getUUID().equals(owner);
    }

    public boolean hasTrade() {
        return !trading.getItem(0).isEmpty();
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.putInt("counter", fuelCounter);

        if (!storage.isEmpty()) {
            CompoundNBT comp = new CompoundNBT();
            storage.writeToNBT(comp);
            compound.put("fluid", comp);
        }

        ItemUtils.saveInventory(compound, "inventory", inventory);

        ItemUtils.saveInventory(compound, "trading", trading);

        compound.putInt("trade_amount", tradeAmount);
        compound.putInt("free_amount", freeAmountLeft);

        compound.putUUID("owner", owner);

        return super.save(compound);
    }

    @Override
    public void load(BlockState blockState, CompoundNBT compound) {
        fuelCounter = compound.getInt("counter");

        if (compound.contains("fluid")) {
            CompoundNBT comp = compound.getCompound("fluid");
            storage = FluidStack.loadFluidStackFromNBT(comp);
        }

        ItemUtils.readInventory(compound, "inventory", inventory);
        ItemUtils.readInventory(compound, "trading", trading);

        tradeAmount = compound.getInt("trade_amount");
        freeAmountLeft = compound.getInt("free_amount");

        if (compound.contains("owner")) {
            owner = compound.getUUID("owner");
        } else {
            owner = new UUID(0L, 0L);
        }
        super.load(blockState, compound);
    }

    public boolean isFueling() {
        return isFueling;
    }

    public int getFuelCounter() {
        return this.fuelCounter;
    }

    public void setStorage(FluidStack storage) {
        this.storage = storage;
        setChanged();
        synchronize();
    }

    public FluidStack getStorage() {
        return storage;
    }

    public void setFuelCounter(int fuelCounter) {
        this.fuelCounter = fuelCounter;
        setChanged();
        synchronize();
    }

    public void setFueling(boolean isFueling) {
        if (getFluidHandlerInFront() == null) {
            return;
        }

        if (isFueling && !this.isFueling) {
            if (level.isClientSide) {
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

    private CachedValue<Vector3d> center = new CachedValue<>(() -> new Vector3d(worldPosition.getX() + 0.5D, worldPosition.getY() + 1.5D, worldPosition.getZ() + 0.5D));

    public IFluidHandler getFluidHandlerInFront() {
        return level.getEntitiesOfClass(Entity.class, getDetectionBox())
                .stream()
                .sorted(Comparator.comparingDouble(o -> o.distanceToSqr(center.get())))
                .map(entity -> entity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(null))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private CachedValue<AxisAlignedBB> detectionBox = new CachedValue<>(this::createDetectionBox);

    private AxisAlignedBB createDetectionBox() {
        BlockState ownState = level.getBlockState(worldPosition);

        if (!ownState.getBlock().equals(ModBlocks.GAS_STATION)) {
            return null;
        }
        Direction facing = ownState.getValue(BlockGasStation.FACING);
        BlockPos start = worldPosition.relative(facing);
        return new AxisAlignedBB(start.getX(), start.getY(), start.getZ(), start.getX() + 1D, start.getY() + 2.5D, start.getZ() + 1D)
                .expandTowards(facing.getStepX(), 0D, facing.getStepZ())
                .inflate(facing.getStepX() == 0 ? 0.5D : 0D, 0D, facing.getStepZ() == 0 ? 0.5D : 0D);
    }

    public AxisAlignedBB getDetectionBox() {
        return detectionBox.get();
    }

    public boolean canEntityBeFueled() {
        IFluidHandler handler = getFluidHandlerInFront();
        if (handler == null) {
            return false;
        }
        FluidStack result = FluidUtil.tryFluidTransfer(handler, this, transferRate, false);
        return !result.isEmpty();
    }

    public BlockState getBlockState() {
        BlockState ownState = level.getBlockState(worldPosition);

        if (!ownState.getBlock().equals(ModBlocks.GAS_STATION)) {
            return null;
        }
        return ownState;
    }

    public Direction getDirection() {
        BlockState state = getBlockState();
        if (state == null) {
            return Direction.NORTH;
        }

        return state.getValue(BlockGasStation.FACING);
    }

    public void sendStartFuelPacket(boolean start) {
        if (level.isClientSide) {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageStartFuel(worldPosition, start));
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
        ModSounds.playSoundLoop(new SoundLoopTileentity(ModSounds.GAS_STATION, SoundCategory.BLOCKS, this), level);
    }

    @Override
    public void play() {

    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(worldPosition, worldPosition.offset(1, 2, 1));
    }

    public int getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(int tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public int getFuelAmount() {
        if (!storage.isEmpty()) {
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

        if (storage.isEmpty()) {
            int amount = Math.min(resource.getAmount(), maxStorageAmount);

            if (action.execute()) {
                storage = new FluidStack(resource.getFluid(), amount);
                synchronize();
                setChanged();
            }

            return amount;
        } else if (resource.getFluid().equals(storage.getFluid())) {
            int amount = Math.min(resource.getAmount(), maxStorageAmount - storage.getAmount());

            if (action.execute()) {
                storage.setAmount(storage.getAmount() + amount);
                setChanged();
            }

            return amount;
        }
        return 0;
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (storage.isEmpty()) {
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

                setChanged();
            }

            return new FluidStack(f, amount);
        }

        return FluidStack.EMPTY;
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if (storage.isEmpty()) {
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

            setChanged();
        }

        return new FluidStack(f, amount);
    }
}
