package de.maxhenkel.car.blocks.fluid;

import de.maxhenkel.car.fluids.IEffectApplyable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class CarFluidBlock extends LiquidBlock {

    public CarFluidBlock(Properties properties, Supplier<? extends FlowingFluid> fluidSupplier) {
        super(fluidSupplier.get(), properties.noCollission().replaceable().sound(SoundType.EMPTY));
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (fluid instanceof IEffectApplyable) {
            ((IEffectApplyable) fluid).applyEffects(entityIn, state, worldIn, pos);
        }
        super.entityInside(state, worldIn, pos, entityIn);
    }
}
