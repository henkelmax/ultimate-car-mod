package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.gui.ContainerCarWorkshopCrafting;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.corelib.block.IItemBlock;
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
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCarWorkshop extends BlockBase implements ITileEntityProvider, IItemBlock {

    public static final BooleanProperty VALID = BooleanProperty.create("valid");

    protected BlockCarWorkshop() {
        super(Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(3F).sound(SoundType.METAL));
        setRegistryName(new ResourceLocation(Main.MODID, "car_workshop"));
        this.registerDefaultState(stateDefinition.any().setValue(VALID, false));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().tab(ModItemGroups.TAB_CAR)).setRegistryName(getRegistryName());
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        TileEntityCarWorkshop workshop = getOwnTileEntity(worldIn, pos);

        if (workshop == null) {
            return ActionResultType.FAIL;
        }

        if (!workshop.areBlocksAround()) {
            return ActionResultType.FAIL;
        }
        if (player instanceof ServerPlayerEntity) {
            TileEntityContainerProvider.openGui((ServerPlayerEntity) player, workshop, (i, playerInventory, playerEntity) -> new ContainerCarWorkshopCrafting(i, workshop, playerInventory));
        }

        return ActionResultType.SUCCESS;
    }

    public TileEntityCarWorkshop getOwnTileEntity(World world, BlockPos pos) {
        TileEntity tile = world.getBlockEntity(pos);
        if (tile == null) {
            return null;
        }

        if (!(tile instanceof TileEntityCarWorkshop)) {
            return null;
        }

        return (TileEntityCarWorkshop) tile;
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);

        TileEntityCarWorkshop workshop = getOwnTileEntity(worldIn, pos);

        if (workshop == null) {
            return;
        }

        workshop.checkValidity();
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(VALID, false);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(VALID);
    }


    public void setValid(World world, BlockPos pos, BlockState state, boolean valid) {
        if (state.getValue(VALID).equals(valid)) {
            return;
        }

        TileEntity tileentity = world.getBlockEntity(pos);

        world.setBlock(pos, state.setValue(VALID, valid), 2);

        if (tileentity != null) {
            tileentity.clearRemoved();
            world.setBlockEntity(pos, tileentity);
        }
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        TileEntityCarWorkshop workshop = getOwnTileEntity(worldIn, pos);

        if (workshop != null) {
            InventoryHelper.dropContents(worldIn, pos, workshop);
        }

        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new TileEntityCarWorkshop();
    }
}
