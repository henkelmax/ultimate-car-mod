package de.maxhenkel.car.blocks;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTarSlopeFlat extends BlockSlope{

	private boolean isUpper;
	
	public BlockTarSlopeFlat(boolean isUpper) {
		super(isUpper ? "tar_slope_flat_upper" : "tar_slope_flat_lower");
		this.isUpper=isUpper;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
		EnumFacing facing=state.getValue(FACING);
		
		if(isUpper){
			upper(state, worldIn, pos, entityBox, collidingBoxes, facing);
		}else{
			lower(state, worldIn, pos, entityBox, collidingBoxes, facing);
		}
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if(isUpper){
			return FULL_BLOCK_AABB;
		}else{
			return new AxisAlignedBB(0, 0, 0, 1, 0.5F, 1);
		}
	}
	
	private void lower(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, EnumFacing facing){
		if(facing.equals(EnumFacing.NORTH)){
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 		0, 1, 0.125F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.125F, 	0, 1, 0.25F, 	0.75F));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.25F, 	0, 1, 0.375F, 	0.5F));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.375F, 	0, 1, 0.5F, 	0.25F));
		}else if(facing.equals(EnumFacing.SOUTH)){
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 		0.25F, 	1, 0.125F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.125F, 	0.5F, 	1, 0.25F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.25F, 	0.75F, 	1, 0.375F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.375F, 	1, 		1, 0.5F, 	1));
		}else if(facing.equals(EnumFacing.EAST)){
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.25F, 	0, 			0, 1, 0.125F,	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.5F, 	0.125F, 	0, 1, 0.25F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.75F, 	0.25F, 		0, 1, 0.375F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(1, 		0.375F, 	0, 1, 0.5F, 	1));
		}else if(facing.equals(EnumFacing.WEST)){
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 		0, 1, 		0.125F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.125F, 	0, 0.75F, 	0.25F, 		1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.25F, 	0, 0.5F, 	0.375F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.375F, 	0, 0.25F, 	0.5F, 		1));
		}
	}
	
	private void upper(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, EnumFacing facing){
		if(facing.equals(EnumFacing.NORTH)){
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.5F, 	0, 1, 0.625F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.625F, 	0, 1, 0.75F, 	0.75F));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.75F, 	0, 1, 0.875F, 	0.5F));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.875F, 	0, 1, 1, 		0.25F));
		}else if(facing.equals(EnumFacing.SOUTH)){
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.5F, 	0.25F, 	1, 0.625F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.625F, 	0.5F, 	1, 0.75F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.75F, 	0.75F, 	1, 0.875F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.875F, 	1, 		1, 1, 		1));
		}else if(facing.equals(EnumFacing.EAST)){
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.25F, 	0.5F, 		0, 1, 0.625F,	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.5F, 	0.625F, 	0, 1, 0.75F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.75F, 	0.75F, 		0, 1, 0.875F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(1, 		0.875F, 	0, 1, 1, 		1));
		}else if(facing.equals(EnumFacing.WEST)){
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.5F, 		0, 1, 	0.625F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.625F, 	0, 0.75F, 	0.75F, 		1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.75F, 	0, 0.5F, 	0.875F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.875F, 	0, 0.25F, 	1, 			1));
		}
	}

}
