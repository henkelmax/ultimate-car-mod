package de.maxhenkel.car.fluids;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IEffectApplyable {

    void applyEffects(Entity entity, BlockState state, World worldIn, BlockPos pos);

}
