package de.maxhenkel.car.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSplitTankTop extends Block {

	public BlockSplitTankTop() {
		super(Material.IRON);
		setUnlocalizedName("split_tank_top");
		setRegistryName("split_tank_top");
		setHardness(3.0F);
		setResistance(Float.MAX_VALUE);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		return ModBlocks.SPLIT_TANK.onBlockActivated(worldIn, pos.down(), worldIn.getBlockState(pos.down()), playerIn, hand, heldItem, side, hitX, hitY, hitZ);
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}

	@Override
	public boolean isFullyOpaque(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.BLOCK;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0, -1, 0, 1, 0.5F, 1);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return false;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		IBlockState stateDown = worldIn.getBlockState(pos.down());
		if (stateDown.getBlock().equals(ModBlocks.SPLIT_TANK)) {
			worldIn.setBlockToAir(pos.down());
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		super.onBlockHarvested(worldIn, pos, state, player);
		
		if(player.capabilities.isCreativeMode){
			return;
		}
		
		IBlockState stateDown = worldIn.getBlockState(pos.down());
		if (stateDown.getBlock().equals(ModBlocks.SPLIT_TANK)) {
			ModBlocks.SPLIT_TANK.dropBlockAsItem(worldIn, pos.down(), stateDown, 0);
		}
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		
		IBlockState stateDown=world.getBlockState(pos.down());
		if(stateDown.getBlock().equals(ModBlocks.SPLIT_TANK)){
			return ModBlocks.SPLIT_TANK.getPickBlock(stateDown, target, world, pos.down(), player);
		}
		
		return super.getPickBlock(stateDown, target, world, pos, player);
	}
}
