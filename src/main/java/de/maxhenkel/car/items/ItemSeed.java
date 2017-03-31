package de.maxhenkel.car.items;

import de.maxhenkel.car.blocks.BlockCrop;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemSeeds;

public class ItemSeed extends ItemSeeds{

	protected BlockCrop crops;

	public ItemSeed(String name, BlockCrop crops) {
		super(crops, null);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.crops = crops;
	}
	
	public boolean canPlant(IBlockState state){
		return crops.canPlant(state);
	}

}

