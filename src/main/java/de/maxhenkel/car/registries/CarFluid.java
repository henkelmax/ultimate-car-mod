package de.maxhenkel.car.registries;

import de.maxhenkel.tools.FluidSelector;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

public class CarFluid extends IForgeRegistryEntry.Impl<CarFluid>{

	public static final IForgeRegistry<CarFluid> REGISTRY=new RegistryBuilder<CarFluid>().setName(new ResourceLocation("car")).setType(CarFluid.class).setIDRange(0, 8096).create();
	
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
