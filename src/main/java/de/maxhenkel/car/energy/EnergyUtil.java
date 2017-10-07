package de.maxhenkel.car.energy;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.util.EnumFacing;

public class EnergyUtil {

	public static void pushEnergy(IEnergyProvider provider, IEnergyReceiver receiver, int maxAmount, EnumFacing extractSide, EnumFacing pushSide){
		int energySim=provider.extractEnergy(extractSide, maxAmount, true);
		
		int receivedSim=receiver.receiveEnergy(pushSide, energySim, true);
		
		int energy=provider.extractEnergy(extractSide, receivedSim, false);
		
		receiver.receiveEnergy(pushSide, energy, false);
	}
	
}
