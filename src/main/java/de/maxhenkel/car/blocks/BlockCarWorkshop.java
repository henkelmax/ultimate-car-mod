package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.gui.ContainerCarWorkshopCrafting;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.corelib.block.IItemBlock;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class BlockCarWorkshop extends BlockBase implements EntityBlock, IItemBlock {

    public static final BooleanProperty VALID = BooleanProperty.create("valid");

    protected BlockCarWorkshop() {
        super(Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(3F).sound(SoundType.METAL));
        this.registerDefaultState(stateDefinition.any().setValue(VALID, false));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties()/*.tab(ModItemGroups.TAB_CAR)*/); // TODO Fix creative tab
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        TileEntityCarWorkshop workshop = getOwnTileEntity(worldIn, pos);

        if (workshop == null) {
            return InteractionResult.FAIL;
        }

        if (!workshop.areBlocksAround()) {
            return InteractionResult.FAIL;
        }
        if (player instanceof ServerPlayer) {
            TileEntityContainerProvider.openGui((ServerPlayer) player, workshop, (i, playerInventory, playerEntity) -> new ContainerCarWorkshopCrafting(i, workshop, playerInventory));
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
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        TileEntityCarWorkshop workshop = getOwnTileEntity(worldIn, pos);

        if (workshop != null) {
            Containers.dropContents(worldIn, pos, workshop);
        }

        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TileEntityCarWorkshop(blockPos, blockState);
    }

}
