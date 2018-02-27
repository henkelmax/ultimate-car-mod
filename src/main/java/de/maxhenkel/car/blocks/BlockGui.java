package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockGui extends BlockContainer{

	public static final PropertyBool POWERED = PropertyBool.create("powered");
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	protected BlockGui(Material materialIn, String name) {
		super(materialIn, MapColor.IRON);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTabs.TAB_CAR);
		
		setDefaultState(blockState.getBaseState().withProperty(POWERED, false).withProperty(FACING, EnumFacing.NORTH));
	}
	
	public abstract int getGUIID();
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!playerIn.isSneaking()){
			playerIn.openGui(Main.instance(), getGUIID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
		
		return false;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity!=null && tileentity instanceof IInventory) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
			worldIn.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, POWERED});
	}
	
	public boolean isPowered(IBlockState state){
		return state.getValue(POWERED);
	}
	
	private static boolean isPowered(int meta) {
		return (meta & 8) != 8;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing=EnumFacing.getFront(meta&7);//TODO change in new version to plane
		if(facing.equals(EnumFacing.UP)||facing.equals(EnumFacing.DOWN)){
			facing=EnumFacing.NORTH;
		}
		return getDefaultState().withProperty(FACING, facing).withProperty(POWERED, isPowered(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i= state.getValue(FACING).getIndex();
		
		if (!state.getValue(POWERED)) {
			i |= 8;
		}
		
		return i;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	public void setPowered(World world, BlockPos pos, IBlockState state, boolean powered){
		
		if(state.getValue(POWERED).equals(powered)){
			return;
		}
		
		TileEntity tileentity = world.getTileEntity(pos);

		world.setBlockState(pos, state.withProperty(POWERED, powered), 2);
		
		if (tileentity != null) {
			tileentity.validate();
			world.setTileEntity(pos, tileentity);
		}
	}

}
