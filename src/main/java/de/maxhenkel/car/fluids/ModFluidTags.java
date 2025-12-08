package de.maxhenkel.car.fluids;

import de.maxhenkel.car.CarMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class ModFluidTags {

    public static final TagKey<Fluid> BLINDING = TagKey.create(Registries.FLUID, Identifier.fromNamespaceAndPath(CarMod.MODID, "blinding"));

}
