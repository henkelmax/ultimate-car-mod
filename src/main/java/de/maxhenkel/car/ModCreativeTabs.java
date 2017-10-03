package de.maxhenkel.car;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ModCreativeTabs {
	
	public static final CreativeTabs TAB_CAR = new CreativeTabs("car") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Item.getItemFromBlock(ModBlocks.TAR));
		}
		
		@Override
		public void displayAllRelevantItems(NonNullList<ItemStack> list) {
			list.add(ModItems.BIO_DIESEL_BUCKET);
			list.add(ModItems.CANOLA_METHANOL_MIX_BUCKET);
			list.add(ModItems.CANOLA_OIL_BUCKET);
			list.add(ModItems.GLYCERIN_BUCKET);
			list.add(ModItems.METHANOL_BUCKET);
			super.displayAllRelevantItems(list);
		}
		
	};
	
}
