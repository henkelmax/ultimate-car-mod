package de.maxhenkel.car.blocks;

import de.maxhenkel.corelib.block.IItemBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public abstract class BlockGui<T extends BlockEntity> extends BlockBase implements EntityBlock, IItemBlock {

    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    protected BlockGui(SoundType soundType, float hardness, float resistance) {
        this(Properties.of().mapColor(MapColor.METAL).strength(hardness, resistance).sound(soundType));
    }

    protected BlockGui(Block.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(POWERED, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties());
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!player.isShiftKeyDown()) {
            if (!(player instanceof ServerPlayer)) {
                return InteractionResult.SUCCESS;
            }

            BlockEntity tileEntity = worldIn.getBlockEntity(pos);

            try {
                T tile = (T) tileEntity;
                openGui(state, worldIn, pos, (ServerPlayer) player, handIn, tile);
            } catch (ClassCastException e) {

            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    public abstract void openGui(BlockState state, Level worldIn, BlockPos pos, ServerPlayer player, InteractionHand handIn, T tileEntity);

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        BlockEntity tileentity = worldIn.getBlockEntity(pos);

        if (state.getBlock() != newState.getBlock()) {
            if (tileentity instanceof Container) {
                Containers.dropContents(worldIn, pos, (Container) tileentity);
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }
        }

        super.onRemove(state, worldIn, pos, newState, isMoving);
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
