package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.tileentity.TileEntitySplitTank;
import de.maxhenkel.car.gui.ContainerSplitTank;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.tools.FluidUtils;
import de.maxhenkel.tools.IItemBlock;
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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockSplitTank extends BlockBase implements ITileEntityProvider, IItemBlock {

    protected BlockSplitTank() {
        super(Properties.create(Material.IRON).hardnessAndResistance(3F).sound(SoundType.STONE));
        setRegistryName(new ResourceLocation(Main.MODID, "split_tank"));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ModItemGroups.TAB_CAR)) {
            @Override
            protected boolean canPlace(BlockItemUseContext context, BlockState state) {
                if (!context.getWorld().isAirBlock(context.getPos().up())) {
                    return false;
                }
                return super.canPlace(context, state);
            }
        }.setRegistryName(this.getRegistryName());
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (FluidUtils.tryFluidInteraction(player, handIn, worldIn, pos)) {
            return true;
        }

        if (!player.isSneaking()) {
            TileEntity te = worldIn.getTileEntity(pos);

            if (!(te instanceof TileEntitySplitTank)) {
                return false;
            }
            TileEntitySplitTank splitTank = (TileEntitySplitTank) te;
            if (player instanceof ServerPlayerEntity) {
                TileEntityContainerProvider.openGui((ServerPlayerEntity) player, splitTank, (i, playerInventory, playerEntity) -> new ContainerSplitTank(i, splitTank, playerInventory));
            }
            return true;
        }

        return false;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(0D, 0D, 0D, 16D, 24D, 16D);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return Block.makeCuboidShape(0D, 0D, 0D, 16D, 24D, 16D);
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return Block.makeCuboidShape(0D, 0D, 0D, 16D, 24D, 16D);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(0D, 0D, 0D, 16D, 24D, 16D);
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canRenderInLayer(BlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.CUTOUT || layer == BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader world, BlockPos pos, Direction face) {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (worldIn.isAirBlock(pos.up())) {
            worldIn.setBlockState(pos.up(), ModBlocks.SPLIT_TANK_TOP.getDefaultState());
        }

        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity != null && tileentity instanceof IInventory) {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onReplaced(state, worldIn, pos, newState, isMoving);

        BlockState stateUp = worldIn.getBlockState(pos.up());
        if (stateUp != null && stateUp.getBlock() != null && stateUp.getBlock().equals(ModBlocks.SPLIT_TANK_TOP)) {
            worldIn.destroyBlock(pos.up(), false);
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntitySplitTank();
    }
}
