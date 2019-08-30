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

public class FluidCanolaOil extends CarFluid.Source {

    public FluidCanolaOil() {
        super(new ResourceLocation(Main.MODID, "canola_oil"), new ResourceLocation(Main.MODID, "block/canola_oil_still"), new ResourceLocation(Main.MODID, "block/canola_oil_flowing"));
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, World worldIn, BlockPos pos) {

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
        return ModFluids.CANOLA_OIL_FLOWING;
    }
}
