package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.corelib.blockentity.ITickableBlockEntity;
import de.maxhenkel.tools.IntegerJournal;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public class TileEntitySplitTank extends TileEntityBase implements ITickableBlockEntity, ResourceHandler<FluidResource>, Container {

    private int currentMix;
    public int maxMix;
    public int mixUsage;

    private int currentBioDiesel;
    public int maxBioDiesel;
    public int bioDieselGeneration;

    private int currentGlycerin;
    public int maxGlycerin;
    public int glycerinGeneration;

    public int generatingTime;
    private int timeToGenerate;

    protected SimpleContainer inventory;

    private final SnapshotJournal<Integer> mixJournal = new IntegerJournal(i -> {
        currentMix = i;
        setChanged();
    }, () -> currentMix);
    private final SnapshotJournal<Integer> bioDieselJournal = new IntegerJournal(i -> {
        currentBioDiesel = i;
        setChanged();
    }, () -> currentBioDiesel);
    private final SnapshotJournal<Integer> glycerinJournal = new IntegerJournal(i -> {
        currentGlycerin = i;
        setChanged();
    }, () -> currentGlycerin);

    public TileEntitySplitTank(BlockPos pos, BlockState state) {
        super(CarMod.SPLIT_TANK_TILE_ENTITY_TYPE.get(), pos, state);
        this.inventory = new SimpleContainer(0);
        this.currentMix = 0;
        this.maxMix = CarMod.SERVER_CONFIG.splitTankFluidStorage.get();

        this.currentBioDiesel = 0;
        this.maxBioDiesel = CarMod.SERVER_CONFIG.splitTankFluidStorage.get();

        this.currentGlycerin = 0;
        this.maxGlycerin = CarMod.SERVER_CONFIG.splitTankFluidStorage.get();

        this.generatingTime = CarMod.SERVER_CONFIG.splitTankGeneratingTime.get();
        this.timeToGenerate = 0;

        this.mixUsage = CarMod.SERVER_CONFIG.splitTankMixUsage.get();
        this.glycerinGeneration = CarMod.SERVER_CONFIG.splitTankGlycerinGeneration.get();
        this.bioDieselGeneration = CarMod.SERVER_CONFIG.splitTankBioDieselGeneration.get();
    }

    public final ContainerData FIELDS = new ContainerData() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return currentMix;
                case 1:
                    return currentBioDiesel;
                case 2:
                    return currentGlycerin;
                case 3:
                    return timeToGenerate;
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    currentMix = value;
                    break;
                case 1:
                    currentBioDiesel = value;
                    break;
                case 2:
                    currentGlycerin = value;
                    break;
                case 3:
                    timeToGenerate = value;
                    break;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    @Override
    public void tick() {
        if (level.isClientSide()) {
            return;
        }

        if (timeToGenerate > 0) {
            timeToGenerate--;

            if (timeToGenerate == 0) {

                if (currentMix - mixUsage >= 0) {
                    currentMix -= mixUsage;
                    if (currentBioDiesel + bioDieselGeneration <= maxBioDiesel) {
                        currentBioDiesel += bioDieselGeneration;
                    }
                    if (currentGlycerin + glycerinGeneration <= maxGlycerin) {
                        currentGlycerin += glycerinGeneration;
                    }
                }
                // synchronize();
            }
        } else {
            if (currentMix >= mixUsage) {
                if (currentBioDiesel + bioDieselGeneration <= maxBioDiesel) {
                    if (currentGlycerin + glycerinGeneration <= maxGlycerin) {
                        timeToGenerate = generatingTime;
                    }

                }

            }
        }

        if (level.getGameTime() % 200 == 0) {
            synchronize();
        }

        setChanged();
    }

    public float getBioDieselPerc() {
        return ((float) currentBioDiesel) / ((float) maxBioDiesel);
    }

    public float getGlycerinPerc() {
        return ((float) currentGlycerin) / ((float) maxGlycerin);
    }

    @Override
    public void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.putInt("mix", currentMix);
        valueOutput.putInt("bio_diesel", currentBioDiesel);
        valueOutput.putInt("glycerin", currentGlycerin);
        valueOutput.putInt("time", timeToGenerate);
    }

    @Override
    public void loadAdditional(ValueInput valueInput) {
        currentMix = valueInput.getIntOr("mix", 0);
        currentBioDiesel = valueInput.getIntOr("bio_diesel", 0);
        currentGlycerin = valueInput.getIntOr("glycerin", 0);
        timeToGenerate = valueInput.getIntOr("timeToGenerate", 0);
        super.loadAdditional(valueInput);
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
    public boolean stillValid(Player player) {
        return inventory.stillValid(player);
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public void startOpen(ContainerUser user) {
        inventory.startOpen(user);
    }

    @Override
    public void stopOpen(ContainerUser user) {
        inventory.stopOpen(user);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return inventory.canPlaceItem(index, stack);
    }

    @Override
    public void clearContent() {
        inventory.clearContent();
    }

    public int getCurrentMix() {
        return currentMix;
    }

    public int getCurrentBioDiesel() {
        return currentBioDiesel;
    }

    public int getCurrentGlycerin() {
        return currentGlycerin;
    }

    public int getTimeToGenerate() {
        return timeToGenerate;
    }

    @Override
    public Component getTranslatedName() {
        return Component.translatable("block.car.split_tank");
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
            return FluidResource.of(ModFluids.CANOLA_METHANOL_MIX.get());
        } else if (index == 1) {
            return FluidResource.of(ModFluids.BIO_DIESEL.get());
        } else {
            return FluidResource.of(ModFluids.GLYCERIN.get());
        }
    }

    @Override
    public long getAmountAsLong(int index) {
        if (index == 0) {
            return currentMix;
        } else if (index == 1) {
            return currentBioDiesel;
        } else {
            return currentGlycerin;
        }
    }

    @Override
    public long getCapacityAsLong(int index, FluidResource resource) {
        if (index == 0) {
            return maxMix;
        } else if (index == 1) {
            return maxBioDiesel;
        } else {
            return maxGlycerin;
        }
    }

    @Override
    public boolean isValid(int index, FluidResource resource) {
        if (index == 0) {
            return resource.is(ModFluids.CANOLA_METHANOL_MIX.get());
        } else if (index == 1) {
            return resource.is(ModFluids.BIO_DIESEL.get());
        } else {
            return resource.is(ModFluids.GLYCERIN.get());
        }
    }

    @Override
    public int insert(int index, FluidResource resource, int amount, TransactionContext transaction) {
        if (resource.getFluid().equals(ModFluids.CANOLA_METHANOL_MIX.get())) {
            int result = Math.min(maxMix - currentMix, amount);
            mixJournal.updateSnapshots(transaction);
            currentMix += result;
            setChanged();
            return result;
        }
        return 0;
    }

    @Override
    public int extract(int index, FluidResource resource, int amount, TransactionContext transaction) {
        if (resource.getFluid().equals(ModFluids.GLYCERIN.get())) {
            int result = Math.min(amount, currentGlycerin);

            glycerinJournal.updateSnapshots(transaction);
            currentGlycerin -= result;
            setChanged();

            return result;
        } else if (resource.getFluid().equals(ModFluids.BIO_DIESEL.get())) {
            int result = Math.min(amount, currentBioDiesel);

            bioDieselJournal.updateSnapshots(transaction);
            currentBioDiesel -= result;
            setChanged();
            return result;
        }

        return 0;
    }

    @Override
    public ResourceHandler<FluidResource> getFluidHandler() {
        return this;
    }
}
