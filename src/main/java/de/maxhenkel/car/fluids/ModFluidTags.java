package de.maxhenkel.car.fluids;

import de.maxhenkel.car.CarMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class ModFluidTags {

    public static final TagKey<Fluid> BLINDING = TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "blinding"));

}
