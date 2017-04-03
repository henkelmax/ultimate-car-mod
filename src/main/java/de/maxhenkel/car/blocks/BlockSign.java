package de.maxhenkel.car.blocks;

import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.tileentity.TileEntitySignCar;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSign extends BlockOrientableHorizontal {

	protected BlockSign() {
		super(Material.IRON, MapColor.AIR);
		setRegistryName("sign");
		setUnlocalizedName("sign");
		setCreativeTab(ModCreativeTabs.TAB_CAR);
		setHardness(0.25F);
		useNeighborBrightness = true;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if(placer instanceof EntityPlayer){
			//EntityPlayer player=(EntityPlayer) placer;
			TileEntity te=worldIn.getTileEntity(pos);
			
			if(te instanceof TileEntitySignCar){
				//TileEntitySign sign=(TileEntitySign) te;
				//TileEntitySignRenderer
				//player.openGui(Main.instance(), GuiHandler.GUI_SIGN, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
			
		}
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return new AxisAlignedBB(0.4375, 0, 0.4375, 0.5625, 1, 0.5625);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (state.getValue(FACING).equals(EnumFacing.NORTH) || state.getValue(FACING).equals(EnumFacing.SOUTH)) {
			return new AxisAlignedBB(0, 0, 0.4375, 1, 1, 0.5625);
		} else {
			return new AxisAlignedBB(0.4375, 0, 0, 0.5625, 1, 1);
		}
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
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
	public boolean isBlockNormalCube(IBlockState state) {
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
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySignCar();
	}

}
