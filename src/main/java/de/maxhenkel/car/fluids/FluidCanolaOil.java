package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidCanolaOil extends Fluid{

	protected static final ResourceLocation STILL=new ResourceLocation(Main.MODID, "block/canola_oil_still");
	protected static final ResourceLocation FLOWING=new ResourceLocation(Main.MODID, "block/canola_oil_flowing");
	
	public FluidCanolaOil() {
		super("canola_oil", STILL, FLOWING);
		
	}

}
