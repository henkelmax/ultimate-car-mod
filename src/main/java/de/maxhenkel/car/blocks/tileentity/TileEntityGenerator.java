package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.BlockGui;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.corelib.energy.EnergyUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;

public class TileEntityGenerator extends TileEntityBase implements ITickableTileEntity, IFluidHandler, IEnergyStorage, IInventory {

    public final int maxStorage;
    public int storedEnergy;

    public final int maxMillibuckets;
    protected int currentMillibuckets;
    protected final int energyGeneration;

    protected Fluid currentFluid;

    protected Inventory inventory;

    public TileEntityGenerator() {
        super(Main.GENERATOR_TILE_ENTITY_TYPE);
        this.inventory = new Inventory(0);
        this.maxStorage = Main.SERVER_CONFIG.generatorEnergyStorage.get();
        this.storedEnergy = 0;
        this.maxMillibuckets = Main.SERVER_CONFIG.generatorFluidStorage.get();
        this.currentMillibuckets = 0;
        this.energyGeneration = Main.SERVER_CONFIG.generatorEnergyGeneration.get();
    }

    public final IIntArray FIELDS = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case 0:
                    return storedEnergy;
                case 1:
                    return currentMillibuckets;
            }
            return 0;
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    storedEnergy = value;
                    break;
                case 1:
                    currentMillibuckets = value;
                    break;
            }
        }

        public int size() {
            return 2;
        }
    };

    @Override
    public void tick() {

        if (world.isRemote) {
            return;
        }

        if (isEnabled()) {
            setBlockEnabled(true);
        } else {
            setBlockEnabled(false);
        }

        if (currentFluid != null && currentMillibuckets > 0
                && (storedEnergy + energyGeneration) <= maxStorage) {
            currentMillibuckets--;
            storedEnergy += energyGeneration;

            if (currentMillibuckets <= 0) {
                currentMillibuckets = 0;
                currentFluid = null;
            }
        }

        if (currentMillibuckets <= 0 && currentFluid != null) {
            currentMillibuckets = 0;
            currentFluid = null;
        }

        if (currentFluid == null && currentMillibuckets > 0) {
            currentMillibuckets = 0;
        }

        handlePushEnergy();
        markDirty();
    }

    public boolean isValidFuel(Fluid f) {
        return ModFluids.BIO_DIESEL.equals(f);
    }

    private void handlePushEnergy() {
        for (Direction side : Direction.values()) {
            IEnergyStorage storage = EnergyUtils.getEnergyStorageOffset(world, pos, side);
            if (storage == null) {
                continue;
            }

            EnergyUtils.pushEnergy(this, storage, storedEnergy);
        }
    }

    public boolean isEnabled() {
        int fuelGen = energyGeneration;
        if (currentMillibuckets > 0 && storedEnergy + fuelGen < maxStorage) {
            return true;
        }
        return false;
    }

    public void setBlockEnabled(boolean enabled) {
        BlockState state = world.getBlockState(getPos());
        if (state.getBlock().equals(ModBlocks.GENERATOR)) {
            if (state.get(BlockGui.POWERED) != enabled) {
                ModBlocks.GENERATOR.setPowered(world, pos, state, enabled);
            }
        }
    }

    @Override
    public ITextComponent getTranslatedName() {
        return new TranslationTextComponent("block.car.generator");
    }


    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("stored_energy", storedEnergy);
        if (currentFluid != null) {
            FluidStack stack = new FluidStack(currentFluid, currentMillibuckets);
            CompoundNBT comp = new CompoundNBT();
            stack.writeToNBT(comp);
            compound.put("fluid", comp);
        }
        return super.write(compound);
    }

    @Override
    public void func_230337_a_(BlockState blockState, CompoundNBT compound) {
        storedEnergy = compound.getInt("stored_energy");
        if (compound.contains("fluid")) {
            FluidStack stack = FluidStack.loadFluidStackFromNBT(compound.getCompound("fluid"));
            currentFluid = stack.getFluid();
            currentMillibuckets = stack.getAmount();
        }
        super.func_230337_a_(blockState, compound);
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
    public boolean isEmpty() {
        return inventory.isEmpty();
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
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int i = Math.min(maxExtract, storedEnergy);

        if (!simulate) {
            storedEnergy -= i;
            markDirty();
        }

        return i;
    }

    @Override
    public int getEnergyStored() {
        return storedEnergy;
    }

    @Override
    public int getMaxEnergyStored() {
        return maxStorage;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return false;
    }


    public int getStoredEnergy() {
        return storedEnergy;
    }

    public int getCurrentMillibuckets() {
        return currentMillibuckets;
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
        if (currentFluid == null) {
            return FluidStack.EMPTY;
        }
        return new FluidStack(currentFluid, currentMillibuckets);
    }

    @Override
    public int getTankCapacity(int tank) {
        return maxMillibuckets;
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        if (isValidFuel(stack.getFluid()) && (currentFluid == null || currentFluid.equals(stack.getFluid()))) {
            return true;
        }
        return false;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if ((currentFluid == null && isValidFuel(resource.getFluid()))
                || resource.getFluid().equals(currentFluid)) {
            int amount = Math.min(maxMillibuckets - currentMillibuckets, resource.getAmount());
            if (action.execute()) {
                currentMillibuckets += amount;
                if (currentFluid == null) {
                    currentFluid = resource.getFluid();
                }
                markDirty();
            }
            return amount;
        }

        return 0;
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        return FluidStack.EMPTY;
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return FluidStack.EMPTY;
    }

}
