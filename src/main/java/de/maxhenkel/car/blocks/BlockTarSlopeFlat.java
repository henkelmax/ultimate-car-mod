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
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        if (isUpper) {
            return UPPER_SHAPES.get(state.get(FACING));
        } else {
            return LOWER_SHAPES.get(state.get(FACING));
        }
    }

    private static final Map<Direction, VoxelShape> LOWER_SHAPES = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH,
            BlockTools.combine(
                    Block.makeCuboidShape(0D, 0D, 0D, 16D, 1D, 16D),
                    Block.makeCuboidShape(0D, 1D, 0D, 16D, 2D, 14D),
                    Block.makeCuboidShape(0D, 2D, 0D, 16D, 3D, 12D),
                    Block.makeCuboidShape(0D, 3D, 0D, 16D, 4D, 10D),
                    Block.makeCuboidShape(0D, 4D, 0D, 16D, 5D, 8D),
                    Block.makeCuboidShape(0D, 5D, 0D, 16D, 6D, 6D),
                    Block.makeCuboidShape(0D, 6D, 0D, 16D, 7D, 4D),
                    Block.makeCuboidShape(0D, 7D, 0D, 16D, 8D, 2D)
            ),
            Direction.SOUTH,
            BlockTools.combine(
                    Block.makeCuboidShape(0D, 0D, 0D, 16D, 1D, 16D),
                    Block.makeCuboidShape(0D, 1D, 2D, 16D, 2D, 16D),
                    Block.makeCuboidShape(0D, 2D, 4D, 16D, 3D, 16D),
                    Block.makeCuboidShape(0D, 3D, 6D, 16D, 4D, 16D),
                    Block.makeCuboidShape(0D, 4D, 8D, 16D, 5D, 16D),
                    Block.makeCuboidShape(0D, 5D, 10D, 16D, 6D, 16D),
                    Block.makeCuboidShape(0D, 6D, 12D, 16D, 7D, 16D),
                    Block.makeCuboidShape(0D, 7D, 14D, 16D, 8D, 16D)
            ),
            Direction.EAST,
            BlockTools.combine(
                    Block.makeCuboidShape(0D, 0D, 0D, 16D, 1D, 16D),
                    Block.makeCuboidShape(2D, 1D, 0D, 16D, 2D, 16D),
                    Block.makeCuboidShape(4D, 2D, 0D, 16D, 3D, 16D),
                    Block.makeCuboidShape(6D, 3D, 0D, 16D, 4D, 16D),
                    Block.makeCuboidShape(8D, 4D, 0D, 16D, 5D, 16D),
                    Block.makeCuboidShape(10D, 5D, 0D, 16D, 6D, 16D),
                    Block.makeCuboidShape(12D, 6D, 0D, 16D, 7D, 16D),
                    Block.makeCuboidShape(14D, 7D, 0D, 16D, 8D, 16D)
            ),
            Direction.WEST,
            BlockTools.combine(
                    Block.makeCuboidShape(0D, 0D, 0D, 16D, 1D, 16D),
                    Block.makeCuboidShape(0D, 1D, 0D, 14D, 2D, 16D),
                    Block.makeCuboidShape(0D, 2D, 0D, 12D, 3D, 16D),
                    Block.makeCuboidShape(0D, 3D, 0D, 10D, 4D, 16D),
                    Block.makeCuboidShape(0D, 4D, 0D, 8D, 5D, 16D),
                    Block.makeCuboidShape(0D, 5D, 0D, 6D, 6D, 16D),
                    Block.makeCuboidShape(0D, 6D, 0D, 4D, 7D, 16D),
                    Block.makeCuboidShape(0D, 7D, 0D, 2D, 8D, 16D)
            )
    ));
    private static final Map<Direction, VoxelShape> UPPER_SHAPES = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH,
            BlockTools.combine(
                    Block.makeCuboidShape(0D, 0D, 0D, 16D, 8D, 16D),
                    Block.makeCuboidShape(0D, 8D, 0D, 16D, 9D, 16D),
                    Block.makeCuboidShape(0D, 9D, 0D, 16D, 10D, 14D),
                    Block.makeCuboidShape(0D, 10D, 0D, 16D, 11D, 12D),
                    Block.makeCuboidShape(0D, 11D, 0D, 16D, 12D, 10D),
                    Block.makeCuboidShape(0D, 12D, 0D, 16D, 13D, 8D),
                    Block.makeCuboidShape(0D, 13D, 0D, 16D, 14D, 6D),
                    Block.makeCuboidShape(0D, 14D, 0D, 16D, 15D, 4D),
                    Block.makeCuboidShape(0D, 15D, 0D, 16D, 16D, 2D)
            ),
            Direction.SOUTH,
            BlockTools.combine(
                    Block.makeCuboidShape(0D, 0D, 0D, 16D, 8D, 16D),
                    Block.makeCuboidShape(0D, 8D, 0D, 16D, 9D, 16D),
                    Block.makeCuboidShape(0D, 9D, 2D, 16D, 10D, 16D),
                    Block.makeCuboidShape(0D, 10D, 4D, 16D, 11D, 16D),
                    Block.makeCuboidShape(0D, 11D, 6D, 16D, 12D, 16D),
                    Block.makeCuboidShape(0D, 12D, 8D, 16D, 13D, 16D),
                    Block.makeCuboidShape(0D, 13D, 10D, 16D, 14D, 16D),
                    Block.makeCuboidShape(0D, 14D, 12D, 16D, 15D, 16D),
                    Block.makeCuboidShape(0D, 15D, 14D, 16D, 16D, 16D)
            ),
            Direction.EAST,
            BlockTools.combine(
                    Block.makeCuboidShape(0D, 0D, 0D, 16D, 8D, 16D),
                    Block.makeCuboidShape(0D, 8D, 0D, 16D, 9D, 16D),
                    Block.makeCuboidShape(2D, 9D, 0D, 16D, 10D, 16D),
                    Block.makeCuboidShape(4D, 10D, 0D, 16D, 11D, 16D),
                    Block.makeCuboidShape(6D, 11D, 0D, 16D, 12D, 16D),
                    Block.makeCuboidShape(8D, 12D, 0D, 16D, 13D, 16D),
                    Block.makeCuboidShape(10D, 13D, 0D, 16D, 14D, 16D),
                    Block.makeCuboidShape(12D, 14D, 0D, 16D, 15D, 16D),
                    Block.makeCuboidShape(14D, 15D, 0D, 16D, 16D, 16D)
            ),
            Direction.WEST,
            BlockTools.combine(
                    Block.makeCuboidShape(0D, 0D, 0D, 16D, 8D, 16D),
                    Block.makeCuboidShape(0D, 8D, 0D, 16D, 9D, 16D),
                    Block.makeCuboidShape(0D, 9D, 0D, 14D, 10D, 16D),
                    Block.makeCuboidShape(0D, 10D, 0D, 12D, 11D, 16D),
                    Block.makeCuboidShape(0D, 11D, 0D, 10D, 12D, 16D),
                    Block.makeCuboidShape(0D, 12D, 0D, 8D, 13D, 16D),
                    Block.makeCuboidShape(0D, 13D, 0D, 6D, 14D, 16D),
                    Block.makeCuboidShape(0D, 14D, 0D, 4D, 15D, 16D),
                    Block.makeCuboidShape(0D, 15D, 0D, 2D, 16D, 16D)
            )
    ));

}
