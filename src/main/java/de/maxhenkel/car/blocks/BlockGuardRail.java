package de.maxhenkel.car.blocks;

import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.corelib.block.VoxelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class BlockGuardRail extends BlockBase implements SimpleWaterloggedBlock {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE_NORTH = Block.box(0D, 0D, 14D, 16D, 16D, 16D);
    private static final VoxelShape SHAPE_SOUTH = Block.box(0D, 0D, 0D, 16D, 16D, 2D);
    private static final VoxelShape SHAPE_EAST = Block.box(0D, 0D, 0D, 2D, 16D, 16D);
    private static final VoxelShape SHAPE_WEST = Block.box(14D, 0D, 0D, 16D, 16D, 16D);
    private static final VoxelShape SHAPE = Block.box(7.5D, 0D, 7.5D, 8.5D, 16D, 8.5D);

    public BlockGuardRail(Properties properties) {
        super(properties.mapColor(MapColor.METAL).strength(2F).sound(SoundType.LANTERN).noOcclusion());

        registerDefaultState(stateDefinition
                .any()
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false)
                .setValue(WATERLOGGED, false)
        );
    }

    public BooleanProperty getProperty(Direction direction) {
        switch (direction) {
            case NORTH:
            default:
                return NORTH;
            case SOUTH:
                return SOUTH;
            case EAST:
                return EAST;
            case WEST:
                return WEST;
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Shapes.empty();
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
        if (shape.isEmpty()) {
            shape = VoxelUtils.combine(shape, SHAPE);
        }
        return shape;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockState state = defaultBlockState().setValue(WATERLOGGED, ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8);
        return state.setValue(getProperty(context.getHorizontalDirection()), true);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos pos1, BlockState state1, RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            tickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, level, tickAccess, pos, direction, pos1, state1, randomSource);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> drops = super.getDrops(state, builder);
        drops.stream().filter(itemStack -> itemStack.getItem() == ModItems.GUARD_RAIL.get()).forEach(itemStack -> {
            if (itemStack.getCount() == 1) {
                int amount = 0;
                if (state.getValue(NORTH)) {
                    amount++;
                }
                if (state.getValue(SOUTH)) {
                    amount++;
                }
                if (state.getValue(EAST)) {
                    amount++;
                }
                if (state.getValue(WEST)) {
                    amount++;
                }
                itemStack.setCount(amount);
            }
        });
        return drops;
    }

}
