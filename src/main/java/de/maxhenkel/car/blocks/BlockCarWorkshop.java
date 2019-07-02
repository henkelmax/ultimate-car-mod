package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.gui.ContainerCarWorkshopCrafting;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.tools.IItemBlock;
import net.minecraft.block.*;
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
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCarWorkshop extends Block implements ITileEntityProvider, IItemBlock {

    public static final BooleanProperty VALID = BooleanProperty.create("valid");

    protected BlockCarWorkshop() {
        super(Properties.create(Material.IRON, MaterialColor.GRAY).hardnessAndResistance(3F).sound(SoundType.METAL));
        setRegistryName(new ResourceLocation(Main.MODID, "car_workshop"));
        this.setDefaultState(stateContainer.getBaseState().with(VALID, false));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ModCreativeTabs.TAB_CAR)).setRegistryName(this.getRegistryName());
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        TileEntityCarWorkshop workshop = getOwnTileEntity(worldIn, pos);

        if (workshop == null) {
            return false;
        }

        if (!workshop.areBlocksAround()) {
            return false;
        }
        if (player instanceof ServerPlayerEntity) {
            TileEntityContainerProvider.openGui((ServerPlayerEntity) player, workshop, (i, playerInventory, playerEntity) -> new ContainerCarWorkshopCrafting(i, workshop, playerInventory));
        }

        return true;
    }

    public TileEntityCarWorkshop getOwnTileEntity(World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile == null) {
            return null;
        }

        if (!(tile instanceof TileEntityCarWorkshop)) {
            return null;
        }

        return (TileEntityCarWorkshop) tile;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        TileEntityCarWorkshop workshop = getOwnTileEntity(worldIn, pos);

        if (workshop == null) {
            return;
        }

        workshop.checkValidity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(VALID, false);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(VALID);
    }


    public void setValid(World world, BlockPos pos, BlockState state, boolean valid) {
        if (state.get(VALID).equals(valid)) {
            return;
        }

        TileEntity tileentity = world.getTileEntity(pos);

        world.setBlockState(pos, state.with(VALID, valid), 2);

        if (tileentity != null) {
            tileentity.validate();
            world.setTileEntity(pos, tileentity);
        }
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        TileEntityCarWorkshop workshop = getOwnTileEntity(worldIn, pos);

        if (workshop != null) {
            InventoryHelper.dropInventoryItems(worldIn, pos, workshop);
        }

        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityCarWorkshop();
    }
}
