package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidCanolaMethanolMix extends Fluid {

    protected static final ResourceLocation STILL = new ResourceLocation(Main.MODID, "block/canola_methanol_mix_still");
    protected static final ResourceLocation FLOWING = new ResourceLocation(Main.MODID, "block/canola_methanol_mix_flowing");

    public FluidCanolaMethanolMix() {
        super("canola_methanol_mix", STILL, FLOWING);

    }

}
