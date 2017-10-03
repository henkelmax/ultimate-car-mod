package de.maxhenkel.car.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPaint extends Block {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	private boolean isYellow;
	
	public BlockPaint(EnumPaintType type, boolean isYellow) {
		super(new Material(MapColor.AIR));
		this.isYellow=isYellow;

		if (isYellow) {
			setUnlocalizedName(type.name + "_yellow");
			setRegistryName(type.name + "_yellow");
		} else {
			setUnlocalizedName(type.name);
			setRegistryName(type.name);
		}

		setHardness(2.0F);

		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumFacing = EnumFacing.getFront(meta);

		if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
			enumFacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumFacing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0, 0, 0, 1, 0.01, 1);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
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
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		this.checkForDrop(worldIn, pos, state);
	}

	private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
		if (!this.canBlockStay(worldIn, pos)) {
			worldIn.setBlockToAir(pos);
			return false;
		} else {
			return true;
		}
	}

	private boolean canBlockStay(World worldIn, BlockPos pos) {
		return canPlaceBlockAt(worldIn, pos);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return canPlaceBlockAt((IBlockAccess) worldIn, pos);
	}

	public static boolean canPlaceBlockAt(IBlockAccess worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos.down());
		return state.getBlock().isNormalCube(state, worldIn, pos.down());
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return (Item) null;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	public boolean isYellow(){
		return isYellow;
	}

	public static enum EnumPaintType {
		ARROW_FRONT_LEFT_RIGHT_LONG("arrow_front_left_right_long"), 
		ARROW_LONG("arrow_long"), 
		ARROW_FRONT_LEFT_LONG("arrow_front_left_long"), 
		ARROW_FRONT_RIGHT_LONG("arrow_front_right_long"), 
		ARROW_LEFT_RIGHT("arrow_front_left_right"), 
		ARROW("arrow"),
		ARROW_FRONT_LEFT("arrow_front_left"), 
		ARROW_FRONT_RIGHT("arrow_front_right"), 
		ARROW_LEFT("arrow_left"),
		ARROW_RIGHT("arrow_right"),
		ARROW_LEFT_DIA("arrow_left_dia"),
		LINE_RIGHT_DIA("arrow_right_dia"),
		LINE_MIDDLE("line_middle"), 
		LINE_LONG("line_long"), 
		LINE_END("line_end"), 
		LINE_SIDE_MIDDLE("line_side_middle"),
		LINE_SIDE_LONG("line_side_long"), 
		LINE_SIDE_END("line_side_end"), 
		LINE_SIDE_LONG_LEFT("line_side_long_left"), 
		LINE_SIDE_LONG_LEFT_FRONT("line_side_long_left_front"), 
		LINE_MIDDLE_EDGE("line_middle_edge"), 
		LINE_CORNER("line_corner"),
		LINE_DOUBLE("line_double"),
		ARROW_ZEBRAS("arrow_zebras"),
		ARROW_P("arrow_p"),
		ARROW_NO_PARKING("arrow_no_parking"),
		ARROW_CROSS("arrow_cross");

		private String name;

		private EnumPaintType(String name) {
			this.name = name;
		}
	}

}
