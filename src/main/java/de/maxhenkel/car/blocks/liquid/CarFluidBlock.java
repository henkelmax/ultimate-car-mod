package de.maxhenkel.car.blocks.liquid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CarFluidBlock extends FlowingFluidBlock {

    public CarFluidBlock(FlowingFluid fluidIn) {
        super(fluidIn, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100F).noDrops());
        setRegistryName(fluidIn.getRegistryName());
        if (fluidIn == null) {
            throw new IllegalStateException("Fluid is null");
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityIn;
            if (!player.abilities.isCreativeMode) {
                player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 100, 0, true, false));
            }
        }
        super.onEntityCollision(state, worldIn, pos, entityIn);
    }
}
