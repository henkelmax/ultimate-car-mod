package de.maxhenkel.car.blocks;

import de.maxhenkel.corelib.block.VoxelUtils;
import de.maxhenkel.corelib.fluid.FluidUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;

import javax.annotation.Nullable;

public class BlockFluidPipe extends BlockBase implements SimpleWaterloggedBlock {

    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public BlockFluidPipe(Properties properties) {
        super(properties.mapColor(MapColor.METAL).strength(0.25F).sound(SoundType.METAL));

        registerDefaultState(stateDefinition.any()
                .setValue(UP, false)
                .setValue(DOWN, false)
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false)
                .setValue(WATERLOGGED, false)
        );
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getState(context.getLevel(), context.getClickedPos());
    }

    private BlockState getState(Level world, BlockPos pos) {
        FluidState ifluidstate = world.getFluidState(pos);
        return defaultBlockState()
                .setValue(UP, isConnectedTo(world, pos, Direction.UP))
                .setValue(DOWN, isConnectedTo(world, pos, Direction.DOWN))
                .setValue(NORTH, isConnectedTo(world, pos, Direction.NORTH))
                .setValue(SOUTH, isConnectedTo(world, pos, Direction.SOUTH))
                .setValue(EAST, isConnectedTo(world, pos, Direction.EAST))
                .setValue(WEST, isConnectedTo(world, pos, Direction.WEST))
                .setValue(WATERLOGGED, ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos pos1, BlockState state1, RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            tickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, level, tickAccess, pos, direction, pos1, state1, randomSource);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean b) {
        super.neighborChanged(state, level, pos, block, orientation, b);
        level.setBlockAndUpdate(pos, getState(level, pos));
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rot) {
        BlockState newState = state;
        newState = setDirection(newState, rot.rotate(Direction.NORTH), state.getValue(NORTH));
        newState = setDirection(newState, rot.rotate(Direction.SOUTH), state.getValue(SOUTH));
        newState = setDirection(newState, rot.rotate(Direction.EAST), state.getValue(EAST));
        newState = setDirection(newState, rot.rotate(Direction.WEST), state.getValue(WEST));
        return newState;
    }

    private BlockState setDirection(BlockState state, Direction direction, boolean connected) {
        return switch (direction) {
            case NORTH -> state.setValue(NORTH, connected);
            case SOUTH -> state.setValue(SOUTH, connected);
            case EAST -> state.setValue(EAST, connected);
            case WEST -> state.setValue(WEST, connected);
            case UP -> state.setValue(UP, connected);
            case DOWN -> state.setValue(DOWN, connected);
        };
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        BlockState newState = state;
        newState = setDirection(newState, mirror.getRotation(Direction.NORTH).rotate(Direction.NORTH), state.getValue(NORTH));
        newState = setDirection(newState, mirror.getRotation(Direction.SOUTH).rotate(Direction.SOUTH), state.getValue(SOUTH));
        newState = setDirection(newState, mirror.getRotation(Direction.EAST).rotate(Direction.EAST), state.getValue(EAST));
        newState = setDirection(newState, mirror.getRotation(Direction.WEST).rotate(Direction.WEST), state.getValue(WEST));
        return newState;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST, WATERLOGGED);
    }

    public static final VoxelShape SHAPE_NORTH = Block.box(6D, 6D, 0D, 10D, 10D, 6D);
    public static final VoxelShape SHAPE_SOUTH = Block.box(6D, 6D, 10D, 10D, 10D, 16D);
    public static final VoxelShape SHAPE_EAST = Block.box(10D, 6D, 6D, 16D, 10D, 10D);
    public static final VoxelShape SHAPE_WEST = Block.box(0D, 6D, 6D, 6D, 10D, 10D);
    public static final VoxelShape SHAPE_UP = Block.box(6D, 10D, 6D, 10D, 16D, 10D);
    public static final VoxelShape SHAPE_DOWN = Block.box(6D, 0D, 6D, 10D, 6D, 10D);
    public static final VoxelShape SHAPE_CORE = Block.box(6D, 6D, 6D, 10D, 10D, 10D);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        VoxelShape shape = SHAPE_CORE;
        if (state.getValue(UP)) {
            shape = VoxelUtils.combine(shape, SHAPE_UP);
        }

        if (state.getValue(DOWN)) {
            shape = VoxelUtils.combine(shape, SHAPE_DOWN);
        }

        if (state.getValue(SOUTH)) {
            shape = VoxelUtils.combine(shape, SHAPE_SOUTH);
        }

        if (state.getValue(NORTH)) {
            shape = VoxelUtils.combine(shape, SHAPE_NORTH);
        }

        if (state.getValue(EAST)) {
            shape = VoxelUtils.combine(shape, SHAPE_EAST);
        }

        if (state.getValue(WEST)) {
            shape = VoxelUtils.combine(shape, SHAPE_WEST);
        }

        return shape;
    }

    public static boolean isConnectedTo(Level world, BlockPos pos, Direction facing) {
        BlockState state = world.getBlockState(pos.relative(facing));

        if (state.getBlock().equals(ModBlocks.FLUID_PIPE.get()) || state.getBlock().equals(ModBlocks.FLUID_EXTRACTOR.get())) {
            return true;
        }

        return world.getCapability(Capabilities.Fluid.BLOCK, pos.relative(facing), facing.getOpposite()) != null;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

}