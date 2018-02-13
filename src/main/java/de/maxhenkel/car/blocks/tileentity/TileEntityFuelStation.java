package de.maxhenkel.car.blocks.tileentity;

import java.util.List;
import java.util.UUID;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.blocks.BlockFuelStation;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.entity.car.base.EntityCarFuelBase;
import de.maxhenkel.car.net.MessageStartFuel;
import de.maxhenkel.car.proxy.CommonProxy;
import de.maxhenkel.car.registries.FuelStationFluid;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.car.sounds.SoundLoopTileentity;
import de.maxhenkel.car.sounds.SoundLoopTileentity.ISoundLoopable;
import de.maxhenkel.tools.ItemTools;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFuelStation extends TileEntityBase implements ITickable, IFluidHandler, ISoundLoopable, IInventory {

    private FluidStack storage;

    public int maxStorageAmount = 16000;

    private final int transferRate;

    private int fuelCounter;
    private boolean isFueling;
    private boolean wasFueling;

    private InventoryBasic inventory;
    private InventoryBasic trading;
    private int tradeAmount;

    private int freeAmountLeft;

    private UUID owner;

    public TileEntityFuelStation() {
        this.transferRate = Config.fuelStationTransferRate;
        this.fuelCounter = 0;
        this.inventory = new InventoryBasic(new TextComponentTranslation("gui.fuelstation").getFormattedText(), false, 27);
        this.trading = new InventoryBasic(new TextComponentTranslation("gui.fuelstation").getFormattedText(), false, 2);
        this.owner=new UUID(0L, 0L);
    }

    @Override
    public void update() {
        EntityCarFuelBase car = getCarInFront();

        if (car == null) {
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

        if (storage == null) {
            return;
        }

        FluidStack s = FluidUtil.tryFluidTransfer(car, this, transferRate, false);
        int amountCarCanTake = 0;
        if (s != null) {
            amountCarCanTake = s.amount;
        }

        if (amountCarCanTake <= 0) {
            return;
        }

        if (freeAmountLeft <= 0) {
            if (removeTradeItem()) {
                freeAmountLeft = tradeAmount;
                markDirty();
            } else {
                isFueling=false;
                synchronize();
                return;
            }
        }

        FluidStack result = FluidUtil.tryFluidTransfer(car, this, Math.min(transferRate, freeAmountLeft), true);

        if (result != null) {
            fuelCounter += result.amount;
            freeAmountLeft -= result.amount;
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

        if (ItemTools.isStackEmpty(tradeTemplate)) {
            return true;
        }

        if (ItemTools.isStackEmpty(tradingStack)) {
            return false;
        }

        if (!tradeTemplate.getItem().equals(tradingStack.getItem())) {
            return false;
        }

        if (tradeTemplate.getItemDamage() != tradingStack.getItemDamage()) {
            return false;
        }

        if (tradingStack.getCount() < tradeTemplate.getCount()) {
            return false;
        }

        ItemStack addStack=tradingStack.copy();
        addStack.setCount(tradeTemplate.getCount());
        ItemStack add=inventory.addItem(addStack);
        if(add.getCount()>0){
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
        for (FuelStationFluid recipe : FuelStationFluid.REGISTRY) {
            if (recipe.getInput().isValid(f)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return fuelCounter;
            case 1:
                if (storage != null) {
                    return storage.amount;
                }
                return 0;
            case 2:
                return tradeAmount;
        }
        return 0;
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.fuelCounter = value;
                break;
            case 1:
                if (storage != null) {
                    this.storage.amount = value;
                }
                break;
            case 2:
                this.tradeAmount = value;
                markDirty();
                break;
        }
    }

    @Override
    public int getFieldCount() {
        return 3;
    }

    public void setOwner(UUID owner){
        this.owner=owner;
        markDirty();
    }

    public void setOwner(EntityPlayer player){
        this.owner=new UUID(player.getUniqueID().getMostSignificantBits(), player.getUniqueID().getLeastSignificantBits());
        markDirty();
    }

    /**
     * OPs are also owners
     */
    public boolean isOwner(EntityPlayer player){
        if(player instanceof EntityPlayerMP){
            EntityPlayerMP p = (EntityPlayerMP) player;

            boolean isOp = p.canUseCommand(p.mcServer.getOpPermissionLevel(), "op");
            if(isOp){
                return true;
            }
        }
        return player.getUniqueID().equals(owner);
    }

    public boolean hasTrade(){
        return !ItemTools.isStackEmpty(trading.getStackInSlot(0));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("counter", fuelCounter);

        if (storage != null) {
            NBTTagCompound comp = new NBTTagCompound();
            storage.writeToNBT(comp);
            compound.setTag("fluid", comp);
        }

        ItemTools.saveInventory(compound, "inventory", inventory);

        ItemTools.saveInventory(compound, "trading", trading);

        compound.setInteger("trade_amount", tradeAmount);
        compound.setInteger("free_amount", freeAmountLeft);

        compound.setUniqueId("owner", owner);

        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {

        fuelCounter = compound.getInteger("counter");

        if (compound.hasKey("fluid")) {
            NBTTagCompound comp = compound.getCompoundTag("fluid");
            storage = FluidStack.loadFluidStackFromNBT(comp);
        }

        ItemTools.readInventory(compound, "inventory", inventory);
        ItemTools.readInventory(compound, "trading", trading);

        tradeAmount = compound.getInteger("trade_amount");
        freeAmountLeft = compound.getInteger("free_amount");

        owner=compound.getUniqueId("owner");

        super.readFromNBT(compound);
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
        if (getCarInFront() == null) {
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
        EntityCarFuelBase car = getCarInFront();
        if (car == null) {
            return new TextComponentTranslation("fuelstation.no_car").getFormattedText();
        } else if (fuelCounter <= 0) {
            return new TextComponentTranslation("fuelstation.ready").getFormattedText();
        } else {
            return new TextComponentTranslation("fuelstation.fuel_amount", fuelCounter)
                    .getFormattedText();
        }
    }

    public EntityCarFuelBase getCarInFront() {
        IBlockState ownState = world.getBlockState(getPos());

        if (!ownState.getBlock().equals(ModBlocks.FUEL_STATION)) {
            return null;
        }

        EnumFacing facing = ownState.getValue(BlockFuelStation.FACING);

        BlockPos start = getPos().offset(facing);

        AxisAlignedBB aabb = new AxisAlignedBB(start.getX(), start.getY(), start.getZ(), start.getX() + 1,
                start.getY() + 1, start.getZ() + 1);

        List<EntityCarFuelBase> cars = world.getEntitiesWithinAABB(EntityCarFuelBase.class, aabb);
        if (cars.isEmpty()) {
            return null;
        }

        return cars.get(0);
    }

    public boolean canCarBeFueled() {
        EntityCarFuelBase car = getCarInFront();
        if (car == null) {
            return false;
        }
        FluidStack result = FluidUtil.tryFluidTransfer(car, this, transferRate, false);
        if (result == null || result.amount <= 0) {
            return false;
        }
        return true;
    }

    public IBlockState getBlockState() {
        IBlockState ownState = world.getBlockState(getPos());

        if (!ownState.getBlock().equals(ModBlocks.FUEL_STATION)) {
            return null;
        }
        return ownState;
    }

    public EnumFacing getDirection() {
        IBlockState state = getBlockState();
        if (state == null) {
            return EnumFacing.NORTH;
        }

        return state.getValue(BlockFuelStation.FACING);
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[]{new IFluidTankProperties() {

            @Override
            public FluidStack getContents() {
                return storage;
            }

            @Override
            public int getCapacity() {
                return maxStorageAmount;
            }

            @Override
            public boolean canFillFluidType(FluidStack fluidStack) {
                return isValidFluid(fluidStack.getFluid());
            }

            @Override
            public boolean canFill() {
                return true;
            }

            @Override
            public boolean canDrainFluidType(FluidStack fluidStack) {
                return true;
            }

            @Override
            public boolean canDrain() {
                return true;
            }
        }};
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (!isValidFluid(resource.getFluid())) {
            return 0;
        }

        if (storage == null) {
            int amount = Math.min(resource.amount, maxStorageAmount);

            if (doFill) {
                storage = new FluidStack(resource.getFluid(), amount);
                synchronize();
                markDirty();
            }

            return amount;
        } else if (resource.getFluid().equals(storage.getFluid())) {
            int amount = Math.min(resource.amount, maxStorageAmount - storage.amount);

            if (doFill) {
                storage.amount += amount;
                markDirty();
            }

            return amount;
        }
        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {

        if (storage == null) {
            return null;
        }

        if (storage.getFluid().equals(resource.getFluid())) {
            int amount = Math.min(resource.amount, storage.amount);

            Fluid f = storage.getFluid();

            if (doDrain) {
                storage.amount -= amount;
                if (storage.amount <= 0) {
                    storage = null;
                    synchronize();
                }

                markDirty();
            }

            return new FluidStack(f, amount);
        }

        return null;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (storage == null) {
            return null;
        }

        int amount = Math.min(maxDrain, storage.amount);

        Fluid f = storage.getFluid();

        if (doDrain) {
            storage.amount -= amount;
            if (storage.amount <= 0) {
                storage = null;
                synchronize();
            }

            markDirty();
        }

        return new FluidStack(f, amount);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    public void sendStartFuelPacket(boolean start) {
        if (world.isRemote) {
            CommonProxy.simpleNetworkWrapper.sendToServer(new MessageStartFuel(pos, start));
        }
    }

    @Override
    public boolean shouldSoundBePlayed() {
        if (!isFueling) {
            return false;
        }

        return canCarBeFueled();
    }

    @SideOnly(Side.CLIENT)
    public void playSound() {
        ModSounds.playSoundLoop(new SoundLoopTileentity(ModSounds.gas_ststion, SoundCategory.BLOCKS, this), world);
    }

    @Override
    public void play() {

    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean hasCustomName() {
        return false;
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
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }

    @Override
    public void clear() {

    }

}
