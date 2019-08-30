package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class ModFluidTags {

    public static final Tag<Fluid> BLINDING = new FluidTags.Wrapper(new ResourceLocation(Main.MODID, "blinding"));

}
