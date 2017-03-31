package de.maxhenkel.car.blocks;

import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCarWorkshopOutter extends Block {

	public static final PropertyInteger POSITION = PropertyInteger.create("position", 0, 8);

	public BlockCarWorkshopOutter() {
		super(Material.IRON);
		setUnlocalizedName("car_workshop_outter");
		setRegistryName("car_workshop_outter");
		setCreativeTab(ModCreativeTabs.TAB_CAR);
		setHardness(3.0F);

		this.setDefaultState(blockState.getBaseState().withProperty(POSITION, 0));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		BlockPos tePos=findCenter(worldIn, pos);
		
		if(tePos==null){
			return false;
		}
		
		return ModBlocks.CAR_WORKSHOP.onBlockActivated(worldIn, tePos, worldIn.getBlockState(tePos), playerIn, hand, heldItem, side, hitX, hitY, hitZ);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		
		validate(worldIn, pos);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		
		validate(worldIn, pos);
	}
	
	private void validate(World worldIn, BlockPos pos){
		BlockPos tePos=findCenter(worldIn, pos);
		
		if(tePos==null){
			return;
		}
		
		TileEntity te=worldIn.getTileEntity(tePos);
		
		if(!(te instanceof TileEntityCarWorkshop)){
			return;
		}
		
		TileEntityCarWorkshop workshop=(TileEntityCarWorkshop) te;
		
		workshop.checkValidity();
	}

	private static BlockPos findCenter(World world, BlockPos pos) {
		if (isCenter(world, pos.add(0, 0, 1))) {
			return pos.add(0, 0, 1);
		}
		if (isCenter(world, pos.add(1, 0, 0))) {
			return pos.add(1, 0, 0);
		}
		if (isCenter(world, pos.add(1, 0, 1))) {
			return pos.add(1, 0, 1);
		}
		if (isCenter(world, pos.add(0, 0, -1))) {
			return pos.add(0, 0, -1);
		}
		if (isCenter(world, pos.add(-1, 0, 0))) {
			return pos.add(-1, 0, 0);
		}
		if (isCenter(world, pos.add(-1, 0, -1))) {
			return pos.add(-1, 0, -1);
		}
		if (isCenter(world, pos.add(-1, 0, 1))) {
			return pos.add(-1, 0, 1);
		}
		if (isCenter(world, pos.add(1, 0, -1))) {
			return pos.add(1, 0, -1);
		}
		return null;
	}

	private static boolean isCenter(World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock().equals(ModBlocks.CAR_WORKSHOP);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(POSITION);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(POSITION, meta);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { POSITION });
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(POSITION, 0);
	}

}
