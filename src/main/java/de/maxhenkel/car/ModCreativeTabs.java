package de.maxhenkel.car;

import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModCreativeTabs {
	
	public static final CreativeTabs TAB_CAR = new CreativeTabs("car") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ModBlocks.TAR);
		}
	};
	
}
