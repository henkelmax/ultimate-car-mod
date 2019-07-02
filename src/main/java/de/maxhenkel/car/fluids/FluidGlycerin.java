package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidGlycerin extends Fluid{

	protected static final ResourceLocation STILL=new ResourceLocation(Main.MODID, "block/glycerin_still");
	protected static final ResourceLocation FLOWING=new ResourceLocation(Main.MODID, "block/glycerin_flowing");
	
	public FluidGlycerin() {
		super("glycerin", STILL, FLOWING);
		
	}

}
