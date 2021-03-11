package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

public class ModFluidTags {

    public static final ITag.INamedTag<Fluid> BLINDING = FluidTags.bind(new ResourceLocation(Main.MODID, "blinding").toString());

}
