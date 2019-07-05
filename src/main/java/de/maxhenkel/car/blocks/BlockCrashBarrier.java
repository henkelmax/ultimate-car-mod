package de.maxhenkel.car.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
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
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;

import javax.annotation.Nullable;
import java.util.Map;

public class BlockCrashBarrier extends BlockBase implements IItemBlock {

    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    public static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(0D, 0D, 15D, 16D, 16D, 13D);
    public static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(0D, 0D, 1D, 16D, 16D, 13D);
    public static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(3D, 0D, 0D, 1D, 16D, 16D);
    public static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(15D, 0D, 0D, 13D, 16D, 16D);

    public BlockCrashBarrier() {
        super(Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(2F).sound(SoundType.LANTERN));
        setRegistryName(new ResourceLocation(Main.MODID, "crash_barrier"));

        setDefaultState(stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ModCreativeTabs.TAB_CAR)).setRegistryName(this.getRegistryName());
    }

    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH,
            SHAPE_NORTH,
            Direction.SOUTH,
            SHAPE_SOUTH,
            Direction.EAST,
            SHAPE_EAST,
            Direction.WEST,
            SHAPE_WEST
    ));

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(state.get(FACING));
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader world, BlockPos pos, Direction face) {
        return false;
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

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
