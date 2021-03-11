package de.maxhenkel.car.blocks.fluid;

import de.maxhenkel.car.fluids.IEffectApplyable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class CarFluidBlock extends FlowingFluidBlock {

    public CarFluidBlock(Supplier<? extends FlowingFluid> fluidSupplier) {
        super(fluidSupplier, Block.Properties.of(Material.WATER).noCollission().strength(100F).noDrops());
        setRegistryName(getFluid().getRegistryName());
    }

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        FlowingFluid fluid = getFluid();
        if (fluid instanceof IEffectApplyable) {
            ((IEffectApplyable) fluid).applyEffects(entityIn, state, worldIn, pos);
        }
        super.entityInside(state, worldIn, pos, entityIn);
    }
}
