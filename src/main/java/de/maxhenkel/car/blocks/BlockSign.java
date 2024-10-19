package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import de.maxhenkel.car.gui.ContainerSign;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.corelib.block.DirectionalVoxelShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BlockSign extends BlockBase implements EntityBlock, SimpleWaterloggedBlock {

    private static final VoxelShape SHAPE_NORTH_SOUTH = Block.box(0D, 3D, 7.5D, 16D, 13D, 8.5D);
    private static final VoxelShape SHAPE_EAST_WEST = Block.box(7.5D, 3D, 0D, 8.5D, 13D, 16D);
    private static final VoxelShape SHAPE_POST = Block.box(7.5D, 0D, 7.5D, 8.5D, 3D, 8.5D);

    private static final DirectionalVoxelShape SHAPES = new DirectionalVoxelShape.Builder()
            .direction(Direction.NORTH,
                    SHAPE_NORTH_SOUTH,
                    SHAPE_POST
            )
            .direction(Direction.SOUTH,
                    SHAPE_NORTH_SOUTH,
                    SHAPE_POST
            )
            .direction(Direction.EAST,
                    SHAPE_EAST_WEST,
                    SHAPE_POST
            )
            .direction(Direction.WEST,
                    SHAPE_EAST_WEST,
                    SHAPE_POST
            ).build();

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public BlockSign(Properties properties) {
        super(properties.mapColor(MapColor.METAL).strength(4F).sound(SoundType.METAL));

        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        BlockEntity te = level.getBlockEntity(blockPos);

        if (!(te instanceof TileEntitySign)) {
            return InteractionResult.FAIL;
        }
        TileEntitySign sign = (TileEntitySign) te;
        if (player instanceof ServerPlayer) {
            TileEntityContainerProvider.openGui((ServerPlayer) player, sign, (i, playerInventory, playerEntity) -> new ContainerSign(i, sign, playerInventory));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(WATERLOGGED, ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos pos1, BlockState state1, RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            tickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, level, tickAccess, pos, direction, pos1, state1, randomSource);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
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
        return new TileEntitySign(blockPos, blockState);
    }

}
