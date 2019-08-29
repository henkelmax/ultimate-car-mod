package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class FluidBioDiesel extends CarFluid.Source {

    public FluidBioDiesel() {
        super(new ResourceLocation(Main.MODID, "bio_diesel"), new ResourceLocation(Main.MODID, "block/bio_diesel_still"), new ResourceLocation(Main.MODID, "block/bio_diesel_flowing"));
    }

    @Override
    public Item getFilledBucket() {
        return ModItems.BIO_DIESEL_BUCKET;
    }

    @Override
    public Block getFluidBlock() {
        return ModBlocks.BIO_DIESEL;
    }

    @Override
    public Fluid getEquivalent() {
        return ModFluids.BIO_DIESEL_FLOWING;
    }
}
