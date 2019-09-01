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
import net.minecraftforge.fluids.FluidAttributes;

public class FluidGlycerinFlowing extends CarFluid.Flowing {

    public FluidGlycerinFlowing() {
        super(new ResourceLocation(Main.MODID, "glycerin_flowing"), new ResourceLocation(Main.MODID, "block/glycerin_still"), new ResourceLocation(Main.MODID, "block/glycerin_flowing"));
    }

    @Override
    protected FluidAttributes.Builder build() {
        return super.build().density(5000).viscosity(100);
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, World worldIn, BlockPos pos) {
        ModFluids.GLYCERIN.applyEffects(entity, state, worldIn, pos);
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
        return ModFluids.GLYCERIN;
    }
}
