package de.maxhenkel.car.fluids;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IEffectApplyable {

    void applyEffects(Entity entity, BlockState state, Level worldIn, BlockPos pos);

}
