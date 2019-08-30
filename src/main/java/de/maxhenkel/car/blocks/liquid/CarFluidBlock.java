package de.maxhenkel.car.blocks.liquid;

import de.maxhenkel.car.fluids.CarFluid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CarFluidBlock extends FlowingFluidBlock {

    private CarFluid carFluid;

    public CarFluidBlock(CarFluid fluidIn) {
        super(fluidIn, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100F).noDrops());
        this.carFluid = fluidIn;
        setRegistryName(fluidIn.getRegistryName());
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        carFluid.applyEffects(entityIn, state, worldIn, pos);
        super.onEntityCollision(state, worldIn, pos, entityIn);
    }
}
