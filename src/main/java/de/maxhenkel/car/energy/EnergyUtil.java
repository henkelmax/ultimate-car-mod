package de.maxhenkel.car.energy;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import javax.annotation.Nullable;

public class EnergyUtil {

    public static void pushEnergy(IEnergyStorage provider, IEnergyStorage receiver, int maxAmount, EnumFacing extractSide, EnumFacing pushSide) {
        int energySim = provider.extractEnergy(maxAmount, true);

        int receivedSim = receiver.receiveEnergy(energySim, true);

        int energy = provider.extractEnergy(receivedSim, false);

        receiver.receiveEnergy(energy, false);
    }

    @Nullable
    public static IEnergyStorage getEnergyStorage(World world, BlockPos pos, EnumFacing side) {
        TileEntity te = world.getTileEntity(pos.offset(side));

        if (te == null || !te.hasCapability(CapabilityEnergy.ENERGY, side.getOpposite())) {
            return null;
        }
        return te.getCapability(CapabilityEnergy.ENERGY, side.getOpposite());
    }

}
