package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.tileentity.TileEntityCable;
import de.maxhenkel.corelib.block.IItemBlock;
import de.maxhenkel.corelib.block.VoxelUtils;
import de.maxhenkel.corelib.blockentity.SimpleBlockEntityTicker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import javax.annotation.Nullable;

public class BlockCable extends BlockBase implements EntityBlock, IItemBlock, SimpleWaterloggedBlock {

    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected BlockCable() {
        super(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_GRAY).strength(0.25F).sound(SoundType.WOOL));

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

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return new SimpleBlockEntityTicker<>();
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
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos pos1, boolean b) {
        super.neighborChanged(state, world, pos, block, pos1, b);
        world.setBlockAndUpdate(pos, getState(world, pos));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST, WATERLOGGED);
    }

    public static final VoxelShape SHAPE_NORTH = Block.box(6.5D, 6.5D, 0D, 9.5D, 9.5D, 6.5D);
    public static final VoxelShape SHAPE_SOUTH = Block.box(6.5D, 6.5D, 9.5D, 9.5D, 9.5D, 16D);
    public static final VoxelShape SHAPE_EAST = Block.box(9.5D, 6.5D, 6.5D, 16D, 9.5D, 9.5D);
    public static final VoxelShape SHAPE_WEST = Block.box(0D, 6.5D, 6.5D, 6.5D, 9.5D, 9.5D);
    public static final VoxelShape SHAPE_UP = Block.box(6.5D, 9.5D, 6.5D, 9.5D, 16D, 9.5D);
    public static final VoxelShape SHAPE_DOWN = Block.box(6.5D, 0D, 6.5D, 9.5D, 6.5D, 9.5D);
    public static final VoxelShape SHAPE_CORE = Block.box(6.5D, 6.5D, 6.5D, 9.5D, 9.5D, 9.5D);

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


    public static boolean isConnectedTo(LevelAccessor world, BlockPos pos, Direction facing) {
        BlockState state = world.getBlockState(pos.relative(facing));

        if (state.getBlock().equals(ModBlocks.CABLE.get())) {
            return true;
        }

        BlockEntity te = world.getBlockEntity(pos.relative(facing));

        if (te == null || !te.getCapability(ForgeCapabilities.ENERGY, facing.getOpposite()).isPresent()) {
            return false;
        }
        return true;
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
        return new TileEntityCable(blockPos, blockState);
    }

}
