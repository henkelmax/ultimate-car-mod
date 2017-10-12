package de.maxhenkel.car.registries;

import de.maxhenkel.tools.FluidSelector;
import net.minecraftforge.fluids.Fluid;

public class CarFluid{

	public static final StringRegistry<CarFluid> REGISTRY=new StringRegistry<CarFluid>();
	
	private FluidSelector input;
	private String carID;
	private double efficiency;
	
	public CarFluid(String carID, Fluid input, double efficiency) {
		this.input=new FluidSelector(input);
		this.carID=carID;
		this.efficiency=efficiency;
	}
	
	public CarFluid(String carID, FluidSelector input, double efficiency) {
		this.input=input;
		this.carID=carID;
		this.efficiency=efficiency;
	}

	public FluidSelector getInput() {
		return input;
	}

	public String getCarID() {
		return carID;
	}

	public double getEfficiency() {
		return efficiency;
	}

}
