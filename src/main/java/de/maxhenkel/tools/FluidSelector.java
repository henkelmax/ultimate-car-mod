package de.maxhenkel.tools;

import javax.annotation.Nullable;

import net.minecraftforge.fluids.Fluid;

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
		//TODO
		Fluid fluid = FluidStackWrapper.byName(str); //ForgeRegistries.FLUIDS.getValue(new ResourceLocation(str)).getFluid();
		if (fluid == null) {
			return null;
		}
		
		return new FluidSelector(fluid);
	}
	
	public String toString() {
		//TODO
		return FluidStackWrapper.getName(fluid); //ForgeRegistries.FLUIDS.getKey(fluid).toString();
	}

	@Override
	public boolean isValid(Fluid f) {
		if(fluid==null||f==null){
			return false;
		}
		return fluid.equals(f);
	}
	
}
