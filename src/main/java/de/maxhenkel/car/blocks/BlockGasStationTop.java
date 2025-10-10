package de.maxhenkel.car.blocks;

import de.maxhenkel.corelib.block.DirectionalVoxelShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BlockGasStationTop extends BlockBase {

    public static VoxelShape SHAPE_NORTH_SOUTH = Block.box(2D, -16D, 5D, 14D, 15D, 11D);
    public static VoxelShape SHAPE_NEAST_WEST = Block.box(5D, -16D, 2D, 11D, 15D, 14D);
    public static VoxelShape SHAPE_SLAB = Block.box(0D, -16D, 0D, 16D, -8D, 16D);

    private static final DirectionalVoxelShape SHAPES = new DirectionalVoxelShape.Builder()
            .direction(Direction.NORTH,
                    SHAPE_NORTH_SOUTH,
                    SHAPE_SLAB
            )
            .direction(Direction.SOUTH,
                    SHAPE_NORTH_SOUTH,
                    SHAPE_SLAB
            )
            .direction(Direction.EAST,
                    SHAPE_NEAST_WEST,
                    SHAPE_SLAB
            )
            .direction(Direction.WEST,
                    SHAPE_NEAST_WEST,
                    SHAPE_SLAB
            ).build();

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public BlockGasStationTop(Properties properties) {
        super(properties.mapColor(MapColor.METAL).strength(4F).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK));
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        return ModBlocks.GAS_STATION.get().useItemOn(itemStack, level.getBlockState(blockPos.below()), level, blockPos.below(), player, interactionHand, blockHitResult);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean moving) {
        super.affectNeighborsAfterRemoval(state, level, pos, moving);

        BlockState stateDown = level.getBlockState(pos.below());
        if (stateDown.getBlock().equals(ModBlocks.GAS_STATION.get())) {
            level.destroyBlock(pos.below(), false);
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, ItemStack toolStack, boolean willHarvest, FluidState fluid) {
        BlockState stateDown = world.getBlockState(pos.below());
        stateDown.getBlock();
        if (stateDown.getBlock().equals(ModBlocks.GAS_STATION.get()) && !player.getAbilities().instabuild) {
            ModBlocks.GAS_STATION.get().playerDestroy(world, player, pos.below(), world.getBlockState(pos.below()), world.getBlockEntity(pos.below()), player.getMainHandItem());
        }
        return super.onDestroyedByPlayer(state, world, pos, player, toolStack, willHarvest, fluid);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player) {
        BlockState stateDown = level.getBlockState(pos.below());
        stateDown.getBlock();
        if (stateDown.getBlock().equals(ModBlocks.GAS_STATION.get())) {
            return ModBlocks.GAS_STATION.get().getCloneItemStack(level, pos.below(), stateDown, includeData, player);
        }
        return super.getCloneItemStack(level, pos, state, includeData, player);
    }
}
