package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Main;
import de.maxhenkel.corelib.energy.EnergyUtils;
import de.maxhenkel.tools.BlockPosList;
import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;

public class TileEntityCable extends TileEntityBase implements ITickableTileEntity, IEnergyStorage {

    private final int transferRate;

    public TileEntityCable(int transferRate) {
        super(Main.CABLE_TILE_ENTITY_TYPE);
        this.transferRate = transferRate;
    }

    public TileEntityCable() {
        this(Main.SERVER_CONFIG.cableTransferRate.get());
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

            if (state.getBlock().equals(ModBlocks.CABLE)) {
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
    public ITextComponent getTranslatedName() {
        return new TranslationTextComponent("block.car.cable");
    }

    @Override
    public IIntArray getFields() {
        return new IntArray(0);
    }
}
