package de.maxhenkel.car;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class Capabilities {

	@CapabilityInject(IFluidHandler.class)
	public static Capability<IFluidHandler> FLUID_HANDLER_CAPABILITY = null;
	
}
