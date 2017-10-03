package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import de.maxhenkel.car.gui.GuiHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFluidExtractor extends BlockContainer{

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final IProperty<Boolean> DOWN = PropertyBool.create("down");
	public static final IProperty<Boolean> UP = PropertyBool.create("up");
	public static final IProperty<Boolean> NORTH = PropertyBool.create("north");
	public static final IProperty<Boolean> SOUTH = PropertyBool.create("south");
	public static final IProperty<Boolean> WEST = PropertyBool.create("west");
	public static final IProperty<Boolean> EAST = PropertyBool.create("east");
	
	protected BlockFluidExtractor() {
		super(new Material(MapColor.AIR));
		setRegistryName("fluid_extractor");
		setUnlocalizedName("fluid_extractor");
		setCreativeTab(ModCreativeTabs.TAB_CAR);
		setHardness(0.5F);
		useNeighborBrightness=true;
		setSoundType(SoundType.METAL);
		
		setDefaultState(blockState.getBaseState()
				.withProperty(FACING, EnumFacing.NORTH)
				.withProperty(UP, false)
				.withProperty(DOWN, false)
				.withProperty(NORTH, false)
				.withProperty(SOUTH, false)
				.withProperty(EAST, false)
				.withProperty(WEST, false)
				);
	}
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!playerIn.isSneaking()){
			playerIn.openGui(Main.instance(), GuiHandler.GUI_FLUID_EXTRACTOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
		
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFluidExtractor();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		
		float x1=0.375F;
		float y1=0.375F;
		float z1=0.375F;
		float x2=0.625F;
		float y2=0.625F;
		float z2=0.625F;
		
		
		if (BlockFluidPipe.isConnectedTo(source, pos, EnumFacing.UP)) {
			y2=1.0F;
		}

		if (BlockFluidPipe.isConnectedTo(source, pos, EnumFacing.DOWN)) {
			y1=0.0F;
		}

		if (BlockFluidPipe.isConnectedTo(source, pos, EnumFacing.SOUTH)) {
			z2=1.0F;
		}

		if (BlockFluidPipe.isConnectedTo(source, pos, EnumFacing.NORTH)) {
			z1=0.0F;
		}

		if (BlockFluidPipe.isConnectedTo(source, pos, EnumFacing.EAST)) {
			x2=1.0F;
		}

		if (BlockFluidPipe.isConnectedTo(source, pos, EnumFacing.WEST)) {
			x1=0.0F;
		}
		
		if(state.getValue(FACING).equals(EnumFacing.UP)){
			x1=0.0F;
			x2=1.0F;
			y2=1.0F;
			z1=0.0F;
			z2=1.0F;
		}else if(state.getValue(FACING).equals(EnumFacing.DOWN)){
			x1=0.0F;
			x2=1.0F;
			y1=0.0F;
			z1=0.0F;
			z2=1.0F;
		}else if(state.getValue(FACING).equals(EnumFacing.SOUTH)){
			x1=0.0F;
			x2=1.0F;
			y1=0.0F;
			y2=1.0F;
			z2=1.0F;
		}else if(state.getValue(FACING).equals(EnumFacing.NORTH)){
			x1=0.0F;
			x2=1.0F;
			y1=0.0F;
			y2=1.0F;
			z1=0.0F;
		}else if(state.getValue(FACING).equals(EnumFacing.EAST)){
			x2=1.0F;
			y1=0.0F;
			y2=1.0F;
			z1=0.0F;
			z2=1.0F;
		}else if(state.getValue(FACING).equals(EnumFacing.WEST)){
			x1=0.0F;
			y1=0.0F;
			y2=1.0F;
			z1=0.0F;
			z2=1.0F;
		}
		
		
		
		return new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		IBlockState actualState= state;
		
		EnumFacing facing=state.getValue(FACING);
		
		if (BlockFluidPipe.isConnectedTo(world, pos, EnumFacing.UP)&&!facing.equals(EnumFacing.UP)) {
			actualState=actualState.withProperty(UP, true);
		}

		if (BlockFluidPipe.isConnectedTo(world, pos, EnumFacing.DOWN)&&!facing.equals(EnumFacing.DOWN)) {
			actualState=actualState.withProperty(DOWN, true);
		}

		if (BlockFluidPipe.isConnectedTo(world, pos, EnumFacing.SOUTH)&&!facing.equals(EnumFacing.SOUTH)) {
			actualState=actualState.withProperty(SOUTH, true);
		}

		if (BlockFluidPipe.isConnectedTo(world, pos, EnumFacing.NORTH)&&!facing.equals(EnumFacing.NORTH)) {
			actualState=actualState.withProperty(NORTH, true);
		}

		if (BlockFluidPipe.isConnectedTo(world, pos, EnumFacing.EAST)&&!facing.equals(EnumFacing.EAST)) {
			actualState=actualState.withProperty(EAST, true);
		}

		if (BlockFluidPipe.isConnectedTo(world, pos, EnumFacing.WEST)&&!facing.equals(EnumFacing.WEST)) {
			actualState=actualState.withProperty(WEST, true);
		}
		
		return actualState;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumFacing = EnumFacing.getFront(meta);
		return this.getDefaultState().withProperty(FACING, enumFacing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, UP, DOWN, EAST, WEST, NORTH, SOUTH);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getActualState(getDefaultState(), world, pos).withProperty(FACING, facing.getOpposite());
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
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

}
