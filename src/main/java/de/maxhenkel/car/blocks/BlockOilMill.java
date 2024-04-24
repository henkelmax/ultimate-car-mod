package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.tileentity.TileEntityOilMill;
import de.maxhenkel.car.gui.ContainerOilMill;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.corelib.blockentity.SimpleBlockEntityTicker;
import de.maxhenkel.corelib.fluid.FluidUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class BlockOilMill extends BlockGui<TileEntityOilMill> {

    protected BlockOilMill() {
        super(SoundType.STONE, 3F, 3F);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return new SimpleBlockEntityTicker<>();
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (FluidUtils.tryFluidInteraction(player, interactionHand, level, blockPos)) {
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Override
    public void openGui(BlockState state, Level worldIn, BlockPos pos, ServerPlayer player, TileEntityOilMill tileEntity) {
        TileEntityContainerProvider.openGui(player, tileEntity, (i, playerInventory, playerEntity) -> new ContainerOilMill(i, tileEntity, playerInventory));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TileEntityOilMill(blockPos, blockState);
    }

}
