package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.tileentity.TileEntitySplitTank;
import de.maxhenkel.car.gui.ContainerSplitTank;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.corelib.blockentity.SimpleBlockEntityTicker;
import de.maxhenkel.tools.FluidInteractionTools;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BlockSplitTank extends BlockBase implements EntityBlock {

    protected BlockSplitTank(Properties properties) {
        super(properties.mapColor(MapColor.METAL).strength(3F).sound(SoundType.STONE).noOcclusion());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return new SimpleBlockEntityTicker<>();
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (FluidInteractionTools.tryFluidInteraction(player, interactionHand, level, blockPos)) {
            return InteractionResult.SUCCESS;
        }

        if (player.isShiftKeyDown()) {
            return InteractionResult.FAIL;
        }
        if (player instanceof ServerPlayer serverPlayer) {
            BlockEntity be = level.getBlockEntity(blockPos);
            if (!(be instanceof TileEntitySplitTank splitTank)) {
                return InteractionResult.FAIL;
            }
            TileEntityContainerProvider.openGui(serverPlayer, splitTank, (i, playerInventory, playerEntity) -> new ContainerSplitTank(i, splitTank, playerInventory));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Block.box(0D, 0D, 0D, 16D, 24D, 16D);
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return Block.box(0D, 0D, 0D, 16D, 24D, 16D);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Block.box(0D, 0D, 0D, 16D, 24D, 16D);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (worldIn.isEmptyBlock(pos.above())) {
            worldIn.setBlockAndUpdate(pos.above(), ModBlocks.SPLIT_TANK_TOP.get().defaultBlockState());
        }

        BlockEntity tileentity = worldIn.getBlockEntity(pos);

        if (tileentity instanceof Container) {
            Containers.dropContents(worldIn, pos, (Container) tileentity);
            worldIn.updateNeighbourForOutputSignal(pos, this);
        }

        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean moving) {
        super.affectNeighborsAfterRemoval(state, level, pos, moving);

        BlockState stateUp = level.getBlockState(pos.above());
        stateUp.getBlock();
        if (stateUp.getBlock().equals(ModBlocks.SPLIT_TANK_TOP.get())) {
            level.destroyBlock(pos.above(), false);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TileEntitySplitTank(blockPos, blockState);
    }

    public static class SplitTankItem extends BlockItem {

        public SplitTankItem(Block block, Properties properties) {
            super(block, properties.useBlockDescriptionPrefix());
        }

        @Override
        protected boolean canPlace(BlockPlaceContext context, BlockState state) {
            if (!context.getLevel().isEmptyBlock(context.getClickedPos().above())) {
                return false;
            }
            return super.canPlace(context, state);
        }

    }

}
