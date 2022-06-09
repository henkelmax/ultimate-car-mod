package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Main;
import de.maxhenkel.corelib.blockentity.ITickableBlockEntity;
import de.maxhenkel.corelib.energy.EnergyUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityDynamo extends TileEntityBase implements IEnergyStorage, ITickableBlockEntity {

    private int storedEnergy;
    public final int maxStorage;
    public final int generation;

    public TileEntityDynamo(BlockPos pos, BlockState state) {
        super(Main.DYNAMO_TILE_ENTITY_TYPE.get(), pos, state);
        this.maxStorage = Main.SERVER_CONFIG.dynamoEnergyStorage.get();
        this.generation = Main.SERVER_CONFIG.dynamoEnergyGeneration.get();
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
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);

        compound.putInt("stored_energy", storedEnergy);
    }

    @Override
    public void load(CompoundTag compound) {
        storedEnergy = compound.getInt("stored_energy");
        super.load(compound);
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
