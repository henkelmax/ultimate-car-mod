package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.tileentity.TileEntitySplitTank;
import de.maxhenkel.car.gui.ContainerSplitTank;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.corelib.block.IItemBlock;
import de.maxhenkel.corelib.fluid.FluidUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class BlockSplitTank extends BlockBase implements ITileEntityProvider, IItemBlock {

    protected BlockSplitTank() {
        super(Properties.of(Material.METAL).strength(3F).sound(SoundType.STONE).noOcclusion());
        setRegistryName(new ResourceLocation(Main.MODID, "split_tank"));
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
        if (FluidUtils.tryFluidInteraction(player, handIn, worldIn, pos)) {
            return ActionResultType.SUCCESS;
        }

        if (!player.isShiftKeyDown()) {
            TileEntity te = worldIn.getBlockEntity(pos);

            if (!(te instanceof TileEntitySplitTank)) {
                return ActionResultType.FAIL;
            }
            TileEntitySplitTank splitTank = (TileEntitySplitTank) te;
            if (player instanceof ServerPlayerEntity) {
                TileEntityContainerProvider.openGui((ServerPlayerEntity) player, splitTank, (i, playerInventory, playerEntity) -> new ContainerSplitTank(i, splitTank, playerInventory));
            }
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.FAIL;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.box(0D, 0D, 0D, 16D, 24D, 16D);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return Block.box(0D, 0D, 0D, 16D, 24D, 16D);
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return Block.box(0D, 0D, 0D, 16D, 24D, 16D);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.box(0D, 0D, 0D, 16D, 24D, 16D);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (worldIn.isEmptyBlock(pos.above())) {
            worldIn.setBlockAndUpdate(pos.above(), ModBlocks.SPLIT_TANK_TOP.defaultBlockState());
        }

        TileEntity tileentity = worldIn.getBlockEntity(pos);

        if (tileentity instanceof IInventory) {
            InventoryHelper.dropContents(worldIn, pos, (IInventory) tileentity);
            worldIn.updateNeighbourForOutputSignal(pos, this);
        }

        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, worldIn, pos, newState, isMoving);

        BlockState stateUp = worldIn.getBlockState(pos.above());
        stateUp.getBlock();
        if (stateUp.getBlock().equals(ModBlocks.SPLIT_TANK_TOP)) {
            worldIn.destroyBlock(pos.above(), false);
        }
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new TileEntitySplitTank();
    }

}
