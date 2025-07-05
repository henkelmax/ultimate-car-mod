package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.corelib.blockentity.ITickableBlockEntity;
import de.maxhenkel.corelib.energy.EnergyUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class TileEntityDynamo extends TileEntityBase implements IEnergyStorage, ITickableBlockEntity {

    private int storedEnergy;
    public final int maxStorage;
    public final int generation;

    public TileEntityDynamo(BlockPos pos, BlockState state) {
        super(CarMod.DYNAMO_TILE_ENTITY_TYPE.get(), pos, state);
        this.maxStorage = CarMod.SERVER_CONFIG.dynamoEnergyStorage.get();
        this.generation = CarMod.SERVER_CONFIG.dynamoEnergyGeneration.get();
        this.storedEnergy = 0;

    }

    @Override
    public void tick() {
        for (Direction side : Direction.values()) {
            IEnergyStorage storage = EnergyUtils.getEnergyStorageOffset(level, worldPosition, side);

            if (storage == null) {
                continue;
            }

            EnergyUtils.pushEnergy(this, storage, storedEnergy);
        }
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

    @Override
    public Component getTranslatedName() {
        return Component.translatable("block.car.dynamo");
    }

    @Override
    public ContainerData getFields() {
        return new SimpleContainerData(0);
    }

}
