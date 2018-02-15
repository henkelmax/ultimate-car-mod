package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.tileentity.TileEntitySplitTank;
import de.maxhenkel.car.gui.GuiHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BlockSplitTank extends BlockContainer{

	protected BlockSplitTank() {
		super(Material.IRON);
		setUnlocalizedName("split_tank");
		setRegistryName("split_tank");
		setCreativeTab(ModCreativeTabs.TAB_CAR);
		setHardness(3.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySplitTank();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = playerIn.getHeldItem(hand);

		if (stack != null) {
			FluidStack fluidStack = FluidUtil.getFluidContained(stack);

			if (fluidStack != null) {
				boolean success = BlockTank.handleEmpty(stack, worldIn, pos, playerIn, hand);
				if (success) {
					return true;
				}
			}
			IFluidHandler handler = FluidUtil.getFluidHandler(stack);

			if (handler != null) {
				boolean success1 = BlockTank.handleFill(stack, worldIn, pos, playerIn, hand);
				if (success1) {
					return true;
				}
			}

		}

		if(!playerIn.isSneaking()){
			playerIn.openGui(Main.instance(), GuiHandler.GUI_SPLITTANK, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
		
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return new AxisAlignedBB(0, 0, 0, 1, 1.5, 1);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0, 0, 0, 1, 1.5, 1);
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
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
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		
		if(!worldIn.isAirBlock(pos.up())){
			return false;
		}
		
		return super.canPlaceBlockAt(worldIn, pos);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		
		if(worldIn.isAirBlock(pos.up())){
			worldIn.setBlockState(pos.up(), ModBlocks.SPLIT_TANK_TOP.getDefaultState());
		}
		
		TileEntity tileentity=worldIn.getTileEntity(pos);
		
		if (tileentity!=null && tileentity instanceof IInventory) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
			worldIn.updateComparatorOutputLevel(pos, this);
		}
		
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		
		IBlockState stateUp=worldIn.getBlockState(pos.up());
		if(stateUp.getBlock().equals(ModBlocks.SPLIT_TANK_TOP)){
			worldIn.setBlockToAir(pos.up());
		}
	}

}
