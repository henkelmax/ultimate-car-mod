package de.maxhenkel.car.fluids;

import de.maxhenkel.car.CarMod;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.fluid.FluidTintSources;

public class ModFluidModels {

    public static final FluidModel.Unbaked CANOLA_OIL_MODEL = new FluidModel.Unbaked(
            new Material(Identifier.fromNamespaceAndPath(CarMod.MODID, "block/canola_oil_still")),
            new Material(Identifier.fromNamespaceAndPath(CarMod.MODID, "block/canola_oil_flowing")),
            new Material(Identifier.fromNamespaceAndPath(CarMod.MODID, "block/water_overlay")),
            FluidTintSources.constant(0xFFFFFFFF)
    );

    public static final FluidModel.Unbaked METHANOL_MODEL = new FluidModel.Unbaked(
            new Material(Identifier.fromNamespaceAndPath(CarMod.MODID, "block/methanol_still")),
            new Material(Identifier.fromNamespaceAndPath(CarMod.MODID, "block/methanol_flowing")),
            new Material(Identifier.fromNamespaceAndPath(CarMod.MODID, "block/water_overlay")),
            FluidTintSources.constant(0xFFFFFFFF)
    );

    public static final FluidModel.Unbaked CANOLA_METHANOL_MIX_MODEL = new FluidModel.Unbaked(
            new Material(Identifier.fromNamespaceAndPath(CarMod.MODID, "block/canola_methanol_mix_still")),
            new Material(Identifier.fromNamespaceAndPath(CarMod.MODID, "block/canola_methanol_mix_flowing")),
            new Material(Identifier.fromNamespaceAndPath(CarMod.MODID, "block/water_overlay")),
            FluidTintSources.constant(0xFFFFFFFF)
    );

    public static final FluidModel.Unbaked GLYCERIN_MODEL = new FluidModel.Unbaked(
            new Material(Identifier.fromNamespaceAndPath(CarMod.MODID, "block/glycerin_still")),
            new Material(Identifier.fromNamespaceAndPath(CarMod.MODID, "block/glycerin_flowing")),
            new Material(Identifier.fromNamespaceAndPath(CarMod.MODID, "block/water_overlay")),
            FluidTintSources.constant(0xFFFFFFFF)
    );

    public static final FluidModel.Unbaked BIO_DIESEL_MODEL = new FluidModel.Unbaked(
            new Material(Identifier.fromNamespaceAndPath(CarMod.MODID, "block/bio_diesel_still")),
            new Material(Identifier.fromNamespaceAndPath(CarMod.MODID, "block/bio_diesel_flowing")),
            new Material(Identifier.fromNamespaceAndPath(CarMod.MODID, "block/water_overlay")),
            FluidTintSources.constant(0xFFFFFFFF)
    );

}
