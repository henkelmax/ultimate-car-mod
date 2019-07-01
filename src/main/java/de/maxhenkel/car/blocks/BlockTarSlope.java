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

public class BlockTarSlope extends BlockSlope {

    public BlockTarSlope() {
        super("tar_slope");

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
