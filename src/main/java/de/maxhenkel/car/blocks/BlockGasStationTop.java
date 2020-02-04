package de.maxhenkel.car.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.maxhenkel.car.Main;
import de.maxhenkel.tools.VoxelShapeTools;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
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
import java.util.Map;

public class BlockGasStationTop extends BlockBase {

    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    public BlockGasStationTop() {
        super(Properties.create(Material.IRON).hardnessAndResistance(4F).sound(SoundType.METAL));
        setRegistryName(new ResourceLocation(Main.MODID, "gas_station_top"));
        setDefaultState(stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        return ModBlocks.FUEL_STATION.onBlockActivated(worldIn.getBlockState(pos.down()), worldIn, pos.down(), player, handIn, hit);
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public static VoxelShape SHAPE_NORTH_SOUTH = Block.makeCuboidShape(2D, -16D, 5D, 14D, 15D, 11D);
    public static VoxelShape SHAPE_NEAST_WEST = Block.makeCuboidShape(5D, -16D, 2D, 11D, 15D, 14D);
    public static VoxelShape SHAPE_SLAB = Block.makeCuboidShape(0D, -16D, 0D, 16D, -8D, 16D);

    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH,
            VoxelShapeTools.combine(
                    SHAPE_NORTH_SOUTH,
                    SHAPE_SLAB
            ),
            Direction.SOUTH,
            VoxelShapeTools.combine(
                    SHAPE_NORTH_SOUTH,
                    SHAPE_SLAB
            ),
            Direction.EAST,
            VoxelShapeTools.combine(
                    SHAPE_NEAST_WEST,
                    SHAPE_SLAB
            ),
            Direction.WEST,
            VoxelShapeTools.combine(
                    SHAPE_NEAST_WEST,
                    SHAPE_SLAB
            )
    ));

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(state.get(FACING));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            super.onReplaced(state, worldIn, pos, newState, isMoving);

            BlockState stateDown = worldIn.getBlockState(pos.down());
            if (stateDown != null && stateDown.getBlock().equals(ModBlocks.FUEL_STATION)) {
                worldIn.destroyBlock(pos.down(), false);
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
        BlockState stateDown = world.getBlockState(pos.down());
        if (stateDown != null && stateDown.getBlock() != null && stateDown.getBlock().equals(ModBlocks.FUEL_STATION) && !player.abilities.isCreativeMode) {
            ModBlocks.FUEL_STATION.harvestBlock(world, player, pos.down(), world.getBlockState(pos.down()), world.getTileEntity(pos.down()), player.getHeldItemMainhand());
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        BlockState stateDown = world.getBlockState(pos.down());
        if (stateDown != null && stateDown.getBlock() != null && stateDown.getBlock().equals(ModBlocks.FUEL_STATION)) {
            return ModBlocks.FUEL_STATION.getPickBlock(stateDown, target, world, pos.down(), player);
        }
        return super.getPickBlock(state, target, world, pos, player);
    }

}
