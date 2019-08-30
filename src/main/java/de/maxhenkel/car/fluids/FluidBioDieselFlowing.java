package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidBioDieselFlowing extends CarFluid.Flowing {

    public FluidBioDieselFlowing() {
        super(new ResourceLocation(Main.MODID, "bio_diesel_flowing"), new ResourceLocation(Main.MODID, "block/bio_diesel_still"), new ResourceLocation(Main.MODID, "block/bio_diesel_flowing"));
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, World worldIn, BlockPos pos) {
        ModFluids.BIO_DIESEL.applyEffects(entity, state, worldIn, pos);
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
        return ModFluids.BIO_DIESEL;
    }
}
