package de.maxhenkel.car.blocks;

import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.tileentity.TileEntityDynamo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrank extends Block{

	public static final IProperty<Integer> CRANK_POS = PropertyInteger.create("rotation", 0, 7);
	
	public BlockCrank() {
		super(Material.WOOD);
		
		setRegistryName("crank");
		setUnlocalizedName("crank");
		setCreativeTab(ModCreativeTabs.TAB_CAR);
		setHardness(0.5F);
		useNeighborBrightness=true;
		
		setDefaultState(blockState.getBaseState().withProperty(CRANK_POS, 0));
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0.2, 0, 0.2, 0.8, 0.6, 0.8);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity te=worldIn.getTileEntity(pos.down());
		
		if(!(te instanceof TileEntityDynamo)){
			return false;
		}
		
		TileEntityDynamo dyn=(TileEntityDynamo) te;
		
		dyn.addEnergy(dyn.generation);
		
		int i=state.getValue(CRANK_POS)+1;
		if(i>7){
			i=0;
		}
		
		worldIn.setBlockState(pos, state.withProperty(CRANK_POS, i), 3);
		
		return true;
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
	public int getMetaFromState(IBlockState state) {
		return state.getValue(CRANK_POS);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(CRANK_POS, meta);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, CRANK_POS);
	}
	
}
