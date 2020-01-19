package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.blocks.BlockGui;
import de.maxhenkel.car.recipes.EnergyFluidProducerRecipe;
import de.maxhenkel.tools.ItemTools;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;

public abstract class TileEntityEnergyFluidProducer extends TileEntityBase implements IEnergyStorage, ISidedInventory, ITickableTileEntity, IFluidHandler {

    protected IRecipeType<? extends EnergyFluidProducerRecipe> recipeType;
    protected Inventory inventory;

    protected int maxEnergy;
    protected int storedEnergy;

    protected int time;

    protected int fluidAmount;
    protected int currentMillibuckets;

    public TileEntityEnergyFluidProducer(TileEntityType<?> tileEntityTypeIn, IRecipeType<? extends EnergyFluidProducerRecipe> recipeType) {
        super(tileEntityTypeIn);
        this.recipeType = recipeType;
        this.inventory = new Inventory(2);
        this.maxEnergy = 10000;
        this.storedEnergy = 0;
        this.time = 0;
        this.fluidAmount = 3000;
        this.currentMillibuckets = 0;
    }

    public final IIntArray FIELDS = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case 0:
                    return time;
                case 1:
                    return storedEnergy;
                case 2:
                    return currentMillibuckets;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    time = value;
                    break;
                case 1:
                    storedEnergy = value;
                    break;
                case 2:
                    currentMillibuckets = value;
                    break;
            }
        }

        public int size() {
            return 3;
        }
    };

    public EnergyFluidProducerRecipe getRecipe() {
        return world.getRecipeManager().getRecipe(recipeType, this, world).orElse(null);
    }

    @Override
    public void tick() {
        if (world.isRemote) {
            return;
        }

        ItemStack input = inventory.getStackInSlot(0);
        ItemStack output = inventory.getStackInSlot(1);

        EnergyFluidProducerRecipe recipe = getRecipe();

        if (recipe == null) {
            time = 0;
            markDirty();
            setBlockEnabled(false);
            return;
        }

        if (storedEnergy < recipe.getEnergy()) {
            setBlockEnabled(false);
            return;
        }

        if (input.isEmpty()) {
            time = 0;
            markDirty();
            setBlockEnabled(false);
            return;
        }

        if (!(output.isEmpty() || (ItemStack.areItemsEqual(output, recipe.getRecipeOutput()) && output.getCount() + recipe.getRecipeOutput().getCount() <= output.getMaxStackSize()))) {
            time = 0;
            markDirty();
            setBlockEnabled(false);
            return;
        }

        if (currentMillibuckets + recipe.getFluidAmount() > fluidAmount) {
            time = 0;
            markDirty();
            setBlockEnabled(false);
            return;
        }

        time++;
        storedEnergy -= recipe.getEnergy();

        if (time > recipe.getDuration()) {
            time = 0;

            if (output.isEmpty()) {
                inventory.setInventorySlotContents(1, recipe.getRecipeOutput());
            } else if (output.getCount() < output.getMaxStackSize()) {
                output.grow(recipe.getRecipeOutput().getCount());
            }
            currentMillibuckets += recipe.getFluidAmount();
            input.shrink(1);
        }
        markDirty();
        setBlockEnabled(true);
    }

    public abstract BlockGui getOwnBlock();

    public void setBlockEnabled(boolean enabled) {
        BlockState state = world.getBlockState(getPos());
        if (state.getBlock().equals(getOwnBlock())) {
            getOwnBlock().setPowered(world, pos, state, enabled);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("energy_stored", storedEnergy);
        compound.putInt("time", time);
        compound.putInt("fluid_stored", currentMillibuckets);

        ItemTools.saveInventory(compound, "slots", inventory);

        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        storedEnergy = compound.getInt("energy_stored");
        time = compound.getInt("time");
        currentMillibuckets = compound.getInt("fluid_stored");

        ItemTools.readInventory(compound, "slots", inventory);

        super.read(compound);
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return inventory.decrStackSize(index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return inventory.removeStackFromSlot(index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory.setInventorySlotContents(index, stack);
    }

    @Override
    public int getInventoryStackLimit() {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return inventory.isUsableByPlayer(player);
    }

    @Override
    public void openInventory(PlayerEntity player) {
        inventory.openInventory(player);
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        inventory.closeInventory(player);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return inventory.isItemValidForSlot(index, stack);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[]{0, 1};
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction) {
        if (index == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        if (index == 1) {
            return true;
        }
        return false;
    }


    public Inventory getInventory() {
        return inventory;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getStoredEnergy() {
        return storedEnergy;
    }

    public int getTimeToGenerate() {
        EnergyFluidProducerRecipe recipe = getRecipe();
        if (recipe == null) {
            return 0;
        }
        return recipe.getDuration();
    }

    public int getGeneratingTime() {
        return time;
    }

    public int getFluidAmount() {
        return fluidAmount;
    }

    public int getCurrentMillibuckets() {
        return currentMillibuckets;
    }

    public abstract Fluid getProducingFluid();

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyNeeded = maxEnergy - storedEnergy;

        if (!simulate) {
            storedEnergy += Math.min(energyNeeded, maxReceive);
            markDirty();
        }

        return Math.min(energyNeeded, maxReceive);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return storedEnergy;
    }

    @Override
    public int getMaxEnergyStored() {
        return maxEnergy;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return true;
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
        return new FluidStack(getProducingFluid(), currentMillibuckets);
    }

    @Override
    public int getTankCapacity(int tank) {
        return fluidAmount;
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        return stack.getFluid().equals(getProducingFluid());
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return 0;
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        int amount = Math.min(resource.getAmount(), currentMillibuckets);

        if (action.execute()) {
            currentMillibuckets -= amount;
            markDirty();
        }

        return new FluidStack(getProducingFluid(), amount);
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        int amount = Math.min(maxDrain, currentMillibuckets);

        if (action.execute()) {
            currentMillibuckets -= amount;
            markDirty();
        }

        return new FluidStack(getProducingFluid(), amount);
    }
}
