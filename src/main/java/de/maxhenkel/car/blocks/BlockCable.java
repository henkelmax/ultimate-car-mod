package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.tileentity.TileEntityCable;
import de.maxhenkel.corelib.block.IItemBlock;
import de.maxhenkel.corelib.block.VoxelUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;

public class BlockCable extends BlockBase implements ITileEntityProvider, IItemBlock, IWaterLoggable {

    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected BlockCable() {
        super(Block.Properties.create(Material.WOOL, MaterialColor.GRAY).hardnessAndResistance(0.25F).sound(SoundType.CLOTH));
        setRegistryName(new ResourceLocation(Main.MODID, "cable"));

        setDefaultState(stateContainer.getBaseState()
                .with(UP, false)
                .with(DOWN, false)
                .with(NORTH, false)
                .with(SOUTH, false)
                .with(EAST, false)
                .with(WEST, false)
                .with(WATERLOGGED, false)
        );
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ModItemGroups.TAB_CAR)).setRegistryName(getRegistryName());
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getState(context.getWorld(), context.getPos());
    }

    private BlockState getState(World world, BlockPos pos) {
        FluidState ifluidstate = world.getFluidState(pos);
        return getDefaultState()
                .with(UP, isConnectedTo(world, pos, Direction.UP))
                .with(DOWN, isConnectedTo(world, pos, Direction.DOWN))
                .with(NORTH, isConnectedTo(world, pos, Direction.NORTH))
                .with(SOUTH, isConnectedTo(world, pos, Direction.SOUTH))
                .with(EAST, isConnectedTo(world, pos, Direction.EAST))
                .with(WEST, isConnectedTo(world, pos, Direction.WEST))
                .with(WATERLOGGED, ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos pos1, boolean b) {
        super.neighborChanged(state, world, pos, block, pos1, b);
        world.setBlockState(pos, getState(world, pos));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST, WATERLOGGED);
    }

    public static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(6.5D, 6.5D, 6.5D, 9.5D, 9.5D, 0D);
    public static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(6.5D, 6.5D, 9.5D, 9.5D, 9.5D, 16D);
    public static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(9.5D, 6.5D, 6.5D, 16D, 9.5D, 9.5D);
    public static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(6.5D, 6.5D, 6.5D, 0D, 9.5D, 9.5D);
    public static final VoxelShape SHAPE_UP = Block.makeCuboidShape(6.5D, 9.5D, 6.5D, 9.5D, 16D, 9.5D);
    public static final VoxelShape SHAPE_DOWN = Block.makeCuboidShape(6.5D, 6.5D, 6.5D, 9.5D, 0D, 9.5D);
    public static final VoxelShape SHAPE_CORE = Block.makeCuboidShape(6.5D, 6.5D, 6.5D, 9.5D, 9.5D, 9.5D);

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = SHAPE_CORE;
        if (state.get(UP)) {
            shape = VoxelUtils.combine(shape, SHAPE_UP);
        }

        if (state.get(DOWN)) {
            shape = VoxelUtils.combine(shape, SHAPE_DOWN);
        }

        if (state.get(SOUTH)) {
            shape = VoxelUtils.combine(shape, SHAPE_SOUTH);
        }

        if (state.get(NORTH)) {
            shape = VoxelUtils.combine(shape, SHAPE_NORTH);
        }

        if (state.get(EAST)) {
            shape = VoxelUtils.combine(shape, SHAPE_EAST);
        }

        if (state.get(WEST)) {
            shape = VoxelUtils.combine(shape, SHAPE_WEST);
        }

        return shape;
    }


    public static boolean isConnectedTo(IWorldReader world, BlockPos pos, Direction facing) {
        BlockState state = world.getBlockState(pos.offset(facing));

        if (state.getBlock().equals(ModBlocks.CABLE)) {
            return true;
        }

        TileEntity te = world.getTileEntity(pos.offset(facing));

        if (te == null || !te.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()).isPresent()) {
            return false;
        }
        return true;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityCable();
    }

}
