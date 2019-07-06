package de.maxhenkel.tools;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class EnergyTools {

    public static void pushEnergy(IEnergyStorage provider, IEnergyStorage receiver, int maxAmount, Direction extractSide, Direction pushSide) {
        int energySim = provider.extractEnergy(maxAmount, true);

        int receivedSim = receiver.receiveEnergy(energySim, true);

        int energy = provider.extractEnergy(receivedSim, false);

        receiver.receiveEnergy(energy, false);
    }

    @Nullable
    public static IEnergyStorage getEnergyStorage(IWorldReader world, BlockPos pos, Direction side) {
        TileEntity te = world.getTileEntity(pos);

        if (te == null) {
            return null;
        }
        return te.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).orElse(null);
    }

    @Nullable
    public static IEnergyStorage getEnergyStorageOffset(IWorldReader world, BlockPos pos, Direction side) {
        return getEnergyStorage(world, pos.offset(side), side);
    }

}
