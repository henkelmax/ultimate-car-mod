package de.maxhenkel.car.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockSplitTankTop extends BlockBase {

    public BlockSplitTankTop(Properties properties) {
        super(properties.mapColor(MapColor.METAL).strength(3F).sound(SoundType.STONE).pushReaction(PushReaction.BLOCK));
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        return ModBlocks.SPLIT_TANK.get().useItemOn(itemStack, level.getBlockState(blockPos.below()), level, blockPos.below(), player, interactionHand, blockHitResult);
    }

    private static final VoxelShape SHAPE = Block.box(0D, -16D, 0D, 16D, 8D, 16D);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            super.onRemove(state, worldIn, pos, newState, isMoving);

            BlockState stateDown = worldIn.getBlockState(pos.below());
            if (stateDown != null && stateDown.getBlock().equals(ModBlocks.SPLIT_TANK.get())) {
                worldIn.destroyBlock(pos.below(), false);
            }
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        BlockState stateDown = world.getBlockState(pos.below());
        if (stateDown != null && stateDown.getBlock() != null && stateDown.getBlock().equals(ModBlocks.SPLIT_TANK.get()) && !player.getAbilities().instabuild) {
            ModBlocks.SPLIT_TANK.get().playerDestroy(world, player, pos.below(), world.getBlockState(pos.below()), world.getBlockEntity(pos.below()), player.getMainHandItem());
        }
        return super.onDestroyedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player) {
        BlockState stateDown = level.getBlockState(pos.below());
        if (stateDown.getBlock().equals(ModBlocks.SPLIT_TANK.get())) {
            return ModBlocks.SPLIT_TANK.get().getCloneItemStack(level, pos.below(), stateDown, includeData, player);
        }
        return super.getCloneItemStack(level, pos, state, includeData, player);
    }
}
