package de.maxhenkel.car.registries;

import de.maxhenkel.tools.FluidSelector;
import net.minecraftforge.fluids.Fluid;

public class FuelStationFluid{

	public static final StringRegistry<FuelStationFluid> REGISTRY=new StringRegistry<FuelStationFluid>();
	
	private FluidSelector input;
	
	public FuelStationFluid(Fluid input) {
		this.input=new FluidSelector(input);
	}
	
	public FuelStationFluid(FluidSelector input) {
		this.input=input;
	}

	public FluidSelector getInput() {
		return input;
	}

}
