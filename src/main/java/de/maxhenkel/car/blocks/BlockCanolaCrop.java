package de.maxhenkel.car.blocks;

import de.maxhenkel.car.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class BlockCanolaCrop extends BlockCrop{

	public BlockCanolaCrop() {
		super("canola");
	}

	@Override
	protected Item getSeed() {
		return ModItems.CANOLA_SEEDS;
	}

	@Override
	protected Item getCrop() {
		return ModItems.CANOLA;
	}

	@Override
	public int getMaxAge() {
		return 3;
	}

	@Override
	public boolean isSoil(IBlockState state) {
		Block block = state.getBlock();

		if (block.equals(Blocks.FARMLAND)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canPlant(IBlockState state) {
		return isSoil(state);
	}

}
