package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemCraftingComponent extends Item {

    public ItemCraftingComponent(String name) {
        super(new Item.Properties().group(ModCreativeTabs.TAB_CAR));
        setRegistryName(new ResourceLocation(Main.MODID, name));
    }

}
