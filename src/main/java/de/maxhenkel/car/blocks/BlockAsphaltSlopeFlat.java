package de.maxhenkel.car.blocks;

import de.maxhenkel.corelib.block.DirectionalVoxelShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockAsphaltSlopeFlat extends BlockSlope {

    private static final DirectionalVoxelShape LOWER_SHAPES = new DirectionalVoxelShape.Builder()
            .direction(Direction.NORTH,
                    Block.box(0D, 0D, 0D, 16D, 1D, 16D),
                    Block.box(0D, 1D, 0D, 16D, 2D, 14D),
                    Block.box(0D, 2D, 0D, 16D, 3D, 12D),
                    Block.box(0D, 3D, 0D, 16D, 4D, 10D),
                    Block.box(0D, 4D, 0D, 16D, 5D, 8D),
                    Block.box(0D, 5D, 0D, 16D, 6D, 6D),
                    Block.box(0D, 6D, 0D, 16D, 7D, 4D),
                    Block.box(0D, 7D, 0D, 16D, 8D, 2D)
            )
            .direction(Direction.SOUTH,
                    Block.box(0D, 0D, 0D, 16D, 1D, 16D),
                    Block.box(0D, 1D, 2D, 16D, 2D, 16D),
                    Block.box(0D, 2D, 4D, 16D, 3D, 16D),
                    Block.box(0D, 3D, 6D, 16D, 4D, 16D),
                    Block.box(0D, 4D, 8D, 16D, 5D, 16D),
                    Block.box(0D, 5D, 10D, 16D, 6D, 16D),
                    Block.box(0D, 6D, 12D, 16D, 7D, 16D),
                    Block.box(0D, 7D, 14D, 16D, 8D, 16D)
            ).direction(Direction.EAST,
                    Block.box(0D, 0D, 0D, 16D, 1D, 16D),
                    Block.box(2D, 1D, 0D, 16D, 2D, 16D),
                    Block.box(4D, 2D, 0D, 16D, 3D, 16D),
                    Block.box(6D, 3D, 0D, 16D, 4D, 16D),
                    Block.box(8D, 4D, 0D, 16D, 5D, 16D),
                    Block.box(10D, 5D, 0D, 16D, 6D, 16D),
                    Block.box(12D, 6D, 0D, 16D, 7D, 16D),
                    Block.box(14D, 7D, 0D, 16D, 8D, 16D)
            )
            .direction(Direction.WEST,
                    Block.box(0D, 0D, 0D, 16D, 1D, 16D),
                    Block.box(0D, 1D, 0D, 14D, 2D, 16D),
                    Block.box(0D, 2D, 0D, 12D, 3D, 16D),
                    Block.box(0D, 3D, 0D, 10D, 4D, 16D),
                    Block.box(0D, 4D, 0D, 8D, 5D, 16D),
                    Block.box(0D, 5D, 0D, 6D, 6D, 16D),
                    Block.box(0D, 6D, 0D, 4D, 7D, 16D),
                    Block.box(0D, 7D, 0D, 2D, 8D, 16D)
            ).build();

    private static final DirectionalVoxelShape UPPER_SHAPES = new DirectionalVoxelShape.Builder()
            .direction(Direction.NORTH,
                    Block.box(0D, 0D, 0D, 16D, 8D, 16D),
                    Block.box(0D, 8D, 0D, 16D, 9D, 16D),
                    Block.box(0D, 9D, 0D, 16D, 10D, 14D),
                    Block.box(0D, 10D, 0D, 16D, 11D, 12D),
                    Block.box(0D, 11D, 0D, 16D, 12D, 10D),
                    Block.box(0D, 12D, 0D, 16D, 13D, 8D),
                    Block.box(0D, 13D, 0D, 16D, 14D, 6D),
                    Block.box(0D, 14D, 0D, 16D, 15D, 4D),
                    Block.box(0D, 15D, 0D, 16D, 16D, 2D)
            )
            .direction(Direction.SOUTH,
                    Block.box(0D, 0D, 0D, 16D, 8D, 16D),
                    Block.box(0D, 8D, 0D, 16D, 9D, 16D),
                    Block.box(0D, 9D, 2D, 16D, 10D, 16D),
                    Block.box(0D, 10D, 4D, 16D, 11D, 16D),
                    Block.box(0D, 11D, 6D, 16D, 12D, 16D),
                    Block.box(0D, 12D, 8D, 16D, 13D, 16D),
                    Block.box(0D, 13D, 10D, 16D, 14D, 16D),
                    Block.box(0D, 14D, 12D, 16D, 15D, 16D),
                    Block.box(0D, 15D, 14D, 16D, 16D, 16D)
            )
            .direction(Direction.EAST,
                    Block.box(0D, 0D, 0D, 16D, 8D, 16D),
                    Block.box(0D, 8D, 0D, 16D, 9D, 16D),
                    Block.box(2D, 9D, 0D, 16D, 10D, 16D),
                    Block.box(4D, 10D, 0D, 16D, 11D, 16D),
                    Block.box(6D, 11D, 0D, 16D, 12D, 16D),
                    Block.box(8D, 12D, 0D, 16D, 13D, 16D),
                    Block.box(10D, 13D, 0D, 16D, 14D, 16D),
                    Block.box(12D, 14D, 0D, 16D, 15D, 16D),
                    Block.box(14D, 15D, 0D, 16D, 16D, 16D)
            )
            .direction(Direction.WEST,
                    Block.box(0D, 0D, 0D, 16D, 8D, 16D),
                    Block.box(0D, 8D, 0D, 16D, 9D, 16D),
                    Block.box(0D, 9D, 0D, 14D, 10D, 16D),
                    Block.box(0D, 10D, 0D, 12D, 11D, 16D),
                    Block.box(0D, 11D, 0D, 10D, 12D, 16D),
                    Block.box(0D, 12D, 0D, 8D, 13D, 16D),
                    Block.box(0D, 13D, 0D, 6D, 14D, 16D),
                    Block.box(0D, 14D, 0D, 4D, 15D, 16D),
                    Block.box(0D, 15D, 0D, 2D, 16D, 16D)
            ).build();

    private boolean isUpper;

    public BlockAsphaltSlopeFlat(boolean isUpper) {
        super(isUpper ? "asphalt_slope_flat_upper" : "asphalt_slope_flat_lower");
        this.isUpper = isUpper;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        if (isUpper) {
            return UPPER_SHAPES.get(state.getValue(FACING));
        } else {
            return LOWER_SHAPES.get(state.getValue(FACING));
        }
    }

}
