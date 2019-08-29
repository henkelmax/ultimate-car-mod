package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class FluidGlycerin extends CarFluid.Source {

    public FluidGlycerin() {
        super(new ResourceLocation(Main.MODID, "glycerin"), new ResourceLocation(Main.MODID, "block/glycerin_still"), new ResourceLocation(Main.MODID, "block/glycerin_flowing"));
    }

    @Override
    public Item getFilledBucket() {
        return ModItems.GLYCERIN_BUCKET;
    }

    @Override
    public Block getFluidBlock() {
        return ModBlocks.GLYCERIN;
    }

    @Override
    public Fluid getEquivalent() {
        return ModFluids.GLYCERIN_FLOWING;
    }
}
