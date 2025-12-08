package de.maxhenkel.car;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageType;

public class DamageSourceCar {

    public static final ResourceKey<DamageType> DAMAGE_CAR_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(CarMod.MODID, "hit_car"));

}
