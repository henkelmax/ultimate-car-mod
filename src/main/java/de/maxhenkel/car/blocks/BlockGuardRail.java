package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.corelib.block.VoxelUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import java.util.List;

public class BlockGuardRail extends BlockBase implements IWaterLoggable {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(0D, 0D, 16D, 16D, 16D, 14D);
    private static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(0D, 0D, 0D, 16D, 16D, 2D);
    private static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(2D, 0D, 0D, 0D, 16D, 16D);
    private static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(16D, 0D, 0D, 14D, 16D, 16D);

    public BlockGuardRail() {
        super(Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(2F).sound(SoundType.LANTERN));
        setRegistryName(new ResourceLocation(Main.MODID, "guard_rail"));

        setDefaultState(stateContainer.getBaseState().with(NORTH, false).with(SOUTH, false).with(EAST, false).with(WEST, false).with(WATERLOGGED, false));
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
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = VoxelShapes.empty();
        if (state.get(NORTH)) {
            shape = VoxelUtils.combine(shape, SHAPE_NORTH);
        }
        if (state.get(SOUTH)) {
            shape = VoxelUtils.combine(shape, SHAPE_SOUTH);
        }
        if (state.get(EAST)) {
            shape = VoxelUtils.combine(shape, SHAPE_EAST);
        }
        if (state.get(WEST)) {
            shape = VoxelUtils.combine(shape, SHAPE_WEST);
        }
        return shape;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        BlockState state = getDefaultState().with(WATERLOGGED, ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8);
        return state.with(getProperty(context.getPlacementHorizontalFacing()), true);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> drops = super.getDrops(state, builder);
        drops.stream().filter(itemStack -> itemStack.getItem() == ModItems.GUARD_RAIL).forEach(itemStack -> {
            if (itemStack.getCount() == 1) {
                int amount = 0;
                if (state.get(NORTH)) {
                    amount++;
                }
                if (state.get(SOUTH)) {
                    amount++;
                }
                if (state.get(EAST)) {
                    amount++;
                }
                if (state.get(WEST)) {
                    amount++;
                }
                itemStack.setCount(amount);
            }
        });
        return drops;
    }
}
