package de.maxhenkel.car.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.maxhenkel.tools.VoxelShapeTools;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import java.util.Map;

public class BlockAsphaltSlope extends BlockSlope {

    public BlockAsphaltSlope() {
        super("asphalt_slope");
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(state.get(FACING));
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return SHAPES.get(state.get(FACING));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(state.get(FACING));
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return SHAPES.get(state.get(FACING));
    }

    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH,
            VoxelShapeTools.combine(
                    Block.makeCuboidShape(0D, 0D, 0D, 16D, 1D, 16D),
                    Block.makeCuboidShape(0D, 1D, 0D, 16D, 2D, 15D),
                    Block.makeCuboidShape(0D, 2D, 0D, 16D, 3D, 14D),
                    Block.makeCuboidShape(0D, 3D, 0D, 16D, 4D, 13D),
                    Block.makeCuboidShape(0D, 4D, 0D, 16D, 5D, 12D),
                    Block.makeCuboidShape(0D, 5D, 0D, 16D, 6D, 11D),
                    Block.makeCuboidShape(0D, 6D, 0D, 16D, 7D, 10D),
                    Block.makeCuboidShape(0D, 7D, 0D, 16D, 8D, 9D),
                    Block.makeCuboidShape(0D, 8D, 0D, 16D, 9D, 8D),
                    Block.makeCuboidShape(0D, 9D, 0D, 16D, 10D, 7D),
                    Block.makeCuboidShape(0D, 10D, 0D, 16D, 11D, 6D),
                    Block.makeCuboidShape(0D, 11D, 0D, 16D, 12D, 5D),
                    Block.makeCuboidShape(0D, 12D, 0D, 16D, 13D, 4D),
                    Block.makeCuboidShape(0D, 13D, 0D, 16D, 14D, 3D),
                    Block.makeCuboidShape(0D, 14D, 0D, 16D, 15D, 2D),
                    Block.makeCuboidShape(0D, 15D, 0D, 16D, 16D, 1D)
            ),
            Direction.SOUTH,
            VoxelShapeTools.combine(
                    Block.makeCuboidShape(0D, 0D, 0D, 16D, 1D, 16D),
                    Block.makeCuboidShape(0D, 1D, 1D, 16D, 2D, 16D),
                    Block.makeCuboidShape(0D, 2D, 2D, 16D, 3D, 16D),
                    Block.makeCuboidShape(0D, 3D, 3D, 16D, 4D, 16D),
                    Block.makeCuboidShape(0D, 4D, 4D, 16D, 5D, 16D),
                    Block.makeCuboidShape(0D, 5D, 5D, 16D, 6D, 16D),
                    Block.makeCuboidShape(0D, 6D, 6D, 16D, 7D, 16D),
                    Block.makeCuboidShape(0D, 7D, 7D, 16D, 8D, 16D),
                    Block.makeCuboidShape(0D, 8D, 8D, 16D, 9D, 16D),
                    Block.makeCuboidShape(0D, 9D, 9D, 16D, 10D, 16D),
                    Block.makeCuboidShape(0D, 10D, 10D, 16D, 11D, 16D),
                    Block.makeCuboidShape(0D, 11D, 11D, 16D, 12D, 16D),
                    Block.makeCuboidShape(0D, 12D, 12D, 16D, 13D, 16D),
                    Block.makeCuboidShape(0D, 13D, 13D, 16D, 14D, 16D),
                    Block.makeCuboidShape(0D, 14D, 14D, 16D, 15D, 16D),
                    Block.makeCuboidShape(0D, 15D, 15D, 16D, 16D, 16D)
            ),
            Direction.EAST,
            VoxelShapeTools.combine(
                    Block.makeCuboidShape(0D, 0D, 0D, 16D, 1D, 16D),
                    Block.makeCuboidShape(1D, 1D, 0D, 16D, 2D, 16D),
                    Block.makeCuboidShape(2D, 2D, 0D, 16D, 3D, 16D),
                    Block.makeCuboidShape(3D, 3D, 0D, 16D, 4D, 16D),
                    Block.makeCuboidShape(4D, 4D, 0D, 16D, 5D, 16D),
                    Block.makeCuboidShape(5D, 5D, 0D, 16D, 6D, 16D),
                    Block.makeCuboidShape(6D, 6D, 0D, 16D, 7D, 16D),
                    Block.makeCuboidShape(7D, 7D, 0D, 16D, 8D, 16D),
                    Block.makeCuboidShape(8D, 8D, 0D, 16D, 9D, 16D),
                    Block.makeCuboidShape(9D, 9D, 0D, 16D, 10D, 16D),
                    Block.makeCuboidShape(10D, 10D, 0D, 16D, 11D, 16D),
                    Block.makeCuboidShape(11D, 11D, 0D, 16D, 12D, 16D),
                    Block.makeCuboidShape(12D, 12D, 0D, 16D, 13D, 16D),
                    Block.makeCuboidShape(13D, 13D, 0D, 16D, 14D, 16D),
                    Block.makeCuboidShape(14D, 14D, 0D, 16D, 15D, 16D),
                    Block.makeCuboidShape(15D, 15D, 0D, 16D, 16D, 16D)
            ),
            Direction.WEST,
            VoxelShapeTools.combine(
                    Block.makeCuboidShape(0D, 0D, 0D, 16D, 1D, 16D),
                    Block.makeCuboidShape(0D, 1D, 0D, 15D, 2D, 16D),
                    Block.makeCuboidShape(0D, 2D, 0D, 14D, 3D, 16D),
                    Block.makeCuboidShape(0D, 3D, 0D, 13D, 4D, 16D),
                    Block.makeCuboidShape(0D, 4D, 0D, 12D, 5D, 16D),
                    Block.makeCuboidShape(0D, 5D, 0D, 11D, 6D, 16D),
                    Block.makeCuboidShape(0D, 6D, 0D, 10D, 7D, 16D),
                    Block.makeCuboidShape(0D, 7D, 0D, 9D, 8D, 16D),
                    Block.makeCuboidShape(0D, 8D, 0D, 8D, 9D, 16D),
                    Block.makeCuboidShape(0D, 9D, 0D, 7D, 10D, 16D),
                    Block.makeCuboidShape(0D, 10D, 0D, 6D, 11D, 16D),
                    Block.makeCuboidShape(0D, 11D, 0D, 5D, 12D, 16D),
                    Block.makeCuboidShape(0D, 12D, 0D, 4D, 13D, 16D),
                    Block.makeCuboidShape(0D, 13D, 0D, 3D, 14D, 16D),
                    Block.makeCuboidShape(0D, 14D, 0D, 2D, 15D, 16D),
                    Block.makeCuboidShape(0D, 15D, 0D, 1D, 16D, 16D)
            )
    ));

}
