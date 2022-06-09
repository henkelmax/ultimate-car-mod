package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.tileentity.TileEntityBackmixReactor;
import de.maxhenkel.car.gui.ContainerBackmixReactor;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.corelib.blockentity.SimpleBlockEntityTicker;
import de.maxhenkel.corelib.fluid.FluidUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class BlockBackmixReactor extends BlockGui<TileEntityBackmixReactor> {

    protected BlockBackmixReactor() {
        super(Material.METAL, SoundType.METAL, 3F, 3F);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (FluidUtils.tryFluidInteraction(player, handIn, worldIn, pos)) {
            return InteractionResult.SUCCESS;
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return new SimpleBlockEntityTicker<>();
    }

    @Override
    public void openGui(BlockState state, Level worldIn, BlockPos pos, ServerPlayer player, InteractionHand handIn, TileEntityBackmixReactor tileEntity) {
        TileEntityContainerProvider.openGui(player, tileEntity, (i, playerInventory, playerEntity) -> new ContainerBackmixReactor(i, tileEntity, playerInventory));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TileEntityBackmixReactor(blockPos, blockState);
    }

}
