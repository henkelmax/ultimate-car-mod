package de.maxhenkel.tools;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidSelector implements Selector<Fluid>{

	private Fluid fluid;
	
	public FluidSelector(Fluid fluid) {
		this.fluid=fluid;
	}
	
	public Fluid getFluid() {
		return fluid;
	}
	
	@Nullable
	public static FluidSelector fromString(String str){

		Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(str)).getFluid();
		if (fluid == null) {
			return null;
		}
		
		return new FluidSelector(fluid);
	}
	
	public String toString() {
		return ForgeRegistries.FLUIDS.getKey(fluid).toString();
	}

	@Override
	public boolean isValid(Fluid f) {
		if(fluid==null||f==null){
			return false;
		}
		return fluid.equals(f);
	}
	
}
