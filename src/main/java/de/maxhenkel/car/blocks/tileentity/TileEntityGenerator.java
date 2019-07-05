package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.BlockGui;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.tools.EnergyUtil;
import de.maxhenkel.car.registries.GeneratorRecipe;
import de.maxhenkel.tools.FluidStackWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntityGenerator extends TileEntityBase implements ITickableTileEntity, IFluidHandler, IEnergyStorage, IInventory {

    public final int maxStorage;
    public int storedEnergy;

    public final int maxMillibuckets;
    protected int currentMillibuckets;

    protected Fluid currentFluid;

    protected Inventory inventory;

    public TileEntityGenerator() {
        super(Main.GENERATOR_TILE_ENTITY_TYPE);
        this.inventory = new Inventory(0);
        this.maxStorage = Config.generatorEnergyStorage.get();
        this.storedEnergy = 0;
        this.maxMillibuckets = Config.generatorFluidStorage.get();
        this.currentMillibuckets = 0;
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
                && (storedEnergy + getCurrentGenerationFactor()) <= maxStorage) {
            currentMillibuckets--;
            storedEnergy += getCurrentGenerationFactor();

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

    public int getCurrentGenerationFactor(Fluid f) {
        for (GeneratorRecipe recipe : GeneratorRecipe.REGISTRY) {
            if (recipe.getInput().isValid(f)) {
                return recipe.getEnergy();
            }
        }

        return 0;
    }

    public int getCurrentGenerationFactor() {
        return getCurrentGenerationFactor(currentFluid);
    }

    public boolean isValidFuel(Fluid f) {
        return getCurrentGenerationFactor(f) > 0;
    }

    private void handlePushEnergy() {
        for (Direction side : Direction.values()) {
            IEnergyStorage storage = EnergyUtil.getEnergyStorageOffset(world, pos, side);
            if (storage == null) {
                continue;
            }

            EnergyUtil.pushEnergy(this, storage, storedEnergy, side.getOpposite(), side);
        }
    }

    public boolean isEnabled() {
        int fuelGen = getCurrentGenerationFactor();
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
            FluidStack stack = new FluidStackWrapper(currentFluid, currentMillibuckets);
            CompoundNBT comp = new CompoundNBT();
            stack.writeToNBT(comp);
            compound.put("fluid", comp);
        }
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {

        storedEnergy = compound.getInt("stored_energy");

        if (compound.contains("fluid")) {
            FluidStack stack = FluidStackWrapper.loadFluidStackFromNBT(compound.getCompound("fluid"));
            currentFluid = stack.getFluid();
            currentMillibuckets = stack.amount;
        }

        super.read(compound);
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[]{new IFluidTankProperties() {

            @Override
            public FluidStack getContents() {
                if (currentFluid == null) {
                    return null;
                }
                return new FluidStackWrapper(currentFluid, currentMillibuckets);
            }

            @Override
            public int getCapacity() {
                return maxMillibuckets;
            }

            @Override
            public boolean canFillFluidType(FluidStack fluidStack) {
                if (isValidFuel(fluidStack.getFluid())
                        && (currentFluid == null || currentFluid.equals(fluidStack.getFluid()))) {
                    return true;
                }
                return false;
            }

            @Override
            public boolean canFill() {
                return true;
            }

            @Override
            public boolean canDrainFluidType(FluidStack fluidStack) {
                return false;
            }

            @Override
            public boolean canDrain() {
                return false;
            }
        }};
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {

        if ((currentFluid == null && isValidFuel(resource.getFluid()))
                || resource.getFluid().equals(currentFluid)) {
            int amount = Math.min(maxMillibuckets - currentMillibuckets, resource.amount);
            if (doFill) {
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

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return null;
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
}
