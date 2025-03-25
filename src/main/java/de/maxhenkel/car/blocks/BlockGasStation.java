package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.car.gui.ContainerGasStation;
import de.maxhenkel.car.gui.ContainerGasStationAdmin;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.corelib.block.DirectionalVoxelShape;
import de.maxhenkel.corelib.blockentity.SimpleBlockEntityTicker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public class BlockGasStation extends BlockOrientableHorizontal {

    public static VoxelShape SHAPE_NORTH_SOUTH = Block.box(2D, 0D, 5D, 14D, 31D, 11D);
    public static VoxelShape SHAPE_EAST_WEST = Block.box(5D, 0D, 2D, 11D, 31D, 14D);
    public static VoxelShape SHAPE_SLAB = Block.box(0D, 0D, 0D, 16D, 8.01D, 16D);

    private static final DirectionalVoxelShape SHAPES = new DirectionalVoxelShape.Builder()
            .direction(Direction.NORTH,
                    SHAPE_NORTH_SOUTH,
                    SHAPE_SLAB
            )
            .direction(Direction.SOUTH,
                    SHAPE_NORTH_SOUTH,
                    SHAPE_SLAB
            )
            .direction(Direction.EAST,
                    SHAPE_EAST_WEST,
                    SHAPE_SLAB
            )
            .direction(Direction.WEST,
                    SHAPE_EAST_WEST,
                    SHAPE_SLAB
            ).build();

    public BlockGasStation(Properties properties) {
        super(properties, MapColor.METAL, SoundType.METAL, 4F, 50F);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return new SimpleBlockEntityTicker<>();
    }

    @Override
    public InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        BlockEntity te = level.getBlockEntity(blockPos);

        if (!(te instanceof TileEntityGasStation)) {
            return InteractionResult.FAIL;
        }

        TileEntityGasStation station = (TileEntityGasStation) te;

        ItemStack stack = player.getItemInHand(interactionHand);

        if (station.isOwner(player) || !station.hasTrade()) {
            FluidStack fluidStack = FluidUtil.getFluidContained(stack).orElse(FluidStack.EMPTY);

            if (!fluidStack.isEmpty()) {
                boolean success = BlockTank.handleEmpty(stack, level, blockPos, player, interactionHand);
                if (success) {
                    return InteractionResult.SUCCESS;
                }
            }
            IFluidHandler handler = FluidUtil.getFluidHandler(stack).orElse(null);

            if (handler != null) {
                boolean success1 = BlockTank.handleFill(stack, level, blockPos, player, interactionHand);
                if (success1) {
                    return InteractionResult.SUCCESS;
                }
            }
        }

        if (!player.isShiftKeyDown()) {
            if (player instanceof ServerPlayer) {
                TileEntityContainerProvider.openGui((ServerPlayer) player, station, (i, playerInventory, playerEntity) -> new ContainerGasStation(i, station, playerInventory));
            }
            return InteractionResult.SUCCESS;
        } else if (station.isOwner(player)) {
            if (player instanceof ServerPlayer) {
                TileEntityContainerProvider.openGui((ServerPlayer) player, station, (i, playerInventory, playerEntity) -> new ContainerGasStationAdmin(i, station, playerInventory));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (worldIn.isEmptyBlock(pos.above())) {
            worldIn.setBlockAndUpdate(pos.above(), ModBlocks.GAS_STATION_TOP.get().defaultBlockState().setValue(ModBlocks.GAS_STATION_TOP.get().FACING, state.getValue(FACING)));
        }

        BlockEntity te = worldIn.getBlockEntity(pos);

        if (te instanceof TileEntityGasStation && placer instanceof Player) {
            TileEntityGasStation station = (TileEntityGasStation) te;
            station.setOwner((Player) placer);
        }

        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean moving) {
        super.affectNeighborsAfterRemoval(state, level, pos, moving);

        BlockState stateUp = level.getBlockState(pos.above());
        stateUp.getBlock();
        if (stateUp.getBlock().equals(ModBlocks.GAS_STATION_TOP.get())) {
            level.destroyBlock(pos.above(), false);
        }

        dropItems(level, pos);
    }

    public static void dropItems(Level worldIn, BlockPos pos) {
        BlockEntity te = worldIn.getBlockEntity(pos);

        if (te instanceof TileEntityGasStation) {
            TileEntityGasStation station = (TileEntityGasStation) te;
            Containers.dropContents(worldIn, pos, station.getInventory());
            Containers.dropContents(worldIn, pos, station.getTradingInventory());
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TileEntityGasStation(blockPos, blockState);
    }

    public static class GasStationItem extends BlockItem {

        public GasStationItem(Block block, Properties properties) {
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
