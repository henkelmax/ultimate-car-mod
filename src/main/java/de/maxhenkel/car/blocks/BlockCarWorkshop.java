package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.gui.GuiHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCarWorkshop extends BlockContainer {

	public static final PropertyBool VALID = PropertyBool.create("valid");

	protected BlockCarWorkshop() {
		super(Material.IRON);
		setUnlocalizedName("car_workshop");
		setRegistryName("car_workshop");
		setCreativeTab(ModCreativeTabs.TAB_CAR);
		setHardness(3.0F);

		this.setDefaultState(blockState.getBaseState().withProperty(VALID, false));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityCarWorkshop workshop=getOwnTileEntity(worldIn, pos);
		
		if(workshop==null){
			return false;
		}
		
		if(!workshop.areBlocksAround()){
			return false;
		}
		//open gui
		playerIn.openGui(Main.MODID, GuiHandler.GUI_CAR_WORKSHOP_CRAFTING, worldIn, pos.getX(), pos.getY(), pos.getZ());
		
		return true;
	}
	
	public TileEntityCarWorkshop getOwnTileEntity(World world, BlockPos pos){
		TileEntity tile=world.getTileEntity(pos);
		if(tile==null){
			return null;
		}
		
		if(!(tile instanceof TileEntityCarWorkshop)){
			return null;
		}
		
		return (TileEntityCarWorkshop) tile;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		
		TileEntityCarWorkshop workshop=getOwnTileEntity(worldIn, pos);
		
		if(workshop==null){
			return;
		}
		
		workshop.checkValidity();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCarWorkshop();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		if (state.getValue(VALID)) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		if (meta == 0) {
			return getDefaultState().withProperty(VALID, false);
		} else {
			return getDefaultState().withProperty(VALID, true);
		}

	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { VALID });
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(VALID, false);
	}

	public void setValid(World world, BlockPos pos, IBlockState state, boolean valid) {
		if (state.getValue(VALID).equals(valid)) {
			return;
		}

		TileEntity tileentity = world.getTileEntity(pos);

		world.setBlockState(pos, state.withProperty(VALID, valid), 2);

		if (tileentity != null) {
			tileentity.validate();
			world.setTileEntity(pos, tileentity);
		}
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityCarWorkshop workshop=getOwnTileEntity(worldIn, pos);
		
		if(workshop!=null){
			InventoryHelper.dropInventoryItems(worldIn, pos, workshop);
		}
		super.breakBlock(worldIn, pos, state);
	}

}
