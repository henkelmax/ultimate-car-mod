package de.maxhenkel.car.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
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

public class BlockFuelStationTop extends Block {

	public BlockFuelStationTop() {
		super(Material.IRON);
		setUnlocalizedName("fuelstation_top");
		setRegistryName("fuelstation_top");
		setHardness(4.0F);
		setResistance(Float.MAX_VALUE);
		setSoundType(SoundType.METAL);
		useNeighborBrightness = true;
	}
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return ModBlocks.FUEL_STATION.onBlockActivated(worldIn, pos.down(), worldIn.getBlockState(pos.down()), playerIn, hand, facing, hitX, hitY, hitZ);
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
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.BLOCK;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		IBlockState stateDown = source.getBlockState(pos.down());
		if (stateDown == null || stateDown.getBlock() == null || !stateDown.getBlock().equals(ModBlocks.FUEL_STATION)) {
			return BlockFuelStation.AABB_NORTH_SOUTH.offset(0, -1, 0);
		}

		EnumFacing facing = stateDown.getValue(BlockOrientableHorizontal.FACING);

		if (facing == null) {
			return BlockFuelStation.AABB_NORTH_SOUTH.offset(0, -1, 0);
		}

		if (facing.equals(EnumFacing.NORTH) || facing.equals(EnumFacing.SOUTH)) {
			return BlockFuelStation.AABB_NORTH_SOUTH.offset(0, -1, 0);
		} else {
			return BlockFuelStation.AABB_EAST_WEST.offset(0, -1, 0);
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		IBlockState stateDown = worldIn.getBlockState(pos.down());
		if(stateDown==null || stateDown.getBlock() == null || !stateDown.getBlock().equals(ModBlocks.FUEL_STATION)){
			return BlockFuelStation.AABB_NORTH_SOUTH.offset(0, -1, 0);
		}
		
		EnumFacing facing = stateDown.getValue(BlockOrientableHorizontal.FACING);

		if (facing.equals(EnumFacing.NORTH) || facing.equals(EnumFacing.SOUTH)) {
			return BlockFuelStation.AABB_NORTH_SOUTH.offset(0, -1, 0);
		} else {
			return BlockFuelStation.AABB_EAST_WEST.offset(0, -1, 0);
		}
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
		if (stateDown != null && stateDown.getBlock() != null && stateDown.getBlock().equals(ModBlocks.FUEL_STATION)) {
			worldIn.setBlockToAir(pos.down());
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		super.onBlockHarvested(worldIn, pos, state, player);

		if (player != null && player.capabilities.isCreativeMode) {
			return;
		}

		IBlockState stateDown = worldIn.getBlockState(pos.down());
		if (stateDown != null && stateDown.getBlock() != null && stateDown.getBlock().equals(ModBlocks.FUEL_STATION)) {
			ModBlocks.FUEL_STATION.dropBlockAsItem(worldIn, pos.down(), stateDown, 0);
			worldIn.setBlockToAir(pos.down());
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {

		IBlockState stateDown = world.getBlockState(pos.down());
		if (stateDown != null && stateDown.getBlock() != null && stateDown.getBlock().equals(ModBlocks.FUEL_STATION)) {
			return ModBlocks.FUEL_STATION.getPickBlock(stateDown, target, world, pos.down(), player);
		}

		return super.getPickBlock(stateDown, target, world, pos, player);
	}

}
