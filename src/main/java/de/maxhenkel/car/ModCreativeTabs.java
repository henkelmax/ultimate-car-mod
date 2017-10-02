package de.maxhenkel.car;

import java.util.List;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModCreativeTabs {
	
	public static final CreativeTabs TAB_CAR = new CreativeTabs("car") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ModBlocks.TAR);
		}
		
		public void displayAllRelevantItems(List<ItemStack> list) {
			list.add(ModItems.BIO_DIESEL_BUCKET);
			list.add(ModItems.CANOLA_METHANOL_MIX_BUCKET);
			list.add(ModItems.CANOLA_OIL_BUCKET);
			list.add(ModItems.GLYCERIN_BUCKET);
			list.add(ModItems.METHANOL_BUCKET);
			super.displayAllRelevantItems(list);
		};
		
	};
	
}
