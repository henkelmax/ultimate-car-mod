package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.tools.BlockTools;
import de.maxhenkel.tools.FluidUtils;
import de.maxhenkel.tools.IItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockFluidPipe extends Block implements IItemBlock {

    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");

    public BlockFluidPipe() {
        super(Properties.create(Material.WOOL, MaterialColor.GRAY).hardnessAndResistance(0.25F).sound(SoundType.METAL));
        setRegistryName(new ResourceLocation(Main.MODID, "fluid_pipe"));

        setDefaultState(stateContainer.getBaseState()
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
        return new BlockItem(this, new Item.Properties().group(ModCreativeTabs.TAB_CAR)).setRegistryName(this.getRegistryName());
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getState(context.getWorld(), context.getPos());
    }

    private BlockState getState(World world, BlockPos pos) {
        return getDefaultState()
                .with(UP, isConnectedTo(world, pos, Direction.UP))
                .with(DOWN, isConnectedTo(world, pos, Direction.DOWN))
                .with(NORTH, isConnectedTo(world, pos, Direction.NORTH))
                .with(SOUTH, isConnectedTo(world, pos, Direction.SOUTH))
                .with(EAST, isConnectedTo(world, pos, Direction.EAST))
                .with(WEST, isConnectedTo(world, pos, Direction.WEST));
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos pos1, boolean b) {
        super.neighborChanged(state, world, pos, block, pos1, b);
        world.setBlockState(pos, getState(world, pos));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }

    //TODO fix shapes
    public static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(6D, 6D, 6D, 10D, 10D, 10D);
    public static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(6D, 6D, 6D, 10D, 10D, 10D);
    public static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(6D, 6D, 6D, 10D, 10D, 10D);
    public static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(6D, 6D, 6D, 10D, 10D, 10D);
    public static final VoxelShape SHAPE_UP = Block.makeCuboidShape(6D, 6D, 6D, 10D, 10D, 10D);
    public static final VoxelShape SHAPE_DOWN = Block.makeCuboidShape(6D, 6D, 6D, 10D, 10D, 10D);
    public static final VoxelShape SHAPE_CORE = Block.makeCuboidShape(6D, 6D, 6D, 10D, 10D, 10D);

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = SHAPE_CORE;
        if (state.get(UP)) {
            shape = BlockTools.combine(shape, SHAPE_UP);
        }

        if (state.get(DOWN)) {
            shape = BlockTools.combine(shape, SHAPE_DOWN);
        }

        if (state.get(SOUTH)) {
            shape = BlockTools.combine(shape, SHAPE_SOUTH);
        }

        if (state.get(NORTH)) {
            shape = BlockTools.combine(shape, SHAPE_NORTH);
        }

        if (state.get(EAST)) {
            shape = BlockTools.combine(shape, SHAPE_EAST);
        }

        if (state.get(WEST)) {
            shape = BlockTools.combine(shape, SHAPE_WEST);
        }

        return shape;
    }

    public static boolean isConnectedTo(IBlockReader world, BlockPos pos, Direction facing) {
        BlockState state = world.getBlockState(pos.offset(facing));

        if (state.getBlock().equals(ModBlocks.FLUID_PIPE) || state.getBlock().equals(ModBlocks.FLUID_EXTRACTOR)) {
            return true;
        }

        return FluidUtils.isFluidHandler(world, pos, facing);
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

}
