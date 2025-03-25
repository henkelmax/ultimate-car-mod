package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.gui.ContainerCarWorkshopCrafting;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class BlockCarWorkshop extends BlockBase implements EntityBlock {

    public static final BooleanProperty VALID = BooleanProperty.create("valid");

    protected BlockCarWorkshop(Properties properties) {
        super(properties.mapColor(MapColor.METAL).strength(3F).sound(SoundType.METAL));
        this.registerDefaultState(stateDefinition.any().setValue(VALID, false));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        TileEntityCarWorkshop workshop = getOwnTileEntity(level, blockPos);

        if (workshop == null) {
            return InteractionResult.FAIL;
        }

        if (!workshop.areBlocksAround()) {
            return InteractionResult.FAIL;
        }
        if (player instanceof ServerPlayer serverPlayer) {
            TileEntityContainerProvider.openGui(serverPlayer, workshop, (i, playerInventory, playerEntity) -> new ContainerCarWorkshopCrafting(i, workshop, playerInventory));
        }

        return InteractionResult.SUCCESS;
    }

    public TileEntityCarWorkshop getOwnTileEntity(Level world, BlockPos pos) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile == null) {
            return null;
        }

        if (!(tile instanceof TileEntityCarWorkshop)) {
            return null;
        }

        return (TileEntityCarWorkshop) tile;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);

        TileEntityCarWorkshop workshop = getOwnTileEntity(worldIn, pos);

        if (workshop == null) {
            return;
        }

        workshop.checkValidity();
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(VALID, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VALID);
    }


    public void setValid(Level world, BlockPos pos, BlockState state, boolean valid) {
        if (state.getValue(VALID).equals(valid)) {
            return;
        }

        BlockEntity tileentity = world.getBlockEntity(pos);

        world.setBlock(pos, state.setValue(VALID, valid), 2);

        if (tileentity != null) {
            tileentity.clearRemoved();
            world.setBlockEntity(tileentity);
        }
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean moving) {
        TileEntityCarWorkshop workshop = getOwnTileEntity(level, pos);

        if (workshop != null) {
            Containers.dropContents(level, pos, workshop);
        }
        super.affectNeighborsAfterRemoval(state, level, pos, moving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TileEntityCarWorkshop(blockPos, blockState);
    }

}
