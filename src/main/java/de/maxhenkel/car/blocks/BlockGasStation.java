package de.maxhenkel.car.blocks;

import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.car.gui.ContainerGasStation;
import de.maxhenkel.car.gui.ContainerGasStationAdmin;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.corelib.block.DirectionalVoxelShape;
import de.maxhenkel.corelib.blockentity.SimpleBlockEntityTicker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
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
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

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

    public BlockGasStation() {
        super("gas_station", Material.METAL, MaterialColor.METAL, SoundType.METAL, 4F, 50F);
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().tab(ModItemGroups.TAB_CAR)) {
            @Override
            protected boolean canPlace(BlockPlaceContext context, BlockState state) {
                if (!context.getLevel().isEmptyBlock(context.getClickedPos().above())) {
                    return false;
                }
                return super.canPlace(context, state);
            }
        }.setRegistryName(getRegistryName());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return new SimpleBlockEntityTicker<>();
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        BlockEntity te = worldIn.getBlockEntity(pos);

        if (!(te instanceof TileEntityGasStation)) {
            return InteractionResult.FAIL;
        }

        TileEntityGasStation station = (TileEntityGasStation) te;

        ItemStack stack = player.getItemInHand(handIn);

        if (station.isOwner(player) || !station.hasTrade()) {
            FluidStack fluidStack = FluidUtil.getFluidContained(stack).orElse(FluidStack.EMPTY);

            if (!fluidStack.isEmpty()) {
                boolean success = BlockTank.handleEmpty(stack, worldIn, pos, player, handIn);
                if (success) {
                    return InteractionResult.SUCCESS;
                }
            }
            IFluidHandler handler = FluidUtil.getFluidHandler(stack).orElse(null);

            if (handler != null) {
                boolean success1 = BlockTank.handleFill(stack, worldIn, pos, player, handIn);
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
            worldIn.setBlockAndUpdate(pos.above(), ModBlocks.GAS_STATION_TOP.defaultBlockState().setValue(ModBlocks.GAS_STATION_TOP.FACING, state.getValue(FACING)));
        }

        BlockEntity te = worldIn.getBlockEntity(pos);

        if (te instanceof TileEntityGasStation && placer instanceof Player) {
            TileEntityGasStation station = (TileEntityGasStation) te;
            station.setOwner((Player) placer);
        }

        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, worldIn, pos, newState, isMoving);

        BlockState stateUp = worldIn.getBlockState(pos.above());
        stateUp.getBlock();
        if (stateUp.getBlock().equals(ModBlocks.GAS_STATION_TOP)) {
            worldIn.destroyBlock(pos.above(), false);
        }

        dropItems(worldIn, pos);
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

}
