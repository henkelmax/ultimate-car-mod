package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.tools.IItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
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
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nullable;

public class BlockPaint extends BlockBase implements IItemBlock {

    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    public BlockPaint(EnumPaintType type, boolean isYellow) {
        super(Properties.create(new Material.Builder(MaterialColor.AIR).build())
                .hardnessAndResistance(2F).sound(SoundType.STONE).func_226896_b_());
        setRegistryName(new ResourceLocation(Main.MODID, type.name + (isYellow ? "_yellow" : "")));

        setDefaultState(stateContainer.getBaseState().with(FACING, Direction.NORTH));
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            RenderTypeLookup.setRenderLayer(BlockPaint.this, RenderType.func_228643_e_());
        });
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties()) {
            @Override
            protected boolean canPlace(BlockItemUseContext context, BlockState state) {
                if (!canPlaceBlockAt(context.getWorld(), context.getPos())) {
                    return false;
                }
                return super.canPlace(context, state);
            }
        }.setRegistryName(this.getRegistryName());
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    private static final VoxelShape SHAPE = Block.makeCuboidShape(0D, 0D, 0D, 16D, 0.25D, 16D);

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean p_220069_6_) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, p_220069_6_);
        checkForDrop(worldIn, pos, state);
    }

    private boolean checkForDrop(World worldIn, BlockPos pos, BlockState state) {
        if (!this.canBlockStay(worldIn, pos)) {
            worldIn.destroyBlock(pos, false);
            return false;
        } else {
            return true;
        }
    }

    private boolean canBlockStay(World worldIn, BlockPos pos) {
        return canPlaceBlockAt(worldIn, pos);
    }

    public static boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return func_220064_c(worldIn, pos.down());
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return false;
    }

    public enum EnumPaintType {
        ARROW_FRONT_LEFT_RIGHT_LONG("arrow_front_left_right_long"),
        ARROW_LONG("arrow_long"),
        ARROW_LEFT_LONG("arrow_left"),
        ARROW_RIGHT_LONG("arrow_right"),
        ARROW_LEFT_RIGHT_LONG("arrow_left_right_long"),
        ARROW_FRONT_LEFT_LONG("arrow_front_left_long"),
        ARROW_FRONT_RIGHT_LONG("arrow_front_right_long"),
        ARROW_LEFT_DIA("arrow_left_dia"),
        LINE_RIGHT_DIA("arrow_right_dia"),
        ARROW_FRONT_LEFT_RIGHT("arrow_front_left_right"),
        ARROW("arrow"),
        ARROW_LEFT("arrow_left_short"),
        ARROW_RIGHT("arrow_right_short"),
        ARROW_LEFT_RIGHT("arrow_left_right"),
        ARROW_FRONT_LEFT("arrow_front_left"),
        ARROW_FRONT_RIGHT("arrow_front_right"),
        ARROW_LEFT_DIA_SHORT("arrow_left_dia_short"),
        LINE_RIGHT_DIA_SHORT("arrow_right_dia_short"),
        LINE_MIDDLE("line_middle"),
        LINE_LONG("line_long"),
        LINE_END("line_end"),
        LINE_SIDE_MIDDLE("line_side_middle"),
        LINE_SIDE_LONG("line_side_long"),
        LINE_SIDE_START("line_side_start"),
        LINE_SIDE_END("line_side_end"),
        LINE_SIDE_LONG_LEFT("line_side_long_left"),
        LINE_SIDE_LONG_LEFT_FRONT("line_side_long_left_front"),
        LINE_MIDDLE_EDGE("line_middle_edge"),
        LINE_CORNER("line_corner"),
        LINE_DOUBLE("line_double"),
        LINE_DOUBLE_MIDDLE("line_double_middle"),
        LINE_DOUBLE_END("line_double_end"),
        ARROW_ZEBRAS("arrow_zebras"),
        ARROW_P("arrow_p"),
        ARROW_NO_PARKING("arrow_no_parking"),
        ARROW_CROSS("arrow_cross");

        private String name;

        EnumPaintType(String name) {
            this.name = name;
        }
    }

}
