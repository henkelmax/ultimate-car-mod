package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemCanolaSeed extends BlockNamedItem{

	public ItemCanolaSeed() {
		super(ModBlocks.CANOLA_CROP, new Item.Properties().group(ModCreativeTabs.TAB_CAR));
		setRegistryName(new ResourceLocation(Main.MODID, "canola_seeds"));
	}
}
