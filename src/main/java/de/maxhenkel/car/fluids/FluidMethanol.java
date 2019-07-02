package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidMethanol extends Fluid{

	protected static final ResourceLocation STILL=new ResourceLocation(Main.MODID, "block/methanol_still");
	protected static final ResourceLocation FLOWING=new ResourceLocation(Main.MODID, "block/methanol_flowing");
	
	public FluidMethanol() {
		super("methanol", STILL, FLOWING);
		
	}

}
