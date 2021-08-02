package de.maxhenkel.car.blocks;

import de.maxhenkel.corelib.block.DirectionalVoxelShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockAsphaltSlope extends BlockSlope {

    private static final DirectionalVoxelShape SHAPES = new DirectionalVoxelShape.Builder()
            .direction(Direction.NORTH,
                    Block.box(0D, 0D, 0D, 16D, 1D, 16D),
                    Block.box(0D, 1D, 0D, 16D, 2D, 15D),
                    Block.box(0D, 2D, 0D, 16D, 3D, 14D),
                    Block.box(0D, 3D, 0D, 16D, 4D, 13D),
                    Block.box(0D, 4D, 0D, 16D, 5D, 12D),
                    Block.box(0D, 5D, 0D, 16D, 6D, 11D),
                    Block.box(0D, 6D, 0D, 16D, 7D, 10D),
                    Block.box(0D, 7D, 0D, 16D, 8D, 9D),
                    Block.box(0D, 8D, 0D, 16D, 9D, 8D),
                    Block.box(0D, 9D, 0D, 16D, 10D, 7D),
                    Block.box(0D, 10D, 0D, 16D, 11D, 6D),
                    Block.box(0D, 11D, 0D, 16D, 12D, 5D),
                    Block.box(0D, 12D, 0D, 16D, 13D, 4D),
                    Block.box(0D, 13D, 0D, 16D, 14D, 3D),
                    Block.box(0D, 14D, 0D, 16D, 15D, 2D),
                    Block.box(0D, 15D, 0D, 16D, 16D, 1D)
            )
            .direction(Direction.SOUTH,
                    Block.box(0D, 0D, 0D, 16D, 1D, 16D),
                    Block.box(0D, 1D, 1D, 16D, 2D, 16D),
                    Block.box(0D, 2D, 2D, 16D, 3D, 16D),
                    Block.box(0D, 3D, 3D, 16D, 4D, 16D),
                    Block.box(0D, 4D, 4D, 16D, 5D, 16D),
                    Block.box(0D, 5D, 5D, 16D, 6D, 16D),
                    Block.box(0D, 6D, 6D, 16D, 7D, 16D),
                    Block.box(0D, 7D, 7D, 16D, 8D, 16D),
                    Block.box(0D, 8D, 8D, 16D, 9D, 16D),
                    Block.box(0D, 9D, 9D, 16D, 10D, 16D),
                    Block.box(0D, 10D, 10D, 16D, 11D, 16D),
                    Block.box(0D, 11D, 11D, 16D, 12D, 16D),
                    Block.box(0D, 12D, 12D, 16D, 13D, 16D),
                    Block.box(0D, 13D, 13D, 16D, 14D, 16D),
                    Block.box(0D, 14D, 14D, 16D, 15D, 16D),
                    Block.box(0D, 15D, 15D, 16D, 16D, 16D)
            )
            .direction(Direction.EAST,
                    Block.box(0D, 0D, 0D, 16D, 1D, 16D),
                    Block.box(1D, 1D, 0D, 16D, 2D, 16D),
                    Block.box(2D, 2D, 0D, 16D, 3D, 16D),
                    Block.box(3D, 3D, 0D, 16D, 4D, 16D),
                    Block.box(4D, 4D, 0D, 16D, 5D, 16D),
                    Block.box(5D, 5D, 0D, 16D, 6D, 16D),
                    Block.box(6D, 6D, 0D, 16D, 7D, 16D),
                    Block.box(7D, 7D, 0D, 16D, 8D, 16D),
                    Block.box(8D, 8D, 0D, 16D, 9D, 16D),
                    Block.box(9D, 9D, 0D, 16D, 10D, 16D),
                    Block.box(10D, 10D, 0D, 16D, 11D, 16D),
                    Block.box(11D, 11D, 0D, 16D, 12D, 16D),
                    Block.box(12D, 12D, 0D, 16D, 13D, 16D),
                    Block.box(13D, 13D, 0D, 16D, 14D, 16D),
                    Block.box(14D, 14D, 0D, 16D, 15D, 16D),
                    Block.box(15D, 15D, 0D, 16D, 16D, 16D)
            )
            .direction(Direction.WEST,
                    Block.box(0D, 0D, 0D, 16D, 1D, 16D),
                    Block.box(0D, 1D, 0D, 15D, 2D, 16D),
                    Block.box(0D, 2D, 0D, 14D, 3D, 16D),
                    Block.box(0D, 3D, 0D, 13D, 4D, 16D),
                    Block.box(0D, 4D, 0D, 12D, 5D, 16D),
                    Block.box(0D, 5D, 0D, 11D, 6D, 16D),
                    Block.box(0D, 6D, 0D, 10D, 7D, 16D),
                    Block.box(0D, 7D, 0D, 9D, 8D, 16D),
                    Block.box(0D, 8D, 0D, 8D, 9D, 16D),
                    Block.box(0D, 9D, 0D, 7D, 10D, 16D),
                    Block.box(0D, 10D, 0D, 6D, 11D, 16D),
                    Block.box(0D, 11D, 0D, 5D, 12D, 16D),
                    Block.box(0D, 12D, 0D, 4D, 13D, 16D),
                    Block.box(0D, 13D, 0D, 3D, 14D, 16D),
                    Block.box(0D, 14D, 0D, 2D, 15D, 16D),
                    Block.box(0D, 15D, 0D, 1D, 16D, 16D)
            ).build();

    public BlockAsphaltSlope() {
        super("asphalt_slope");
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

}
