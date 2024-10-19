package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import de.maxhenkel.car.gui.ContainerGenerator;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.corelib.blockentity.SimpleBlockEntityTicker;
import de.maxhenkel.corelib.fluid.FluidUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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

public class BlockGenerator extends BlockGui<TileEntityGenerator> {

    protected BlockGenerator(Properties properties) {
        super(properties.mapColor(MapColor.METAL).strength(3F, 3F).sound(SoundType.STONE).noOcclusion());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return new SimpleBlockEntityTicker<>();
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (FluidUtils.tryFluidInteraction(player, interactionHand, level, blockPos)) {
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Override
    public void openGui(BlockState state, Level worldIn, BlockPos pos, ServerPlayer player, TileEntityGenerator tileEntity) {
        TileEntityContainerProvider.openGui(player, tileEntity, (i, playerInventory, playerEntity) -> new ContainerGenerator(i, tileEntity, playerInventory));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Block.box(0D, 0D, 0D, 16D, 13D, 16D);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TileEntityGenerator(blockPos, blockState);
    }

}
