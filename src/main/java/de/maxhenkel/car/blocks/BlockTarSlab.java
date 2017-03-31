package de.maxhenkel.car.blocks;

import de.maxhenkel.car.IDrivable;
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

public class BlockTarSlab extends Block implements IDrivable {

	public BlockTarSlab() {
		super(Material.ROCK, MapColor.OBSIDIAN);
		setUnlocalizedName("tar_slab");
		setRegistryName("tar_slab");
		setHardness(2.2F);
		setResistance(20.0F);
		setSoundType(SoundType.STONE);
		setCreativeTab(ModCreativeTabs.TAB_CAR);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0, 0, 0, 1, 0.5F, 1);
	}
	
	@Override
	public float getSpeedModifier() {
		return 1.0F;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
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
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}
}