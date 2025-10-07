package de.maxhenkel.car.blocks.fluid;

import de.maxhenkel.car.fluids.IEffectApplyable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class CarFluidBlock extends LiquidBlock {

    public CarFluidBlock(Properties properties, Supplier<? extends FlowingFluid> fluidSupplier) {
        super(fluidSupplier.get(), properties.noCollision().replaceable().sound(SoundType.EMPTY));
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean b) {
        if (fluid instanceof IEffectApplyable) {
            ((IEffectApplyable) fluid).applyEffects(entity, state, level, pos);
        }
        super.entityInside(state, level, pos, entity, effectApplier, b);
    }
}
