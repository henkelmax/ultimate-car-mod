package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.corelib.block.DirectionalVoxelShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

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
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        return ModBlocks.GAS_STATION.use(worldIn.getBlockState(pos.below()), worldIn, pos.below(), player, handIn, hit);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }


    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
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
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        BlockState stateDown = world.getBlockState(pos.below());
        stateDown.getBlock();
        if (stateDown.getBlock().equals(ModBlocks.GAS_STATION) && !player.abilities.instabuild) {
            ModBlocks.GAS_STATION.playerDestroy(world, player, pos.below(), world.getBlockState(pos.below()), world.getBlockEntity(pos.below()), player.getMainHandItem());
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        BlockState stateDown = world.getBlockState(pos.below());
        stateDown.getBlock();
        if (stateDown.getBlock().equals(ModBlocks.GAS_STATION)) {
            return ModBlocks.GAS_STATION.getPickBlock(stateDown, target, world, pos.below(), player);
        }
        return super.getPickBlock(state, target, world, pos, player);
    }

}
