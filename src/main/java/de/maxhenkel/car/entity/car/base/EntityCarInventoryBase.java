package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.blocks.BlockTank;
import de.maxhenkel.tools.ItemTools;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.gui.GuiHandler;
import de.maxhenkel.car.items.ItemCanister;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import javax.annotation.Nullable;

public abstract class EntityCarInventoryBase extends EntityCarFuelBase implements IInventory {

    protected IInventory internalInventory;
    protected IInventory externalInventory;
    protected IInventory partInventory;

    protected FluidStack fluidInventory;

    public EntityCarInventoryBase(World worldIn) {
        super(worldIn);

        this.internalInventory = new InventoryBasic(getCarName().getUnformattedText(), false, 27);
        this.externalInventory = new InventoryBasic(getCarName().getUnformattedText(), false, 0);
        this.partInventory = new InventoryBasic(getCarName().getUnformattedText(), false, 15);
        this.fluidInventory = null;
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (canPlayerAccessInventoryExternal(player) && player.isSneaking()) {
            //Canister
            ItemStack stack = player.getHeldItem(hand);
            if (!ItemTools.isStackEmpty(stack)) {
                if (stack.getItem() instanceof ItemCanister) {
                    boolean success = ItemCanister.fillCanister(stack, this);

                    if (success) {
                        ModSounds.playSound(SoundEvents.BLOCK_BREWING_STAND_BREW, world, getPosition(), null, SoundCategory.BLOCKS);
                    }
                    return true;
                }
                if(getFluidInventorySize()>0){
                    IFluidHandler handler = FluidUtil.getFluidHandler(stack);
                    if (handler != null) {
                        FluidStack fluidStack = FluidUtil.getFluidContained(stack);

                        if (fluidStack != null) {
                            if(handleEmpty(stack, getInventoryFluidHandler(), player, hand)){
                                return true;
                            }
                        }

                        if(handleFill(stack, getInventoryFluidHandler(), player, hand)){
                            return true;
                        }
                    }
                }
            }

            //Inv
            if (externalInventory.getSizeInventory() <= 0) {
                player.displayGUIChest(internalInventory);
            } else {
                player.displayGUIChest(externalInventory);
            }

            return true;
        }
        return super.processInitialInteract(player, hand);
    }

    public static boolean handleEmpty(ItemStack stack, IFluidHandler handler, EntityPlayer playerIn, EnumHand hand) {
        IItemHandler inv = new InvWrapper(playerIn.inventory);

        FluidActionResult res = FluidUtil.tryEmptyContainerAndStow(stack, handler, inv, Integer.MAX_VALUE, playerIn, true);

        if (res.isSuccess()) {
            playerIn.setHeldItem(hand, res.result);
            return true;
        }

        return false;
    }

    public static boolean handleFill(ItemStack stack, IFluidHandler handler, EntityPlayer playerIn, EnumHand hand) {
        IItemHandler inv = new InvWrapper(playerIn.inventory);

        FluidActionResult result = FluidUtil.tryFillContainerAndStow(stack, handler, inv, Integer.MAX_VALUE,
                playerIn, true);

        if (result.isSuccess()) {
            playerIn.setHeldItem(hand, result.result);
            return true;
        }

        return false;
    }

    public abstract int getFluidInventorySize();

    public boolean canPlayerAccessInventoryExternal(EntityPlayer player) {
        return true;
    }

    public IInventory getPartInventory() {
        return partInventory;
    }

    @Override
    public void destroyCar(EntityPlayer player, boolean dropParts) {
        super.destroyCar(player, dropParts);

        InventoryHelper.dropInventoryItems(world, this, this);
        InventoryHelper.dropInventoryItems(world, this, externalInventory);
        InventoryHelper.dropInventoryItems(world, this, partInventory);
    }

    @Override
    public void openCarGUi(EntityPlayer player) {
        super.openCarGUi(player);
        if (!world.isRemote) {
            player.openGui(Main.instance(), GuiHandler.GUI_CAR, world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        ItemTools.readInventory(compound, "int_inventory", internalInventory);

        this.externalInventory = new InventoryBasic(getCarName().getUnformattedText(), false, compound.getInteger("external_inventory_size"));
        ItemTools.readInventory(compound, "external_inventory", externalInventory);

        ItemTools.readInventory(compound, "parts", partInventory);

        if (compound.hasKey("fluid_inventory")) {
            fluidInventory = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("fluid_inventory"));
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        ItemTools.saveInventory(compound, "int_inventory", internalInventory);

        compound.setInteger("external_inventory_size", externalInventory.getSizeInventory());
        ItemTools.saveInventory(compound, "external_inventory", externalInventory);

        ItemTools.saveInventory(compound, "parts", partInventory);

        if (fluidInventory != null) {
            compound.setTag("fluid_inventory", fluidInventory.writeToNBT(new NBTTagCompound()));
        }
    }

    public IFluidHandler getInventoryFluidHandler() {
        return inventoryFluidHandler;
    }

    private IFluidHandler inventoryFluidHandler = new IFluidHandler() {
        @Override
        public IFluidTankProperties[] getTankProperties() {
            return new IFluidTankProperties[]{
                    new IFluidTankProperties() {
                        @Nullable
                        @Override
                        public FluidStack getContents() {
                            return fluidInventory;
                        }

                        @Override
                        public int getCapacity() {
                            return getFluidInventorySize();
                        }

                        @Override
                        public boolean canFill() {
                            return true;
                        }

                        @Override
                        public boolean canDrain() {
                            return true;
                        }

                        @Override
                        public boolean canFillFluidType(FluidStack fluidStack) {
                            return true;
                        }

                        @Override
                        public boolean canDrainFluidType(FluidStack fluidStack) {
                            return true;
                        }
                    }
            };
        }

        @Override
        public int fill(FluidStack resource, boolean doFill) {
            if (fluidInventory == null) {
                int amount = Math.min(resource.amount, getFluidInventorySize());

                if (doFill) {
                    fluidInventory = new FluidStack(resource.getFluid(), amount);
                }

                return amount;
            } else if (resource.getFluid().equals(fluidInventory.getFluid())) {
                int amount = Math.min(resource.amount, getFluidInventorySize() - fluidInventory.amount);

                if (doFill) {
                    fluidInventory.amount += amount;
                }

                return amount;
            }
            return 0;
        }

        @Nullable
        @Override
        public FluidStack drain(FluidStack resource, boolean doDrain) {
            if (fluidInventory == null) {
                return null;
            }

            if (fluidInventory.getFluid().equals(resource.getFluid())) {
                int amount = Math.min(resource.amount, fluidInventory.amount);

                Fluid f = fluidInventory.getFluid();

                if (doDrain) {
                    fluidInventory.amount -= amount;
                    if (fluidInventory.amount <= 0) {
                        fluidInventory = null;
                    }
                }

                return new FluidStack(f, amount);
            }

            return null;
        }

        @Nullable
        @Override
        public FluidStack drain(int maxDrain, boolean doDrain) {
            if (fluidInventory == null) {
                return null;
            }

            int amount = Math.min(maxDrain, fluidInventory.amount);

            Fluid f = fluidInventory.getFluid();

            if (doDrain) {
                fluidInventory.amount -= amount;
                if (fluidInventory.amount <= 0) {
                    fluidInventory = null;
                }
            }

            return new FluidStack(f, amount);
        }
    };


    @Override
    public int getSizeInventory() {
        return internalInventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return internalInventory.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return internalInventory.decrStackSize(index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return internalInventory.removeStackFromSlot(index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        internalInventory.setInventorySlotContents(index, stack);
    }

    @Override
    public int getInventoryStackLimit() {
        return internalInventory.getInventoryStackLimit();
    }

    @Override
    public void markDirty() {
        internalInventory.markDirty();
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return internalInventory.isUsableByPlayer(player);
    }

    @Override
    public boolean isEmpty() {
        return internalInventory.isEmpty();
    }

    @Override
    public void openInventory(EntityPlayer player) {
        internalInventory.openInventory(player);
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        internalInventory.closeInventory(player);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return internalInventory.isItemValidForSlot(index, stack);
    }

    @Override
    public int getField(int id) {
        return internalInventory.getField(id);
    }

    @Override
    public void setField(int id, int value) {
        internalInventory.setField(id, value);
    }

    @Override
    public int getFieldCount() {
        return internalInventory.getFieldCount();
    }

    @Override
    public void clear() {
        internalInventory.clear();
    }

}
