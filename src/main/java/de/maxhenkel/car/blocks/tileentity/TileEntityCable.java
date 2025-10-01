package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.corelib.blockentity.ITickableBlockEntity;
import de.maxhenkel.tools.BlockPosList;
import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.ArrayList;
import java.util.List;

public class TileEntityCable extends TileEntityBase implements ITickableBlockEntity, EnergyHandler {

    private final int transferRate;

    public TileEntityCable(int transferRate, BlockPos pos, BlockState state) {
        super(CarMod.CABLE_TILE_ENTITY_TYPE.get(), pos, state);
        this.transferRate = transferRate;
    }

    public TileEntityCable(BlockPos pos, BlockState state) {
        this(CarMod.SERVER_CONFIG.cableTransferRate.get(), pos, state);
    }

    @Override
    public void tick() {
        int energy = 0;
        List<EnergyHandler> providers = new ArrayList<>();

        for (Direction facing : Direction.values()) {
            EnergyHandler energyHandler = level.getCapability(Capabilities.Energy.BLOCK, worldPosition.relative(facing), facing.getOpposite());

            if (energyHandler == null || energyHandler instanceof TileEntityCable) {
                continue;
            }

            int cex = Math.max(0, transferRate - energy);

            if (cex <= 0) {
                break;
            }

            int extract;
            try (Transaction transaction = Transaction.open(null)) {
                extract = energyHandler.extract(cex, transaction);
            }

            if (extract > 0) {
                energy += extract;
                providers.add(energyHandler);
            }

        }

        if (energy <= 0) {
            return;
        }

        List<EnergyHandler> receivers = new ArrayList<>();

        getConnectedReceivers(providers, receivers, new BlockPosList(), worldPosition);

        if (receivers.size() <= 0) {
            return;
        }

        int split = energy / receivers.size();

        if (split <= 0) {
            return; //TODO handle splitting if there is not enough energy to split over all connections
        }

        int received = 0;

        try (Transaction transaction = Transaction.open(null)) {
            for (EnergyHandler entry : receivers) {
                received += entry.insert(split, transaction);
            }

            for (EnergyHandler entry : providers) {
                if (received <= 0) {
                    break;
                }
                received -= entry.extract(received, transaction);
            }
            transaction.commit();
        }
    }

    public void getConnectedReceivers(List<EnergyHandler> sources, List<EnergyHandler> receivers, BlockPosList positions, BlockPos pos) {
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

            EnergyHandler energyHandler = level.getCapability(Capabilities.Energy.BLOCK, pos.relative(side), side.getOpposite());

            if (energyHandler == null || energyHandler.equals(this)) {
                continue;
            }

            if (sources.contains(energyHandler)) {
                continue;
            }

            if (receivers.contains(energyHandler)) {
                continue;
            }

            receivers.add(energyHandler);
        }
    }

    @Override
    public Component getTranslatedName() {
        return Component.translatable("block.car.cable");
    }

    @Override
    public ContainerData getFields() {
        return new SimpleContainerData(0);
    }

    @Override
    public long getAmountAsLong() {
        return 0;
    }

    @Override
    public long getCapacityAsLong() {
        return 0;
    }

    @Override
    public int insert(int amount, TransactionContext transaction) {
        return 0;
    }

    @Override
    public int extract(int amount, TransactionContext transaction) {
        return 0;
    }

    @Override
    public EnergyHandler getEnergyStorage() {
        return this;
    }
}
