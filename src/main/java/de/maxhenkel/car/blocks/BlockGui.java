package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.corelib.block.IItemBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class BlockGui<T extends TileEntity> extends BlockBase implements ITileEntityProvider, IItemBlock {

    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    protected BlockGui(String name, Material material, SoundType soundType, float hardness, float resistance) {
        this(name, Properties.of(material, MaterialColor.METAL).strength(hardness, resistance).sound(soundType));
    }

    protected BlockGui(String name, Block.Properties properties) {
        super(properties);
        setRegistryName(new ResourceLocation(Main.MODID, name));
        registerDefaultState(stateDefinition.any().setValue(POWERED, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().tab(ModItemGroups.TAB_CAR)).setRegistryName(getRegistryName());
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!player.isShiftKeyDown()) {
            if (!(player instanceof ServerPlayerEntity)) {
                return ActionResultType.SUCCESS;
            }

            TileEntity tileEntity = worldIn.getBlockEntity(pos);

            try {
                T tile = (T) tileEntity;
                openGui(state, worldIn, pos, (ServerPlayerEntity) player, handIn, tile);
            } catch (ClassCastException e) {

            }
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.FAIL;
    }

    public abstract void openGui(BlockState state, World worldIn, BlockPos pos, ServerPlayerEntity player, Hand handIn, T tileEntity);

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        TileEntity tileentity = worldIn.getBlockEntity(pos);

        if (state.getBlock() != newState.getBlock()) {
            if (tileentity instanceof IInventory) {
                InventoryHelper.dropContents(worldIn, pos, (IInventory) tileentity);
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }
        }

        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(POWERED, false);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public void setPowered(World world, BlockPos pos, BlockState state, boolean powered) {

        if (state.getValue(POWERED).equals(powered)) {
            return;
        }

        TileEntity tileentity = world.getBlockEntity(pos);

        world.setBlock(pos, state.setValue(POWERED, powered), 2);

        if (tileentity != null) {
            tileentity.clearRemoved();
            world.setBlockEntity(pos, tileentity);
        }
    }

}
