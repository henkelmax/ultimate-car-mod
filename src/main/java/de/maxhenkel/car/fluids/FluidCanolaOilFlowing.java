package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class FluidCanolaOilFlowing extends CarFluid.Flowing {

    public FluidCanolaOilFlowing() {
        super(new ResourceLocation(Main.MODID, "canola_oil_flowing"), new ResourceLocation(Main.MODID, "block/canola_oil_still"), new ResourceLocation(Main.MODID, "block/canola_oil_flowing"));
    }

    @Override
    public Item getFilledBucket() {
        return ModItems.CANOLA_OIL_BUCKET;
    }

    @Override
    public Block getFluidBlock() {
        return ModBlocks.CANOLA_OIL;
    }

    @Override
    public Fluid getEquivalent() {
        return ModFluids.CANOLA_OIL;
    }
}
