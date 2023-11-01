package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Main;
import de.maxhenkel.corelib.blockentity.ITickableBlockEntity;
import de.maxhenkel.corelib.energy.EnergyUtils;
import de.maxhenkel.tools.BlockPosList;
import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import java.util.ArrayList;
import java.util.List;

public class TileEntityCable extends TileEntityBase implements ITickableBlockEntity, IEnergyStorage {

    private final int transferRate;

    public TileEntityCable(int transferRate, BlockPos pos, BlockState state) {
        super(Main.CABLE_TILE_ENTITY_TYPE.get(), pos, state);
        this.transferRate = transferRate;
    }

    public TileEntityCable(BlockPos pos, BlockState state) {
        this(Main.SERVER_CONFIG.cableTransferRate.get(), pos, state);
    }

    @Override
    public void tick() {
        int energy = 0;
        List<IEnergyStorage> providers = new ArrayList<>();

        for (Direction facing : Direction.values()) {
            IEnergyStorage provider = EnergyUtils.getEnergyStorageOffset(level, worldPosition, facing);

            if (provider == null || provider instanceof TileEntityCable) {
                continue;
            }

            int cex = Math.max(0, transferRate - energy);

            if (cex <= 0) {
                break;
            }

            int extract = provider.extractEnergy(cex, true);

            if (extract > 0) {
                energy += extract;
                providers.add(provider);
            }

        }

        if (energy <= 0) {
            return;
        }

        List<IEnergyStorage> receivers = new ArrayList<>();

        getConnectedReceivers(providers, receivers, new BlockPosList(), worldPosition);

        if (receivers.size() <= 0) {
            return;
        }

        int split = energy / receivers.size();

        if (split <= 0) {
            return; //TODO handle splitting if there is not enough energy to split over all connections
        }

        int received = 0;

        for (IEnergyStorage entry : receivers) {
            received += entry.receiveEnergy(split, false);
        }

        for (IEnergyStorage entry : providers) {
            if (received <= 0) {
                break;
            }
            received -= entry.extractEnergy(received, false);
        }

    }

    public void getConnectedReceivers(List<IEnergyStorage> sources, List<IEnergyStorage> receivers, BlockPosList positions, BlockPos pos) {
        for (Direction side : Direction.values()) {
            BlockPos p = pos.relative(side);

            if (positions.contains(p)) {
                continue;
            }

            BlockState state = level.getBlockState(p);

            if (state.getBlock().equals(ModBlocks.CABLE.get())) {
                positions.add(p);
                getConnectedReceivers(sources, receivers, positions, p);
                continue;
            }

            IEnergyStorage storage = EnergyUtils.getEnergyStorageOffset(level, pos, side);

            if (storage == null || storage.equals(this)) {
                continue;
            }

            if (sources.contains(storage)) {
                continue;
            }

            if (receivers.contains(storage)) {
                continue;
            }

            receivers.add(storage);
        }
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return 0;
    }

    @Override
    public int getMaxEnergyStored() {
        return 0;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return false;
    }

    @Override
    public Component getTranslatedName() {
        return Component.translatable("block.car.cable");
    }

    @Override
    public ContainerData getFields() {
        return new SimpleContainerData(0);
    }
}
