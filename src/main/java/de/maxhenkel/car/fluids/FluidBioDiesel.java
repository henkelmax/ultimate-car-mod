package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidBioDiesel extends Fluid {

    protected static final ResourceLocation STILL = new ResourceLocation(Main.MODID, "block/bio_diesel_still");
    protected static final ResourceLocation FLOWING = new ResourceLocation(Main.MODID, "block/bio_diesel_flowing");

    public FluidBioDiesel() {
        super("bio_diesel", STILL, FLOWING);

    }

}
