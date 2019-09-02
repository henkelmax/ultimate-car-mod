package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemCraftingComponent extends Item {

    public ItemCraftingComponent(String name) {
        super(new Item.Properties().group(ModItemGroups.TAB_CAR));
        setRegistryName(new ResourceLocation(Main.MODID, name));
    }

}
