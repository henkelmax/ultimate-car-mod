package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.tileentity.TileEntityCable;
import de.maxhenkel.tools.BlockTools;
import de.maxhenkel.tools.IItemBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;

public class BlockCable extends Block implements ITileEntityProvider, IItemBlock {

    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");

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
        );
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ModCreativeTabs.TAB_CAR)).setRegistryName(this.getRegistryName());
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState()
                .with(UP, isConnectedTo(context.getWorld(), context.getPos(), Direction.UP))
                .with(DOWN, isConnectedTo(context.getWorld(), context.getPos(), Direction.DOWN))
                .with(NORTH, isConnectedTo(context.getWorld(), context.getPos(), Direction.NORTH))
                .with(SOUTH, isConnectedTo(context.getWorld(), context.getPos(), Direction.SOUTH))
                .with(EAST, isConnectedTo(context.getWorld(), context.getPos(), Direction.EAST))
                .with(WEST, isConnectedTo(context.getWorld(), context.getPos(), Direction.WEST));
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
    /*
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

        float x1 = 0.40625F;
        float y1 = 0.40625F;
        float z1 = 0.40625F;
        float x2 = 0.59375F;
        float y2 = 0.59375F;
        float z2 = 0.59375F;


        if (isConnectedTo(source, pos, EnumFacing.UP)) {
            y2 = 1.0F;
        }

        if (isConnectedTo(source, pos, EnumFacing.DOWN)) {
            y1 = 0.0F;
        }

        if (isConnectedTo(source, pos, EnumFacing.SOUTH)) {
            z2 = 1.0F;
        }

        if (isConnectedTo(source, pos, EnumFacing.NORTH)) {
            z1 = 0.0F;
        }

        if (isConnectedTo(source, pos, EnumFacing.EAST)) {
            x2 = 1.0F;
        }

        if (isConnectedTo(source, pos, EnumFacing.WEST)) {
            x1 = 0.0F;
        }

        return new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
    }*/


    public static boolean isConnectedTo(IWorldReader world, BlockPos pos, Direction facing) {
        BlockState state = world.getBlockState(pos.offset(facing));

        if (state.getBlock().equals(ModBlocks.CABLE)) {
            return true;
        }

        TileEntity te = world.getTileEntity(pos.offset(facing));

        if (te == null || te.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()).isPresent()) {
            return false;
        }
        // te.getCapability(CapabilityEnergy.ENERGY, side.getOpposite());
        return true;
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
        return new TileEntityCable();
    }
}
