package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.corelib.blockentity.ITickableBlockEntity;
import de.maxhenkel.tools.IntegerJournal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import javax.annotation.Nullable;

public class TileEntityDynamo extends TileEntityBase implements EnergyHandler, ITickableBlockEntity {

    private int storedEnergy;
    public final int maxStorage;
    public final int generation;

    private final SnapshotJournal<Integer> energyJournal = new IntegerJournal(i -> {
        storedEnergy = i;
        setChanged();
    }, () -> storedEnergy);

    public TileEntityDynamo(BlockPos pos, BlockState state) {
        super(CarMod.DYNAMO_TILE_ENTITY_TYPE.get(), pos, state);
        this.maxStorage = CarMod.SERVER_CONFIG.dynamoEnergyStorage.get();
        this.generation = CarMod.SERVER_CONFIG.dynamoEnergyGeneration.get();
        this.storedEnergy = 0;

    }

    @Override
    public void tick() {
        for (Direction side : Direction.values()) {
            EnergyHandler energyHandler = level.getCapability(Capabilities.Energy.BLOCK, worldPosition.relative(side), side.getOpposite());

            if (energyHandler == null) {
                continue;
            }

            pushEnergy(this, energyHandler, storedEnergy, null);
        }
    }

    private static int pushEnergy(EnergyHandler provider, EnergyHandler receiver, int maxAmount, @Nullable TransactionContext transaction) {
        int energySim;
        int receivedSim;
        try (Transaction t = Transaction.open(transaction)) {
            energySim = provider.extract(maxAmount, t);
            receivedSim = receiver.insert(energySim, t);
        }
        int energy;
        try (Transaction t = Transaction.open(transaction)) {
            energy = provider.extract(receivedSim, t);
            receiver.insert(energy, t);
            t.commit();
        }
        return energy;
    }

    public void addEnergy(int energy) {
        storedEnergy += energy;
        if (storedEnergy > maxStorage) {
            storedEnergy = maxStorage;
        }
        setChanged();
    }

    @Override
    public void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.putInt("stored_energy", storedEnergy);
    }

    @Override
    public void loadAdditional(ValueInput valueInput) {
        storedEnergy = valueInput.getIntOr("stored_energy", 0);
        super.loadAdditional(valueInput);
    }

    @Override
    public Component getTranslatedName() {
        return Component.translatable("block.car.dynamo");
    }

    @Override
    public ContainerData getFields() {
        return new SimpleContainerData(0);
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
    public EnergyHandler getEnergyStorage() {
        return this;
    }
}
