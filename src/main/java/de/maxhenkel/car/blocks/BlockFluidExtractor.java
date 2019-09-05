package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import de.maxhenkel.car.gui.ContainerFluidExtractor;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.tools.VoxelShapeTools;
import de.maxhenkel.tools.IItemBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockFluidExtractor extends BlockBase implements ITileEntityProvider, IItemBlock {

    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.values());
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");

    protected BlockFluidExtractor() {
        super(Properties.create(Material.WOOL, MaterialColor.GRAY).hardnessAndResistance(0.5F).sound(SoundType.METAL));
        setRegistryName(new ResourceLocation(Main.MODID, "fluid_extractor"));

        setDefaultState(stateContainer.getBaseState()
                .with(FACING, Direction.NORTH)
                .with(UP, false)
                .with(DOWN, false)
                .with(NORTH, false)
                .with(SOUTH, false)
                .with(EAST, false)
                .with(WEST, false)
        );
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ModItemGroups.TAB_CAR)).setRegistryName(this.getRegistryName());
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.isSneaking()) {
            return false;
        }
        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof TileEntityFluidExtractor)) {
            return false;
        }
        TileEntityFluidExtractor fluidExtractor = (TileEntityFluidExtractor) te;
        if (player instanceof ServerPlayerEntity) {
            TileEntityContainerProvider.openGui((ServerPlayerEntity) player, fluidExtractor, (i, playerInventory, playerEntity) -> new ContainerFluidExtractor(i, fluidExtractor, playerInventory));
        }
        return true;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction facing = context.getFace().getOpposite();
        return getState(context.getWorld(), context.getPos(), facing).with(FACING, facing);
    }

    private BlockState getState(World world, BlockPos pos, Direction except) {
        return getDefaultState()
                .with(UP, except.equals(Direction.UP) ? false : BlockFluidPipe.isConnectedTo(world, pos, Direction.UP))
                .with(DOWN, except.equals(Direction.DOWN) ? false : BlockFluidPipe.isConnectedTo(world, pos, Direction.DOWN))
                .with(NORTH, except.equals(Direction.NORTH) ? false : BlockFluidPipe.isConnectedTo(world, pos, Direction.NORTH))
                .with(SOUTH, except.equals(Direction.SOUTH) ? false : BlockFluidPipe.isConnectedTo(world, pos, Direction.SOUTH))
                .with(EAST, except.equals(Direction.EAST) ? false : BlockFluidPipe.isConnectedTo(world, pos, Direction.EAST))
                .with(WEST, except.equals(Direction.WEST) ? false : BlockFluidPipe.isConnectedTo(world, pos, Direction.WEST));
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos pos1, boolean b) {
        super.neighborChanged(state, world, pos, block, pos1, b);
        Direction facing = world.getBlockState(pos).get(FACING);
        world.setBlockState(pos, getState(world, pos, facing).with(FACING, facing));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }

    public static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(6D, 6D, 6D, 10D, 10D, 0D);
    public static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(6D, 6D, 10D, 10D, 10D, 16D);
    public static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(10D, 6D, 6D, 16D, 10D, 10D);
    public static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(6D, 6D, 6D, 0D, 10D, 10D);
    public static final VoxelShape SHAPE_UP = Block.makeCuboidShape(6D, 10D, 6D, 10D, 16D, 10D);
    public static final VoxelShape SHAPE_DOWN = Block.makeCuboidShape(6D, 6D, 6D, 10D, 0D, 10D);
    public static final VoxelShape SHAPE_CORE = Block.makeCuboidShape(6D, 6D, 6D, 10D, 10D, 10D);
    public static final VoxelShape SHAPE_EXTRACTOR_NORTH = Block.makeCuboidShape(1D, 1D, 0D, 15D, 15D, 1D);
    public static final VoxelShape SHAPE_EXTRACTOR_SOUTH = Block.makeCuboidShape(1D, 1D, 15D, 15D, 15D, 16D);
    public static final VoxelShape SHAPE_EXTRACTOR_EAST = Block.makeCuboidShape(15D, 1D, 1D, 16D, 15D, 15D);
    public static final VoxelShape SHAPE_EXTRACTOR_WEST = Block.makeCuboidShape(0D, 1D, 1D, 1D, 15D, 15D);
    public static final VoxelShape SHAPE_EXTRACTOR_UP = Block.makeCuboidShape(1D, 15D, 1D, 15D, 16D, 15D);
    public static final VoxelShape SHAPE_EXTRACTOR_DOWN = Block.makeCuboidShape(1D, 0D, 1D, 15D, 1D, 15D);

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = SHAPE_CORE;

        if (state.get(NORTH)) {
            shape = VoxelShapeTools.combine(shape, SHAPE_NORTH);
        }
        if (state.get(SOUTH)) {
            shape = VoxelShapeTools.combine(shape, SHAPE_SOUTH);
        }
        if (state.get(EAST)) {
            shape = VoxelShapeTools.combine(shape, SHAPE_EAST);
        }
        if (state.get(WEST)) {
            shape = VoxelShapeTools.combine(shape, SHAPE_WEST);
        }
        if (state.get(UP)) {
            shape = VoxelShapeTools.combine(shape, SHAPE_UP);
        }
        if (state.get(DOWN)) {
            shape = VoxelShapeTools.combine(shape, SHAPE_DOWN);
        }
        switch (state.get(FACING)) {
            case NORTH:
                shape = VoxelShapeTools.combine(shape, SHAPE_EXTRACTOR_NORTH, SHAPE_NORTH);
                break;
            case SOUTH:
                shape = VoxelShapeTools.combine(shape, SHAPE_EXTRACTOR_SOUTH, SHAPE_SOUTH);
                break;
            case EAST:
                shape = VoxelShapeTools.combine(shape, SHAPE_EXTRACTOR_EAST, SHAPE_EAST);
                break;
            case WEST:
                shape = VoxelShapeTools.combine(shape, SHAPE_EXTRACTOR_WEST, SHAPE_WEST);
                break;
            case UP:
                shape = VoxelShapeTools.combine(shape, SHAPE_EXTRACTOR_UP, SHAPE_UP);
                break;
            case DOWN:
                shape = VoxelShapeTools.combine(shape, SHAPE_EXTRACTOR_DOWN, SHAPE_DOWN);
                break;
        }
        return shape;
    }


    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader world, BlockPos pos, Direction face) {
        return false;
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityFluidExtractor();
    }
}
