package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.corelib.blockentity.ITickableBlockEntity;
import de.maxhenkel.tools.IntegerJournal;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public class TileEntityBackmixReactor extends TileEntityBase implements ITickableBlockEntity, ResourceHandler<FluidResource>, EnergyHandler, Container {

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

    private final SnapshotJournal<Integer> canolaJournal = new IntegerJournal(i -> {
        currentCanola = i;
        setChanged();
    }, () -> currentCanola);
    private final SnapshotJournal<Integer> methanolJournal = new IntegerJournal(i -> {
        currentMethanol = i;
        setChanged();
    }, () -> currentMethanol);
    private final SnapshotJournal<Integer> mixJournal = new IntegerJournal(i -> {
        currentMix = i;
        setChanged();
    }, () -> currentMix);
    private final SnapshotJournal<Integer> energyJournal = new IntegerJournal(i -> {
        storedEnergy = i;
        setChanged();
    }, () -> storedEnergy);

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
        if (level.isClientSide()) {
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
    public void startOpen(ContainerUser user) {
        Container.super.startOpen(user);
    }

    @Override
    public void stopOpen(ContainerUser user) {
        Container.super.stopOpen(user);
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
    public int size() {
        return 3;
    }

    @Override
    public FluidResource getResource(int index) {
        if (index == 0) {
            return FluidResource.of(new FluidStack(ModFluids.CANOLA_OIL.get(), currentCanola));
        } else if (index == 1) {
            return FluidResource.of(new FluidStack(ModFluids.METHANOL.get(), currentMethanol));
        } else {
            return FluidResource.of(new FluidStack(ModFluids.CANOLA_METHANOL_MIX.get(), currentMix));
        }
    }

    @Override
    public long getAmountAsLong(int index) {
        if (index == 0) {
            return currentCanola;
        } else if (index == 1) {
            return currentMethanol;
        } else {
            return currentMix;
        }
    }

    @Override
    public long getCapacityAsLong(int index, FluidResource resource) {
        if (index == 0 && resource.is(ModFluids.CANOLA_OIL.get())) {
            return maxCanola;
        }
        if (index == 1 && resource.is(ModFluids.METHANOL.get())) {
            return maxMethanol;
        }
        if (index == 2 && resource.is(ModFluids.CANOLA_METHANOL_MIX.get())) {
            return maxMix;
        }
        return 0;
    }

    @Override
    public boolean isValid(int index, FluidResource resource) {
        if (index == 0 && resource.is(ModFluids.CANOLA_OIL.get())) {
            return true;
        }
        if (index == 1 && resource.is(ModFluids.METHANOL.get())) {
            return true;
        }
        if (index == 2 && resource.is(ModFluids.CANOLA_METHANOL_MIX.get())) {
            return true;
        }
        return false;
    }

    @Override
    public int insert(int index, FluidResource resource, int amount, TransactionContext transaction) {
        if (resource.is(ModFluids.METHANOL.get())) {
            int actualAmount = Math.min(maxMethanol - currentMethanol, amount);
            methanolJournal.updateSnapshots(transaction);
            currentMethanol += actualAmount;
            setChanged();
            return actualAmount;
        } else if (resource.is(ModFluids.CANOLA_OIL.get())) {
            int actualAmount = Math.min(maxCanola - currentCanola, amount);
            canolaJournal.updateSnapshots(transaction);
            currentCanola += actualAmount;
            setChanged();
            return actualAmount;
        }
        return 0;
    }

    @Override
    public int extract(int index, FluidResource resource, int amount, TransactionContext transaction) {
        if (!resource.is(ModFluids.CANOLA_METHANOL_MIX.get())) {
            return 0;
        }
        int actualAmount = Math.min(amount, currentMix);

        mixJournal.updateSnapshots(transaction);
        currentMix -= actualAmount;
        setChanged();

        return actualAmount;
    }

    @Override
    public long getAmountAsLong() {
        return storedEnergy;
    }

    @Override
    public long getCapacityAsLong() {
        return maxStorage;
    }

    @Override
    public int insert(int amount, TransactionContext transaction) {
        int energyNeeded = maxStorage - storedEnergy;
        int toStore = Math.min(energyNeeded, amount);
        energyJournal.updateSnapshots(transaction);
        storedEnergy += toStore;
        setChanged();
        return toStore;
    }

    @Override
    public int extract(int amount, TransactionContext transaction) {
        return 0;
    }

    @Override
    public EnergyHandler getEnergyStorage() {
        return this;
    }

    @Override
    public ResourceHandler<FluidResource> getFluidHandler() {
        return this;
    }
}
