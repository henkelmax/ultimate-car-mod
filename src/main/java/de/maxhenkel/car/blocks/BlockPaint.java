package de.maxhenkel.car.blocks;

import de.maxhenkel.corelib.block.IItemBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BlockPaint extends BlockBase implements IItemBlock {

    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    private final EnumPaintType paintType;
    private final boolean yellow;

    public BlockPaint(EnumPaintType type, boolean yellow) {
        super(Properties.of(new Material.Builder(MaterialColor.NONE).build()).strength(2F).sound(SoundType.STONE).noOcclusion());
        this.paintType = type;
        this.yellow = yellow;

        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties()) {
            @Override
            protected boolean canPlace(BlockPlaceContext context, BlockState state) {
                if (!canPlaceBlockAt(context.getLevel(), context.getClickedPos())) {
                    return false;
                }
                return super.canPlace(context, state);
            }
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    private static final VoxelShape SHAPE = Block.box(0D, 0D, 0D, 16D, 0.25D, 16D);

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean p_220069_6_) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, p_220069_6_);
        checkForDrop(worldIn, pos, state);
    }

    private boolean checkForDrop(Level worldIn, BlockPos pos, BlockState state) {
        if (!this.canBlockStay(worldIn, pos)) {
            worldIn.destroyBlock(pos, false);
            return false;
        } else {
            return true;
        }
    }

    private boolean canBlockStay(Level worldIn, BlockPos pos) {
        return canPlaceBlockAt(worldIn, pos);
    }

    public static boolean canPlaceBlockAt(Level worldIn, BlockPos pos) {
        return canSupportRigidBlock(worldIn, pos.below());
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
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
        LINE_DOUBLE_MIDDLE_EDGE("line_double_middle_edge"),
        LINE_DOUBLE_END("line_double_end"),
        ARROW_ZEBRAS("arrow_zebras"),
        ARROW_P("arrow_p"),
        ARROW_NO_PARKING("arrow_no_parking"),
        ARROW_CROSS("arrow_cross");

        private String name;

        EnumPaintType(String name) {
            this.name = name;
        }

        public String getPaintName() {
            return name;
        }
    }

}
