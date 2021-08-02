package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockSplitTankTop extends BlockBase {

    public BlockSplitTankTop() {
        super(Properties.of(Material.METAL).strength(3F).sound(SoundType.STONE));
        setRegistryName(new ResourceLocation(Main.MODID, "split_tank_top"));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        return ModBlocks.SPLIT_TANK.use(worldIn.getBlockState(pos.below()), worldIn, pos.below(), player, handIn, hit);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
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
            if (stateDown != null && stateDown.getBlock().equals(ModBlocks.SPLIT_TANK)) {
                worldIn.destroyBlock(pos.below(), false);
            }
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean removedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        BlockState stateDown = world.getBlockState(pos.below());
        if (stateDown != null && stateDown.getBlock() != null && stateDown.getBlock().equals(ModBlocks.SPLIT_TANK) && !player.getAbilities().instabuild) {
            ModBlocks.SPLIT_TANK.playerDestroy(world, player, pos.below(), world.getBlockState(pos.below()), world.getBlockEntity(pos.below()), player.getMainHandItem());
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        BlockState stateDown = world.getBlockState(pos.below());
        if (stateDown.getBlock().equals(ModBlocks.SPLIT_TANK)) {
            return ModBlocks.SPLIT_TANK.getPickBlock(stateDown, target, world, pos.below(), player);
        }
        return super.getPickBlock(stateDown, target, world, pos, player);
    }

}
