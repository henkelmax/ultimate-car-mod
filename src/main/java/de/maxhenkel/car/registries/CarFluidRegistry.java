package de.maxhenkel.car.registries;

import de.maxhenkel.tools.FluidSelector;
import net.minecraftforge.fluids.Fluid;

public class CarFluidRegistry {

	public static final StringRegistry<CarFluidRegistry> REGISTRY=new StringRegistry<>();
	
	private FluidSelector input;
	private float efficiency;
	
	public CarFluidRegistry(Fluid input, float efficiency) {
		this.input=new FluidSelector(input);
		this.efficiency=efficiency;
	}
	
	public CarFluidRegistry(FluidSelector input, float efficiency) {
		this.input=input;
		this.efficiency=efficiency;
	}

	public FluidSelector getInput() {
		return input;
	}

	public float getEfficiency() {
		return efficiency;
	}

}
