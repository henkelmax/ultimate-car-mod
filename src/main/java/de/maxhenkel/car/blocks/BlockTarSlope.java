package de.maxhenkel.car.blocks;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTarSlope extends BlockSlope{

	public BlockTarSlope() {
		super("tar_slope");
		
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn) {
		EnumFacing facing=state.getValue(FACING);
		
		if(facing.equals(EnumFacing.NORTH)){
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 		0, 1, 0.25F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.25F, 	0, 1, 0.5F, 	0.75F));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.5F, 	0, 1, 0.75F, 	0.5F));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.75F, 	0, 1, 1, 		0.25F));
		}else if(facing.equals(EnumFacing.SOUTH)){
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 		0.25F, 	1, 0.25F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.25F, 	0.5F, 	1, 0.5F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.5F, 	0.75F, 	1, 0.75F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.75F, 	1, 		1, 1, 		1));
		}else if(facing.equals(EnumFacing.EAST)){
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.25F, 	0, 		0, 1, 0.25F,	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.5F, 	0.25F, 	0, 1, 0.5F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.75F, 	0.5F, 	0, 1, 0.75F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(1, 		0.75F, 	0, 1, 1, 		1));
		}else if(facing.equals(EnumFacing.WEST)){
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 		0, 1, 		0.25F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.25F, 	0, 0.75F, 	0.5F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.5F, 	0, 0.5F, 	0.75F, 	1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.75F, 	0, 0.25F, 	1, 		1));
		}	
	}

}
