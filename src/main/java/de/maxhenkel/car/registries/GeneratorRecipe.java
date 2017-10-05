package de.maxhenkel.car.registries;

import de.maxhenkel.tools.FluidSelector;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

public class GeneratorRecipe extends IForgeRegistryEntry.Impl<GeneratorRecipe>{

	public static final IForgeRegistry<GeneratorRecipe> REGISTRY=new RegistryBuilder<GeneratorRecipe>().setName(new ResourceLocation("generator")).setType(GeneratorRecipe.class).setIDRange(0, 8096).create();
	
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
