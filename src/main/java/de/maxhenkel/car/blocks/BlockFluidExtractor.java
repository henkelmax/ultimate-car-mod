package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import de.maxhenkel.car.gui.ContainerFluidExtractor;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.corelib.block.IItemBlock;
import de.maxhenkel.corelib.block.VoxelUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockFluidExtractor extends BlockBase implements ITileEntityProvider, IItemBlock, IWaterLoggable {

    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.values());
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected BlockFluidExtractor() {
        super(Properties.of(Material.WOOL, MaterialColor.COLOR_GRAY).strength(0.5F).sound(SoundType.METAL));
        setRegistryName(new ResourceLocation(Main.MODID, "fluid_extractor"));

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
        return new BlockItem(this, new Item.Properties().tab(ModItemGroups.TAB_CAR)).setRegistryName(getRegistryName());
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.isShiftKeyDown()) {
            return ActionResultType.FAIL;
        }
        TileEntity te = worldIn.getBlockEntity(pos);
        if (!(te instanceof TileEntityFluidExtractor)) {
            return ActionResultType.FAIL;
        }
        TileEntityFluidExtractor fluidExtractor = (TileEntityFluidExtractor) te;
        if (player instanceof ServerPlayerEntity) {
            TileEntityContainerProvider.openGui((ServerPlayerEntity) player, fluidExtractor, (i, playerInventory, playerEntity) -> new ContainerFluidExtractor(i, fluidExtractor, playerInventory));
        }
        return ActionResultType.SUCCESS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction facing = context.getClickedFace().getOpposite();
        return getState(context.getLevel(), context.getClickedPos(), facing).setValue(FACING, facing);
    }

    private BlockState getState(World world, BlockPos pos, Direction except) {
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
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos pos1, boolean b) {
        super.neighborChanged(state, world, pos, block, pos1, b);
        Direction facing = world.getBlockState(pos).getValue(FACING);
        world.setBlockAndUpdate(pos, getState(world, pos, facing).setValue(FACING, facing));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, UP, DOWN, NORTH, SOUTH, EAST, WEST, WATERLOGGED);
    }

    public static final VoxelShape SHAPE_NORTH = Block.box(6D, 6D, 6D, 10D, 10D, 0D);
    public static final VoxelShape SHAPE_SOUTH = Block.box(6D, 6D, 10D, 10D, 10D, 16D);
    public static final VoxelShape SHAPE_EAST = Block.box(10D, 6D, 6D, 16D, 10D, 10D);
    public static final VoxelShape SHAPE_WEST = Block.box(6D, 6D, 6D, 0D, 10D, 10D);
    public static final VoxelShape SHAPE_UP = Block.box(6D, 10D, 6D, 10D, 16D, 10D);
    public static final VoxelShape SHAPE_DOWN = Block.box(6D, 6D, 6D, 10D, 0D, 10D);
    public static final VoxelShape SHAPE_CORE = Block.box(6D, 6D, 6D, 10D, 10D, 10D);
    public static final VoxelShape SHAPE_EXTRACTOR_NORTH = Block.box(1D, 1D, 0D, 15D, 15D, 1D);
    public static final VoxelShape SHAPE_EXTRACTOR_SOUTH = Block.box(1D, 1D, 15D, 15D, 15D, 16D);
    public static final VoxelShape SHAPE_EXTRACTOR_EAST = Block.box(15D, 1D, 1D, 16D, 15D, 15D);
    public static final VoxelShape SHAPE_EXTRACTOR_WEST = Block.box(0D, 1D, 1D, 1D, 15D, 15D);
    public static final VoxelShape SHAPE_EXTRACTOR_UP = Block.box(1D, 15D, 1D, 15D, 16D, 15D);
    public static final VoxelShape SHAPE_EXTRACTOR_DOWN = Block.box(1D, 0D, 1D, 15D, 1D, 15D);

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
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
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new TileEntityFluidExtractor();
    }

}
