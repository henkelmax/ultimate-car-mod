package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class ModFluidTags {

    public static final TagKey<Fluid> BLINDING = TagKey.create(Registry.FLUID_REGISTRY, new ResourceLocation(Main.MODID, "blinding"));

}
