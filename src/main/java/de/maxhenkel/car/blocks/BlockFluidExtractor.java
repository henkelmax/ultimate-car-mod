package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import de.maxhenkel.tools.BlockTools;
import de.maxhenkel.tools.IItemBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
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

public class BlockFluidExtractor extends Block implements ITileEntityProvider, IItemBlock {

    public static final DirectionProperty FACING = DirectionProperty.create("facing");
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
        return new BlockItem(this, new Item.Properties().group(ModCreativeTabs.TAB_CAR)).setRegistryName(this.getRegistryName());
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!player.isSneaking()) {
            //TODO GUI
            //playerIn.openGui(Main.instance(), GuiHandler.GUI_FLUID_EXTRACTOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }

        return false;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState()
                .with(UP, BlockFluidPipe.isConnectedTo(context.getWorld(), context.getPos(), Direction.UP))
                .with(DOWN, BlockFluidPipe.isConnectedTo(context.getWorld(), context.getPos(), Direction.DOWN))
                .with(NORTH, BlockFluidPipe.isConnectedTo(context.getWorld(), context.getPos(), Direction.NORTH))
                .with(SOUTH, BlockFluidPipe.isConnectedTo(context.getWorld(), context.getPos(), Direction.SOUTH))
                .with(EAST, BlockFluidPipe.isConnectedTo(context.getWorld(), context.getPos(), Direction.EAST))
                .with(WEST, BlockFluidPipe.isConnectedTo(context.getWorld(), context.getPos(), Direction.WEST));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }

    //TODO fix shapes (copy from fluidpipe
    public static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(6D, 6D, 6D, 10D, 10D, 10D);
    public static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(6D, 6D, 6D, 10D, 10D, 10D);
    public static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(6D, 6D, 6D, 10D, 10D, 10D);
    public static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(6D, 6D, 6D, 10D, 10D, 10D);
    public static final VoxelShape SHAPE_UP = Block.makeCuboidShape(6D, 6D, 6D, 10D, 10D, 10D);
    public static final VoxelShape SHAPE_DOWN = Block.makeCuboidShape(6D, 6D, 6D, 10D, 10D, 10D);
    public static final VoxelShape SHAPE_BASE = Block.makeCuboidShape(6D, 6D, 6D, 10D, 10D, 10D);

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = SHAPE_BASE;
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
