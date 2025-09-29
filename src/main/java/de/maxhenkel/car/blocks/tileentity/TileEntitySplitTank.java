package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.corelib.blockentity.ITickableBlockEntity;
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
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;

public class TileEntitySplitTank extends TileEntityBase implements ITickableBlockEntity, IFluidHandler, Container {

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
    public int getTanks() {
        return 3;
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        if (tank == 0) {
            return new FluidStack(ModFluids.CANOLA_METHANOL_MIX.get(), currentMix);
        } else if (tank == 1) {
            return new FluidStack(ModFluids.BIO_DIESEL.get(), currentBioDiesel);
        } else {
            return new FluidStack(ModFluids.GLYCERIN.get(), currentGlycerin);
        }
    }

    @Override
    public int getTankCapacity(int tank) {
        if (tank == 0) {
            return maxMix;
        } else if (tank == 1) {
            return maxBioDiesel;
        } else {
            return maxGlycerin;
        }
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        if (tank == 0) {
            return stack.getFluid().equals(ModFluids.CANOLA_METHANOL_MIX.get());
        } else if (tank == 1) {
            return stack.getFluid().equals(ModFluids.BIO_DIESEL.get());
        } else {
            return stack.getFluid().equals(ModFluids.GLYCERIN.get());
        }
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource.getFluid().equals(ModFluids.CANOLA_METHANOL_MIX.get())) {
            int amount = Math.min(maxMix - currentMix, resource.getAmount());
            if (action.execute()) {
                currentMix += amount;
                setChanged();
            }
            return amount;
        }

        return 0;
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.getFluid().equals(ModFluids.GLYCERIN.get())) {
            int amount = Math.min(resource.getAmount(), currentGlycerin);

            if (action.execute()) {
                currentGlycerin -= amount;
                setChanged();
            }

            return new FluidStack(ModFluids.GLYCERIN.get(), amount);
        } else if (resource.getFluid().equals(ModFluids.BIO_DIESEL.get())) {
            int amount = Math.min(resource.getAmount(), currentBioDiesel);

            if (action.execute()) {
                currentBioDiesel -= amount;
                setChanged();
            }
            return new FluidStack(ModFluids.BIO_DIESEL.get(), amount);
        }

        return FluidStack.EMPTY;
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if (currentGlycerin > currentBioDiesel) {
            int amount = Math.min(maxDrain, currentGlycerin);

            if (action.execute()) {
                currentGlycerin -= amount;
                setChanged();
            }

            return new FluidStack(ModFluids.GLYCERIN.get(), amount);
        } else {
            int amount = Math.min(maxDrain, currentBioDiesel);

            if (action.execute()) {
                currentBioDiesel -= amount;
                setChanged();
            }
            return new FluidStack(ModFluids.BIO_DIESEL.get(), amount);
        }
    }
}
