package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemCanola extends Item {

    public ItemCanola() {
        super(new Item.Properties().group(ModCreativeTabs.TAB_CAR));
        setRegistryName(new ResourceLocation(Main.MODID, "canola"));
    }

}
