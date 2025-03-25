package de.maxhenkel.car.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public abstract class BlockGui<T extends BlockEntity> extends BlockBase implements EntityBlock {

    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected BlockGui(Properties properties, SoundType soundType, float hardness, float resistance) {
        this(properties.mapColor(MapColor.METAL).strength(hardness, resistance).sound(soundType));
    }

    protected BlockGui(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(POWERED, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (!player.isShiftKeyDown()) {
            if (!(player instanceof ServerPlayer)) {
                return InteractionResult.SUCCESS;
            }

            BlockEntity tileEntity = level.getBlockEntity(blockPos);

            try {
                T tile = (T) tileEntity;
                openGui(blockState, level, blockPos, (ServerPlayer) player, tile);
            } catch (ClassCastException e) {

            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    public abstract void openGui(BlockState state, Level worldIn, BlockPos pos, ServerPlayer player, T tileEntity);

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean moving) {
        BlockEntity tileentity = level.getBlockEntity(pos);

        if (tileentity instanceof Container) {
            Containers.dropContents(level, pos, (Container) tileentity);
            level.updateNeighbourForOutputSignal(pos, this);
        }
        super.affectNeighborsAfterRemoval(state, level, pos, moving);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(POWERED, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public void setPowered(Level world, BlockPos pos, BlockState state, boolean powered) {

        if (state.getValue(POWERED).equals(powered)) {
            return;
        }

        BlockEntity tileentity = world.getBlockEntity(pos);

        world.setBlock(pos, state.setValue(POWERED, powered), 2);

        if (tileentity != null) {
            tileentity.clearRemoved();
            world.setBlockEntity(tileentity);
        }
    }

}
