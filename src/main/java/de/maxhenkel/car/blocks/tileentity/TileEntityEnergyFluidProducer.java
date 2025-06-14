package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.blocks.BlockGui;
import de.maxhenkel.car.recipes.EnergyFluidProducerRecipe;
import de.maxhenkel.corelib.blockentity.ITickableBlockEntity;
import de.maxhenkel.corelib.codec.ValueInputOutputUtils;
import de.maxhenkel.corelib.item.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;

public abstract class TileEntityEnergyFluidProducer extends TileEntityBase implements IEnergyStorage, WorldlyContainer, ITickableBlockEntity, IFluidHandler, RecipeInput {

    protected RecipeType<? extends EnergyFluidProducerRecipe> recipeType;
    protected SimpleContainer inventory;

    protected int maxEnergy;
    protected int storedEnergy;

    protected int time;

    protected int fluidAmount;
    protected int currentMillibuckets;

    public TileEntityEnergyFluidProducer(BlockEntityType<?> tileEntityTypeIn, RecipeType<? extends EnergyFluidProducerRecipe> recipeType, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
        this.recipeType = recipeType;
        this.inventory = new SimpleContainer(2);
        this.maxEnergy = 10000;
        this.storedEnergy = 0;
        this.time = 0;
        this.fluidAmount = 3000;
        this.currentMillibuckets = 0;
    }

    public final ContainerData FIELDS = new ContainerData() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return time;
                case 1:
                    return storedEnergy;
                case 2:
                    return currentMillibuckets;
                case 3:
                    if (level instanceof ServerLevel serverLevel) {
                        return getTimeToGenerate(serverLevel);
                    }
                    return 0;
                default:
                    return 0;
            }
        }

        @Override
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
                case 3:
                    clientTimeToGenerate = value;
                    break;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public EnergyFluidProducerRecipe getRecipe(ServerLevel serverLevel) {
        return serverLevel.recipeAccess().getRecipeFor(recipeType, this, level).map(RecipeHolder::value).orElse(null);
    }

    @Override
    public void tick() {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        ItemStack input = inventory.getItem(0);
        ItemStack output = inventory.getItem(1);

        EnergyFluidProducerRecipe recipe = getRecipe(serverLevel);

        if (recipe == null) {
            time = 0;
            setChanged();
            setBlockEnabled(false);
            return;
        }

        if (storedEnergy < recipe.getEnergy()) {
            setBlockEnabled(false);
            return;
        }

        if (input.isEmpty()) {
            time = 0;
            setChanged();
            setBlockEnabled(false);
            return;
        }

        if (!(output.isEmpty() || (ItemStack.isSameItem(output, recipe.getResultItem()) && output.getCount() + recipe.getResultItem().getCount() <= output.getMaxStackSize()))) {
            time = 0;
            setChanged();
            setBlockEnabled(false);
            return;
        }

        if (currentMillibuckets + recipe.getFluidAmount() > fluidAmount) {
            time = 0;
            setChanged();
            setBlockEnabled(false);
            return;
        }

        time++;
        storedEnergy -= recipe.getEnergy();

        if (time > recipe.getDuration()) {
            time = 0;

            if (output.isEmpty()) {
                inventory.setItem(1, recipe.getResultItem());
            } else if (output.getCount() < output.getMaxStackSize()) {
                output.grow(recipe.getResultItem().getCount());
            }
            currentMillibuckets += recipe.getFluidAmount();
            input.shrink(1);
        }
        setChanged();
        setBlockEnabled(true);
    }

    public abstract BlockGui<? extends TileEntityEnergyFluidProducer> getOwnBlock();

    public void setBlockEnabled(boolean enabled) {
        BlockState state = level.getBlockState(getBlockPos());
        if (state.getBlock().equals(getOwnBlock())) {
            getOwnBlock().setPowered(level, worldPosition, state, enabled);
        }
    }

    @Override
    public void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.putInt("energy_stored", storedEnergy);
        valueOutput.putInt("time", time);
        valueOutput.putInt("fluid_stored", currentMillibuckets);


        CompoundTag compound = new CompoundTag();
        ItemUtils.saveInventory(compound, "slots", inventory);
        valueOutput.store(compound);
    }

    @Override
    public void loadAdditional(ValueInput valueInput) {
        storedEnergy = valueInput.getIntOr("energy_stored", 0);
        time = valueInput.getIntOr("time", 0);
        currentMillibuckets = valueInput.getIntOr("fluid_stored", 0);

        CompoundTag tag = ValueInputOutputUtils.getTag(valueInput);
        ItemUtils.readInventory(tag, "slots", inventory);
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
    public void startOpen(Player player) {
        inventory.startOpen(player);
    }

    @Override
    public void stopOpen(Player player) {
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
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[]{0, 1};
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, Direction direction) {
        return index == 0;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index == 1;
    }


    public SimpleContainer getInventory() {
        return inventory;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getStoredEnergy() {
        return storedEnergy;
    }

    public int getTimeToGenerate(ServerLevel serverLevel) {
        EnergyFluidProducerRecipe recipe = getRecipe(serverLevel);
        if (recipe == null) {
            return 0;
        }
        return recipe.getDuration();
    }

    private int clientTimeToGenerate;

    public int getClientTimeToGenerate() {
        return clientTimeToGenerate;
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
    public ContainerData getFields() {
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
            setChanged();
        }

        return new FluidStack(getProducingFluid(), amount);
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        int amount = Math.min(maxDrain, currentMillibuckets);

        if (action.execute()) {
            currentMillibuckets -= amount;
            setChanged();
        }

        return new FluidStack(getProducingFluid(), amount);
    }

    @Override
    public int size() {
        return 1;
    }
}
