package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import de.maxhenkel.car.gui.ContainerFluidExtractor;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.corelib.block.IItemBlock;
import de.maxhenkel.corelib.block.VoxelUtils;
import de.maxhenkel.corelib.blockentity.SimpleBlockEntityTicker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BlockFluidExtractor extends BlockBase implements EntityBlock, IItemBlock, SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.values());
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected BlockFluidExtractor() {
        super(Properties.of().mapColor(MapColor.METAL).strength(0.5F).sound(SoundType.METAL));

        registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(UP, false)
                .setValue(DOWN, false)
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return new SimpleBlockEntityTicker<>();
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (player.isShiftKeyDown()) {
            return InteractionResult.FAIL;
        }
        BlockEntity te = level.getBlockEntity(blockPos);
        if (!(te instanceof TileEntityFluidExtractor)) {
            return InteractionResult.FAIL;
        }
        TileEntityFluidExtractor fluidExtractor = (TileEntityFluidExtractor) te;
        if (player instanceof ServerPlayer) {
            TileEntityContainerProvider.openGui((ServerPlayer) player, fluidExtractor, (i, playerInventory, playerEntity) -> new ContainerFluidExtractor(i, fluidExtractor, playerInventory));
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getClickedFace().getOpposite();
        return getState(context.getLevel(), context.getClickedPos(), facing).setValue(FACING, facing);
    }

    private BlockState getState(Level world, BlockPos pos, Direction except) {
        FluidState ifluidstate = world.getFluidState(pos);
        return defaultBlockState()
                .setValue(UP, !except.equals(Direction.UP) && BlockFluidPipe.isConnectedTo(world, pos, Direction.UP))
                .setValue(DOWN, !except.equals(Direction.DOWN) && BlockFluidPipe.isConnectedTo(world, pos, Direction.DOWN))
                .setValue(NORTH, !except.equals(Direction.NORTH) && BlockFluidPipe.isConnectedTo(world, pos, Direction.NORTH))
                .setValue(SOUTH, !except.equals(Direction.SOUTH) && BlockFluidPipe.isConnectedTo(world, pos, Direction.SOUTH))
                .setValue(EAST, !except.equals(Direction.EAST) && BlockFluidPipe.isConnectedTo(world, pos, Direction.EAST))
                .setValue(WEST, !except.equals(Direction.WEST) && BlockFluidPipe.isConnectedTo(world, pos, Direction.WEST))
                .setValue(WATERLOGGED, ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos pos1, boolean b) {
        super.neighborChanged(state, world, pos, block, pos1, b);
        Direction facing = world.getBlockState(pos).getValue(FACING);
        world.setBlockAndUpdate(pos, getState(world, pos, facing).setValue(FACING, facing));
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rot) {
        BlockState newState = state.setValue(FACING, rot.rotate(state.getValue(FACING)));
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
        BlockState newState = state.rotate(mirror.getRotation(state.getValue(FACING)));
        newState = setDirection(newState, mirror.getRotation(Direction.NORTH).rotate(Direction.NORTH), state.getValue(NORTH));
        newState = setDirection(newState, mirror.getRotation(Direction.SOUTH).rotate(Direction.SOUTH), state.getValue(SOUTH));
        newState = setDirection(newState, mirror.getRotation(Direction.EAST).rotate(Direction.EAST), state.getValue(EAST));
        newState = setDirection(newState, mirror.getRotation(Direction.WEST).rotate(Direction.WEST), state.getValue(WEST));
        return newState;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, UP, DOWN, NORTH, SOUTH, EAST, WEST, WATERLOGGED);
    }

    public static final VoxelShape SHAPE_NORTH = Block.box(6D, 6D, 0D, 10D, 10D, 6D);
    public static final VoxelShape SHAPE_SOUTH = Block.box(6D, 6D, 10D, 10D, 10D, 16D);
    public static final VoxelShape SHAPE_EAST = Block.box(10D, 6D, 6D, 16D, 10D, 10D);
    public static final VoxelShape SHAPE_WEST = Block.box(0D, 6D, 6D, 6D, 10D, 10D);
    public static final VoxelShape SHAPE_UP = Block.box(6D, 10D, 6D, 10D, 16D, 10D);
    public static final VoxelShape SHAPE_DOWN = Block.box(6D, 0D, 6D, 10D, 6D, 10D);
    public static final VoxelShape SHAPE_CORE = Block.box(6D, 6D, 6D, 10D, 10D, 10D);
    public static final VoxelShape SHAPE_EXTRACTOR_NORTH = Block.box(1D, 1D, 0D, 15D, 15D, 1D);
    public static final VoxelShape SHAPE_EXTRACTOR_SOUTH = Block.box(1D, 1D, 15D, 15D, 15D, 16D);
    public static final VoxelShape SHAPE_EXTRACTOR_EAST = Block.box(15D, 1D, 1D, 16D, 15D, 15D);
    public static final VoxelShape SHAPE_EXTRACTOR_WEST = Block.box(0D, 1D, 1D, 1D, 15D, 15D);
    public static final VoxelShape SHAPE_EXTRACTOR_UP = Block.box(1D, 15D, 1D, 15D, 16D, 15D);
    public static final VoxelShape SHAPE_EXTRACTOR_DOWN = Block.box(1D, 0D, 1D, 15D, 1D, 15D);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        VoxelShape shape = SHAPE_CORE;

        if (state.getValue(NORTH)) {
            shape = VoxelUtils.combine(shape, SHAPE_NORTH);
        }
        if (state.getValue(SOUTH)) {
            shape = VoxelUtils.combine(shape, SHAPE_SOUTH);
        }
        if (state.getValue(EAST)) {
            shape = VoxelUtils.combine(shape, SHAPE_EAST);
        }
        if (state.getValue(WEST)) {
            shape = VoxelUtils.combine(shape, SHAPE_WEST);
        }
        if (state.getValue(UP)) {
            shape = VoxelUtils.combine(shape, SHAPE_UP);
        }
        if (state.getValue(DOWN)) {
            shape = VoxelUtils.combine(shape, SHAPE_DOWN);
        }
        switch (state.getValue(FACING)) {
            case NORTH:
                shape = VoxelUtils.combine(shape, SHAPE_EXTRACTOR_NORTH, SHAPE_NORTH);
                break;
            case SOUTH:
                shape = VoxelUtils.combine(shape, SHAPE_EXTRACTOR_SOUTH, SHAPE_SOUTH);
                break;
            case EAST:
                shape = VoxelUtils.combine(shape, SHAPE_EXTRACTOR_EAST, SHAPE_EAST);
                break;
            case WEST:
                shape = VoxelUtils.combine(shape, SHAPE_EXTRACTOR_WEST, SHAPE_WEST);
                break;
            case UP:
                shape = VoxelUtils.combine(shape, SHAPE_EXTRACTOR_UP, SHAPE_UP);
                break;
            case DOWN:
                shape = VoxelUtils.combine(shape, SHAPE_EXTRACTOR_DOWN, SHAPE_DOWN);
                break;
        }
        return shape;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TileEntityFluidExtractor(blockPos, blockState);
    }

}
