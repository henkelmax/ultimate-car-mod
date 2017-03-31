package de.maxhenkel.car.items;

import de.maxhenkel.car.ModCreativeTabs;
import net.minecraft.item.Item;

public class ItemRepairTool extends Item{

	public ItemRepairTool(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTabs.TAB_CAR);
		setMaxDamage(200);
		setMaxStackSize(1);
	}
	
}
