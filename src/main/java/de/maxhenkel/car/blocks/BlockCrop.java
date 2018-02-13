package de.maxhenkel.car.blocks;

import java.util.Random;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockCrop extends BlockCrops{

	public static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D) };

	public BlockCrop(String name) {
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setDefaultState(blockState.getBaseState().withProperty(getAgeProperty(), 0));
	}

	@Override
	protected abstract Item getSeed();

	@Override
	protected abstract Item getCrop();

	@Override
	public PropertyInteger getAgeProperty() {
		return PropertyInteger.create("age", 0, getMaxAge());
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		int age=getAge(state);
		if(age>=CROPS_AABB.length){
			return CROPS_AABB[CROPS_AABB.length-1];
		}else{
			return CROPS_AABB[age];
		}
		
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {

		if (worldIn.getLightFromNeighbors(pos.up()) >= getMinLightLevel()) {
			int i = this.getAge(state);

			if (i < this.getMaxAge()) {

				if (rand.nextInt(getGrowthChance()) == 0) {
					worldIn.setBlockState(pos, this.withAge(i + 1), 2);
				}
			}
		}
	}

	@Override
	public abstract int getMaxAge();

	/*
	 * Default is 8
	 */
	public int getMinLightLevel(){
		return 8;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { getAgeProperty() });
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return isSoil(state);
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		IBlockState soil = worldIn.getBlockState(pos.down());
		return (worldIn.getLight(pos) >= getMinLightLevel() || worldIn.canSeeSky(pos)) && isSoil(soil);
	}

	public abstract boolean isSoil(IBlockState state);

	public abstract boolean canPlant(IBlockState state);

	public boolean isMaxAge(IBlockState state) {
		return getAge(state) >= getMaxAge();
	}

	public int getGrowthChance() {
		return 20;
	}

}

