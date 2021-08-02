package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.material.Fluid;

public class ModFluidTags {

    public static final Tag.Named<Fluid> BLINDING = FluidTags.bind(new ResourceLocation(Main.MODID, "blinding").toString());

}
