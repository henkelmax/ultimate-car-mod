package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.tools.IItemBlock;
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
        super(Properties.create(material, MaterialColor.IRON).hardnessAndResistance(hardness, resistance).sound(soundType));
        setRegistryName(new ResourceLocation(Main.MODID, name));
        setDefaultState(stateContainer.getBaseState().with(POWERED, false).with(FACING, Direction.NORTH));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ModCreativeTabs.TAB_CAR)).setRegistryName(this.getRegistryName());
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!player.isSneaking()) {
            if (!(player instanceof ServerPlayerEntity)) {
                return true;
            }

            TileEntity tileEntity = worldIn.getTileEntity(pos);

            try {
                T tile = (T) tileEntity;
                openGui(state, worldIn, pos, (ServerPlayerEntity) player, handIn, tile);
            } catch (ClassCastException e) {

            }
            return true;
        }

        return false;
    }

    public abstract void openGui(BlockState state, World worldIn, BlockPos pos, ServerPlayerEntity player, Hand handIn, T tileEntity);

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (state.getBlock() != newState.getBlock()) {
            if (tileentity != null && tileentity instanceof IInventory) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }

        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite()).with(POWERED, false);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public void setPowered(World world, BlockPos pos, BlockState state, boolean powered) {

        if (state.get(POWERED).equals(powered)) {
            return;
        }

        TileEntity tileentity = world.getTileEntity(pos);

        world.setBlockState(pos, state.with(POWERED, powered), 2);

        if (tileentity != null) {
            tileentity.validate();
            world.setTileEntity(pos, tileentity);
        }
    }

}
