package de.maxhenkel.car.blocks;

import de.maxhenkel.car.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockSignPost extends Block{

	public static final AxisAlignedBB AABB = new AxisAlignedBB(7.5/16D, 0D, 7.5D/16D, 8.5/16D, 1D, 8.5D/16D);
	
	public BlockSignPost() {
		super(Material.IRON, MapColor.IRON);
		setUnlocalizedName("sign_post");
		setRegistryName("sign_post");
		setHardness(2.0F);
		setSoundType(SoundType.METAL);
		setCreativeTab(ModCreativeTabs.TAB_CAR);
		useNeighborBrightness=true;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
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
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}

}
