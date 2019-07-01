package de.maxhenkel.car.blocks;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.maxhenkel.tools.BlockTools;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BlockTarSlopeFlat extends BlockSlope {

    private boolean isUpper;

    public BlockTarSlopeFlat(boolean isUpper) {
        super(isUpper ? "tar_slope_flat_upper" : "tar_slope_flat_lower");
        this.isUpper = isUpper;
    }

    //TODO fix shapes
	/*private void lower(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
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
	}*/


    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        if (isUpper) {
            return UPPER_SHAPES.get(state.get(FACING));
        } else {
            return UPPER_SHAPES.get(state.get(FACING));
        }
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos) {
        if (isUpper) {
            return UPPER_SHAPES.get(state.get(FACING));
        } else {
            return UPPER_SHAPES.get(state.get(FACING));
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        if (isUpper) {
            return UPPER_SHAPES.get(state.get(FACING));
        } else {
            return UPPER_SHAPES.get(state.get(FACING));
        }
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader reader, BlockPos pos) {
        if (isUpper) {
            return UPPER_SHAPES.get(state.get(FACING));
        } else {
            return UPPER_SHAPES.get(state.get(FACING));
        }
    }

    private static final Map<Direction, VoxelShape> UPPER_SHAPES = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH,
            BlockTools.combine(
                    Block.makeCuboidShape(0D, 0D, 0D, 16D, 4D, 16D),
                    Block.makeCuboidShape(0D, 4D, 0D, 16D, 8D, 12D),
                    Block.makeCuboidShape(0D, 8D, 0D, 16D, 12D, 8D),
                    Block.makeCuboidShape(0D, 12D, 0D, 16D, 16D, 4D)
            ),
            Direction.SOUTH,
            BlockTools.combine(
                    Block.makeCuboidShape(0D, 0D, 4D, 16D, 4D, 16D),
                    Block.makeCuboidShape(0D, 4D, 8D, 16D, 8D, 16D),
                    Block.makeCuboidShape(0D, 8D, 12D, 16D, 12D, 16D),
                    Block.makeCuboidShape(0D, 12D, 16D, 16D, 16D, 16D)
            ),
            Direction.EAST,
            BlockTools.combine(
                    Block.makeCuboidShape(4D, 0D, 0D, 16D, 4D, 16D),
                    Block.makeCuboidShape(0D, 4D, 0D, 12D, 8D, 16D),
                    Block.makeCuboidShape(12D, 8D, 0D, 16D, 12D, 16D),
                    Block.makeCuboidShape(16D, 12D, 0D, 16D, 16D, 16D)
            ),
            Direction.WEST,
            BlockTools.combine(
                    Block.makeCuboidShape(0D, 0D, 0D, 16D, 4D, 16D),
                    Block.makeCuboidShape(0D, 4D, 0D, 12D, 8D, 16D),
                    Block.makeCuboidShape(0D, 8D, 0D, 8D, 12D, 16D),
                    Block.makeCuboidShape(0D, 12D, 0D, 4D, 16D, 16D)
            )
    ));

}
