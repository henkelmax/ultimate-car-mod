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
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return storedEnergy;
                case 1:
                    return currentMillibuckets;
            }
            return 0;
        }

        @Override
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

        @Override
        public int getCount() {
            return 2;
        }
    };

    @Override
    public void tick() {
        if (level.isClientSide) {
            return;
        }

        setBlockEnabled(isEnabled());

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
        setChanged();
    }

    public boolean isValidFuel(Fluid f) {
        return Main.SERVER_CONFIG.generatorValidFuelList.stream().anyMatch(f::is);
    }

    private void handlePushEnergy() {
        for (Direction side : Direction.values()) {
            IEnergyStorage storage = EnergyUtils.getEnergyStorageOffset(level, worldPosition, side);
            if (storage == null) {
                continue;
            }

            EnergyUtils.pushEnergy(this, storage, storedEnergy);
        }
    }

    public boolean isEnabled() {
        return currentMillibuckets > 0 && storedEnergy + energyGeneration < maxStorage;
    }

    public void setBlockEnabled(boolean enabled) {
        BlockState state = level.getBlockState(worldPosition);
        if (state.getBlock().equals(ModBlocks.GENERATOR)) {
            if (state.getValue(BlockGui.POWERED) != enabled) {
                ModBlocks.GENERATOR.setPowered(level, worldPosition, state, enabled);
            }
        }
    }

    @Override
    public ITextComponent getTranslatedName() {
        return new TranslationTextComponent("block.car.generator");
    }


    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.putInt("stored_energy", storedEnergy);
        if (currentFluid != null) {
            FluidStack stack = new FluidStack(currentFluid, currentMillibuckets);
            CompoundNBT comp = new CompoundNBT();
            stack.writeToNBT(comp);
            compound.put("fluid", comp);
        }
        return super.save(compound);
    }

    @Override
    public void load(BlockState blockState, CompoundNBT compound) {
        storedEnergy = compound.getInt("stored_energy");
        if (compound.contains("fluid")) {
            FluidStack stack = FluidStack.loadFluidStackFromNBT(compound.getCompound("fluid"));
            currentFluid = stack.getFluid();
            currentMillibuckets = stack.getAmount();
        }
        super.load(blockState, compound);
    }

    @Override
    public int getContainerSize() {
        return inventory.getContainerSize();
    }

    @Override
    public ItemStack getItem(int index) {
        return inventory.getItem(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return inventory.removeItem(index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return inventory.removeItemNoUpdate(index);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        inventory.setItem(index, stack);
    }

    @Override
    public int getMaxStackSize() {
        return inventory.getMaxStackSize();
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return inventory.stillValid(player);
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public void startOpen(PlayerEntity player) {
        inventory.startOpen(player);
    }

    @Override
    public void stopOpen(PlayerEntity player) {
        inventory.stopOpen(player);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return inventory.canPlaceItem(index, stack);
    }

    @Override
    public void clearContent() {
        inventory.clearContent();
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
            setChanged();
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
                setChanged();
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
