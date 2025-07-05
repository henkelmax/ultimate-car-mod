package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.corelib.blockentity.ITickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;

public class TileEntityBackmixReactor extends TileEntityBase implements ITickableBlockEntity, IFluidHandler, IEnergyStorage, Container {

    public final int maxStorage;
    protected int storedEnergy;
    public final int energyUsage;

    public final int methanolUsage;
    public final int maxMethanol;
    protected int currentMethanol;

    public final int canolaUsage;
    public final int maxCanola;
    protected int currentCanola;

    public final int maxMix;
    protected int currentMix;

    public final int mixGeneration;

    public final int generatingTime;
    protected int timeToGenerate;

    public TileEntityBackmixReactor(BlockPos pos, BlockState state) {
        super(CarMod.BACKMIX_REACTOR_TILE_ENTITY_TYPE.get(), pos, state);
        this.maxStorage = CarMod.SERVER_CONFIG.backmixReactorEnergyStorage.get();
        this.storedEnergy = 0;
        this.energyUsage = CarMod.SERVER_CONFIG.backmixReactorEnergyUsage.get();

        this.maxMethanol = CarMod.SERVER_CONFIG.backmixReactorFluidStorage.get();
        this.maxCanola = CarMod.SERVER_CONFIG.backmixReactorFluidStorage.get();
        this.maxMix = CarMod.SERVER_CONFIG.backmixReactorFluidStorage.get();

        this.currentCanola = 0;
        this.currentMethanol = 0;
        this.currentMix = 0;

        this.generatingTime = CarMod.SERVER_CONFIG.backmixReactorGeneratingTime.get();
        this.timeToGenerate = 0;

        this.mixGeneration = CarMod.SERVER_CONFIG.backmixReactorMixGeneration.get();
        this.methanolUsage = CarMod.SERVER_CONFIG.backmixReactorMethanolUsage.get();
        this.canolaUsage = CarMod.SERVER_CONFIG.backmixReactorCanolaUsage.get();
    }

    public final ContainerData FIELDS = new ContainerData() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return storedEnergy;
                case 1:
                    return currentCanola;
                case 2:
                    return currentMethanol;
                case 3:
                    return currentMix;
                case 4:
                    return timeToGenerate;
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
                    currentCanola = value;
                    break;
                case 2:
                    currentMethanol = value;
                    break;
                case 3:
                    currentMix = value;
                    break;
                case 4:
                    timeToGenerate = value;
                    break;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    };

    @Override
    public void tick() {
        if (level.isClientSide) {
            return;
        }

        setBlockEnabled(isEnabled());

        if (timeToGenerate > 0 && storedEnergy >= energyUsage) {
            storedEnergy -= energyUsage;
            timeToGenerate--;


            if (timeToGenerate == 0) {

                if (currentMix + mixGeneration <= maxMix) {
                    currentMix += mixGeneration;
                    currentCanola -= canolaUsage;
                    currentMethanol -= methanolUsage;
                }
            }
        } else if (storedEnergy >= energyUsage) {
            if (currentCanola >= canolaUsage) {
                if (currentMethanol >= methanolUsage) {
                    if (currentMix + mixGeneration <= maxMix) {
                        timeToGenerate = generatingTime;
                    }
                }
            }
        }
        setChanged();
    }

    public boolean isEnabled() {
        if (storedEnergy > 0 && currentMix < maxMix) {
            if (currentMethanol >= methanolUsage) {
                return currentCanola >= canolaUsage;
            }
        }
        return false;
    }

    public void setBlockEnabled(boolean enabled) {
        BlockState state = level.getBlockState(getBlockPos());
        if (state.getBlock().equals(ModBlocks.BACKMIX_REACTOR.get())) {
            ModBlocks.BACKMIX_REACTOR.get().setPowered(level, worldPosition, state, enabled);
        }
    }

    @Override
    public void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);

        valueOutput.putInt("stored_endergy", storedEnergy);
        valueOutput.putInt("canola", currentCanola);
        valueOutput.putInt("methanol", currentMethanol);
        valueOutput.putInt("mix", currentMix);
        valueOutput.putInt("time", timeToGenerate);
    }

    @Override
    public void loadAdditional(ValueInput valueInput) {
        storedEnergy = valueInput.getIntOr("stored_endergy", 0);
        currentCanola = valueInput.getIntOr("canola", 0);
        currentMethanol = valueInput.getIntOr("methanol", 0);
        currentMix = valueInput.getIntOr("mix", 0);
        timeToGenerate = valueInput.getIntOr("time", 0);
        super.loadAdditional(valueInput);
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public ItemStack getItem(int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int index, ItemStack stack) {

    }

    @Override
    public int getMaxStackSize() {
        return 0;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void startOpen(Player player) {

    }

    @Override
    public void stopOpen(Player player) {

    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return false;
    }

    @Override
    public void clearContent() {

    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyNeeded = maxStorage - storedEnergy;

        if (!simulate) {
            storedEnergy += Math.min(energyNeeded, maxReceive);
            setChanged();
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
        return maxStorage;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    public int getCurrentCanola() {
        return currentCanola;
    }

    public int getCurrentMethanol() {
        return currentMethanol;
    }

    public int getCurrentMix() {
        return currentMix;
    }

    public int getStoredEnergy() {
        return storedEnergy;
    }

    public int getTimeToGenerate() {
        return timeToGenerate;
    }

    @Override
    public Component getTranslatedName() {
        return Component.translatable("block.car.backmix_reactor");
    }

    @Override
    public ContainerData getFields() {
        return FIELDS;
    }

    @Override
    public int getTanks() {
        return 3;
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        if (tank == 0) {
            return new FluidStack(ModFluids.CANOLA_OIL.get(), currentCanola);
        } else if (tank == 1) {
            return new FluidStack(ModFluids.METHANOL.get(), currentMethanol);
        } else {
            return new FluidStack(ModFluids.CANOLA_METHANOL_MIX.get(), currentMix);
        }
    }

    @Override
    public int getTankCapacity(int tank) {
        if (tank == 0) {
            return maxCanola;
        } else if (tank == 1) {
            return maxMethanol;
        } else {
            return maxMix;
        }
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        if (tank == 0) {
            return stack.getFluid().equals(ModFluids.CANOLA_OIL.get());
        } else if (tank == 1) {
            return stack.getFluid().equals(ModFluids.METHANOL.get());
        } else {
            return false;
        }

    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource.getFluid().equals(ModFluids.METHANOL.get())) {
            int amount = Math.min(maxMethanol - currentMethanol, resource.getAmount());
            if (action.execute()) {
                currentMethanol += amount;
                setChanged();
            }
            return amount;
        } else if (resource.getFluid().equals(ModFluids.CANOLA_OIL.get())) {
            int amount = Math.min(maxCanola - currentCanola, resource.getAmount());
            if (action.execute()) {
                currentCanola += amount;
                setChanged();
            }
            return amount;
        }

        return 0;
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        int amount = Math.min(resource.getAmount(), currentMix);

        if (action.execute()) {
            currentMix -= amount;
            setChanged();
        }

        return new FluidStack(ModFluids.CANOLA_METHANOL_MIX.get(), amount);
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        int amount = Math.min(maxDrain, currentMix);

        if (action.execute()) {
            currentMix -= amount;
            setChanged();
        }

        return new FluidStack(ModFluids.CANOLA_METHANOL_MIX.get(), amount);
    }
}
