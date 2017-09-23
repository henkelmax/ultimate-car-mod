package de.maxhenkel.tools;

import javax.annotation.Nullable;

import de.maxhenkel.car.Config;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidUtils {

	public static boolean isFuelForFuelStation(Fluid fluid) {
		//return fluid.equals(ModFluids.BIO_DIESEL);
		return Config.validFuelStationFluids.contains(fluid);
	}

	public static int getGenerationFactor(Fluid fluid) {
		Integer i=Config.generationFactors.get(fluid);
		if(i==null||i<0){
			return 0;
		}
		return i;
	}

	public static boolean isFluidForGenerator(Fluid fluid) {
		return getGenerationFactor(fluid)>0;
	}

	@Nullable
	public static FluidStack tryFluidTransfer(IFluidHandler fluidDestination, IFluidHandler fluidSource, int maxAmount,
			boolean doTransfer, Fluid filter) {
		FluidStack drainable = fluidSource.drain(new FluidStack(filter, maxAmount), false);
		if (drainable != null && drainable.amount > 0) {
			int fillableAmount = fluidDestination.fill(drainable, false);
			if (fillableAmount > 0) {
				if (doTransfer) {
					FluidStack drained = fluidSource.drain(new FluidStack(filter, fillableAmount), true);
					if (drained != null) {
						drained.amount = fluidDestination.fill(drained, true);
						return drained;
					}
				} else {
					drainable.amount = fillableAmount;
					return drainable;
				}
			}
		}
		return null;
	}

}
