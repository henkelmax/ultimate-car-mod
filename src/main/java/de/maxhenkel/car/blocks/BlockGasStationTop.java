package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.corelib.block.DirectionalVoxelShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
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

    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    public BlockGasStationTop() {
        super(Properties.of(Material.METAL).strength(4F).sound(SoundType.METAL));
        setRegistryName(new ResourceLocation(Main.MODID, "gas_station_top"));
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        return ModBlocks.GAS_STATION.use(worldIn.getBlockState(pos.below()), worldIn, pos.below(), player, handIn, hit);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
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
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            super.onRemove(state, worldIn, pos, newState, isMoving);

            BlockState stateDown = worldIn.getBlockState(pos.below());
            if (stateDown.getBlock().equals(ModBlocks.GAS_STATION)) {
                worldIn.destroyBlock(pos.below(), false);
            }
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean removedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        BlockState stateDown = world.getBlockState(pos.below());
        stateDown.getBlock();
        if (stateDown.getBlock().equals(ModBlocks.GAS_STATION) && !player.getAbilities().instabuild) {
            ModBlocks.GAS_STATION.playerDestroy(world, player, pos.below(), world.getBlockState(pos.below()), world.getBlockEntity(pos.below()), player.getMainHandItem());
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        BlockState stateDown = world.getBlockState(pos.below());
        stateDown.getBlock();
        if (stateDown.getBlock().equals(ModBlocks.GAS_STATION)) {
            return ModBlocks.GAS_STATION.getPickBlock(stateDown, target, world, pos.below(), player);
        }
        return super.getPickBlock(state, target, world, pos, player);
    }

}
