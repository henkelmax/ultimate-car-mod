package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockSplitTankTop extends Block {

    public BlockSplitTankTop() {
        super(Properties.create(Material.IRON).hardnessAndResistance(3F).sound(SoundType.STONE));
        setRegistryName(new ResourceLocation(Main.MODID, "split_tank_top"));
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        return ModBlocks.SPLIT_TANK.onBlockActivated(worldIn.getBlockState(pos.down()), worldIn, pos.down(), player, handIn, hit);
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
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader world, BlockPos pos, Direction face) {
        return false;
    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return Block.makeCuboidShape(0D, -16D, 0D, 16D, 8D, 16D);
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            super.onReplaced(state, worldIn, pos, newState, isMoving);

            BlockState stateDown = worldIn.getBlockState(pos.down());
            if (stateDown != null &&stateDown.getBlock().equals(ModBlocks.SPLIT_TANK)) {
                worldIn.destroyBlock(pos.down(), false);
            }
        }

        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);

        if (player.abilities.isCreativeMode) {
            return;
        }

        BlockState stateDown = worldIn.getBlockState(pos.down());
        if (stateDown != null && stateDown.getBlock() != null && stateDown.getBlock().equals(ModBlocks.SPLIT_TANK)) {
            //TODO check if it works
            //ModBlocks.SPLIT_TANK.dropBlockAsItem(worldIn, pos.down(), stateDown, 0);
            InventoryHelper.spawnItemStack(worldIn, pos.down().getX()+0.5D, pos.down().getY()+0.5D, pos.down().getZ()+0.5D, new ItemStack(ModBlocks.SPLIT_TANK.toItem()));
        }
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        BlockState stateDown = world.getBlockState(pos.down());
        if (stateDown.getBlock().equals(ModBlocks.SPLIT_TANK)) {
            return ModBlocks.SPLIT_TANK.getPickBlock(stateDown, target, world, pos.down(), player);
        }
        return super.getPickBlock(stateDown, target, world, pos, player);
    }

}
