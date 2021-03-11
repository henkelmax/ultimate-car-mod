package de.maxhenkel.car.blocks;

import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.car.gui.ContainerGasStation;
import de.maxhenkel.car.gui.ContainerGasStationAdmin;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.corelib.block.DirectionalVoxelShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public class BlockGasStation extends BlockOrientableHorizontal {

    public static VoxelShape SHAPE_NORTH_SOUTH = Block.box(2D, 0D, 5D, 14D, 31D, 11D);
    public static VoxelShape SHAPE_NEAST_WEST = Block.box(5D, 0D, 2D, 11D, 31D, 14D);
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
                    SHAPE_NEAST_WEST,
                    SHAPE_SLAB
            )
            .direction(Direction.WEST,
                    SHAPE_NEAST_WEST,
                    SHAPE_SLAB
            ).build();

    public BlockGasStation() {
        super("gas_station", Material.METAL, MaterialColor.METAL, SoundType.METAL, 4F, 50F);
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().tab(ModItemGroups.TAB_CAR)) {
            @Override
            protected boolean canPlace(BlockItemUseContext context, BlockState state) {
                if (!context.getLevel().isEmptyBlock(context.getClickedPos().above())) {
                    return false;
                }
                return super.canPlace(context, state);
            }
        }.setRegistryName(getRegistryName());
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        TileEntity te = worldIn.getBlockEntity(pos);

        if (!(te instanceof TileEntityGasStation)) {
            return ActionResultType.FAIL;
        }

        TileEntityGasStation station = (TileEntityGasStation) te;

        ItemStack stack = player.getItemInHand(handIn);

        if (station.isOwner(player) || !station.hasTrade()) {
            FluidStack fluidStack = FluidUtil.getFluidContained(stack).orElse(FluidStack.EMPTY);

            if (!fluidStack.isEmpty()) {
                boolean success = BlockTank.handleEmpty(stack, worldIn, pos, player, handIn);
                if (success) {
                    return ActionResultType.SUCCESS;
                }
            }
            IFluidHandler handler = FluidUtil.getFluidHandler(stack).orElse(null);

            if (handler != null) {
                boolean success1 = BlockTank.handleFill(stack, worldIn, pos, player, handIn);
                if (success1) {
                    return ActionResultType.SUCCESS;
                }
            }
        }

        if (!player.isShiftKeyDown()) {
            if (player instanceof ServerPlayerEntity) {
                TileEntityContainerProvider.openGui((ServerPlayerEntity) player, station, (i, playerInventory, playerEntity) -> new ContainerGasStation(i, station, playerInventory));
            }
            return ActionResultType.SUCCESS;
        } else if (station.isOwner(player)) {
            if (player instanceof ServerPlayerEntity) {
                TileEntityContainerProvider.openGui((ServerPlayerEntity) player, station, (i, playerInventory, playerEntity) -> new ContainerGasStationAdmin(i, station, playerInventory));
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }


    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (worldIn.isEmptyBlock(pos.above())) {
            worldIn.setBlockAndUpdate(pos.above(), ModBlocks.FUEL_STATION_TOP.defaultBlockState().setValue(ModBlocks.FUEL_STATION_TOP.FACING, state.getValue(FACING)));
        }

        TileEntity te = worldIn.getBlockEntity(pos);

        if (te instanceof TileEntityGasStation && placer instanceof PlayerEntity) {
            TileEntityGasStation station = (TileEntityGasStation) te;
            station.setOwner((PlayerEntity) placer);
        }

        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, worldIn, pos, newState, isMoving);

        BlockState stateUp = worldIn.getBlockState(pos.above());
        stateUp.getBlock();
        if (stateUp.getBlock().equals(ModBlocks.FUEL_STATION_TOP)) {
            worldIn.destroyBlock(pos.above(), false);
        }

        dropItems(worldIn, pos);
    }

    public static void dropItems(World worldIn, BlockPos pos) {
        TileEntity te = worldIn.getBlockEntity(pos);

        if (te instanceof TileEntityGasStation) {
            TileEntityGasStation station = (TileEntityGasStation) te;
            InventoryHelper.dropContents(worldIn, pos, station.getInventory());
            InventoryHelper.dropContents(worldIn, pos, station.getTradingInventory());
        }
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new TileEntityGasStation();
    }

}
