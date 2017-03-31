package de.maxhenkel.car.items;

import de.maxhenkel.car.ModCreativeTabs;
import net.minecraft.item.Item;

public class ItemCraftingComponent extends Item{

	public ItemCraftingComponent(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTabs.TAB_CAR);
	}
	
}
