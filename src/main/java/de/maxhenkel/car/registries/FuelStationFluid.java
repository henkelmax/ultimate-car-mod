package de.maxhenkel.car.registries;

import de.maxhenkel.tools.FluidSelector;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

public class FuelStationFluid extends IForgeRegistryEntry.Impl<FuelStationFluid>{

	public static final IForgeRegistry<FuelStationFluid> REGISTRY=new RegistryBuilder<FuelStationFluid>().setName(new ResourceLocation("fuelstation")).setType(FuelStationFluid.class).setIDRange(0, 8096).create();
	
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
