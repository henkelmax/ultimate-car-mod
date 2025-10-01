package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.BlockGui;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.corelib.blockentity.ITickableBlockEntity;
import de.maxhenkel.tools.IntegerJournal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandlerUtil;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.AbstractMap;
import java.util.Map;

public class TileEntityGenerator extends TileEntityBase implements ITickableBlockEntity, ResourceHandler<FluidResource>, EnergyHandler, Container {

    public final int maxStorage;
    public int storedEnergy;

    public final int maxMillibuckets;
    protected int currentMillibuckets;
    protected final int energyGeneration;

    protected Fluid currentFluid;

    protected SimpleContainer inventory;

    private final IntegerJournal energyJournal = new IntegerJournal(i -> {
        storedEnergy = i;
        setChanged();
    }, () -> storedEnergy);

    private final SnapshotJournal<Map.Entry<Fluid, Integer>> fluidJournal = new SnapshotJournal<>() {
        @Override
        protected Map.Entry<Fluid, Integer> createSnapshot() {
            return new AbstractMap.SimpleEntry<>(currentFluid, currentMillibuckets);
        }

        @Override
        protected void revertToSnapshot(Map.Entry<Fluid, Integer> snapshot) {
            currentFluid = snapshot.getKey();
            currentMillibuckets = snapshot.getValue();
            setChanged();
        }
    };

    public TileEntityGenerator(BlockPos pos, BlockState state) {
        super(CarMod.GENERATOR_TILE_ENTITY_TYPE.get(), pos, state);
        this.inventory = new SimpleContainer(0);
        this.maxStorage = CarMod.SERVER_CONFIG.generatorEnergyStorage.get();
        this.storedEnergy = 0;
        this.maxMillibuckets = CarMod.SERVER_CONFIG.generatorFluidStorage.get();
        this.currentMillibuckets = 0;
        this.energyGeneration = CarMod.SERVER_CONFIG.generatorEnergyGeneration.get();
    }

    public final ContainerData FIELDS = new ContainerData() {
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
        if (level.isClientSide()) {
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
        return CarMod.SERVER_CONFIG.generatorValidFuelList.stream().anyMatch(fluidTag -> fluidTag.contains(f));
    }

    private void handlePushEnergy() {
        for (Direction side : Direction.values()) {
            EnergyHandler energyHandler = level.getCapability(Capabilities.Energy.BLOCK, worldPosition.relative(side), side.getOpposite());
            if (energyHandler == null) {
                continue;
            }
            try (Transaction transaction = Transaction.open(null)) {
                int moved = EnergyHandlerUtil.move(this, energyHandler, storedEnergy, transaction);
                storedEnergy -= moved;
                transaction.commit();
            }
        }
    }

    public boolean isEnabled() {
        return currentMillibuckets > 0 && storedEnergy + energyGeneration < maxStorage;
    }

    public void setBlockEnabled(boolean enabled) {
        BlockState state = level.getBlockState(worldPosition);
        if (state.getBlock().equals(ModBlocks.GENERATOR.get())) {
            if (state.getValue(BlockGui.POWERED) != enabled) {
                ModBlocks.GENERATOR.get().setPowered(level, worldPosition, state, enabled);
            }
        }
    }

    @Override
    public Component getTranslatedName() {
        return Component.translatable("block.car.generator");
    }

    @Override
    public void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.putInt("stored_energy", storedEnergy);
        if (currentFluid != null) {
            FluidStack stack = new FluidStack(currentFluid, currentMillibuckets);
            valueOutput.store("fluid", FluidStack.CODEC, stack);
        }
    }

    @Override
    public void loadAdditional(ValueInput valueInput) {
        storedEnergy = valueInput.getIntOr("stored_energy", 0);
        valueInput.read("fluid", FluidStack.CODEC).ifPresent(stack -> {
            currentFluid = stack.getFluid();
            currentMillibuckets = stack.getAmount();
        });
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


    public int getStoredEnergy() {
        return storedEnergy;
    }

    public int getCurrentMillibuckets() {
        return currentMillibuckets;
    }

    @Override
    public ContainerData getFields() {
        return FIELDS;
    }

    @Override
    public ResourceHandler<FluidResource> getFluidHandler() {
        return this;
    }

    @Override
    public EnergyHandler getEnergyStorage() {
        return this;
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
        return 0;
    }

    @Override
    public int extract(int amount, TransactionContext transaction) {
        int i = Math.min(amount, storedEnergy);
        energyJournal.updateSnapshots(transaction);
        storedEnergy -= i;
        setChanged();
        return i;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public FluidResource getResource(int index) {
        if (currentFluid == null) {
            return FluidResource.EMPTY;
        }
        return FluidResource.of(currentFluid);
    }

    @Override
    public long getAmountAsLong(int index) {
        return currentMillibuckets;
    }

    @Override
    public long getCapacityAsLong(int index, FluidResource resource) {
        return maxMillibuckets;
    }

    @Override
    public boolean isValid(int index, FluidResource resource) {
        return isValidFuel(resource.getFluid());
    }

    @Override
    public int insert(int index, FluidResource resource, int amount, TransactionContext transaction) {
        if ((currentFluid == null && isValidFuel(resource.getFluid())) || resource.is(currentFluid)) {
            int result = Math.min(maxMillibuckets - currentMillibuckets, amount);
            fluidJournal.updateSnapshots(transaction);
            currentMillibuckets += result;
            if (currentFluid == null) {
                currentFluid = resource.getFluid();
            }
            setChanged();
            return result;
        }
        return 0;
    }

    @Override
    public int extract(int index, FluidResource resource, int amount, TransactionContext transaction) {
        return 0;
    }
}
