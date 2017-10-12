package de.maxhenkel.car.registries;

import de.maxhenkel.tools.FluidSelector;
import net.minecraftforge.fluids.Fluid;

public class GeneratorRecipe{

	public static final StringRegistry<GeneratorRecipe> REGISTRY=new StringRegistry<GeneratorRecipe>();
	
	private FluidSelector input;
	private int energy;
	
	public GeneratorRecipe(Fluid input, int energy) {
		this.input=new FluidSelector(input);
		this.energy=energy;
	}
	
	public GeneratorRecipe(FluidSelector input, int energy) {
		this.input=input;
		this.energy=energy;
	}

	public FluidSelector getInput() {
		return input;
	}

	public int getEnergy() {
		return energy;
	}

}
