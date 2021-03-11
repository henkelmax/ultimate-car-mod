package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemCanola extends Item {

    public ItemCanola() {
        super(new Item.Properties().tab(ModItemGroups.TAB_CAR));
        setRegistryName(new ResourceLocation(Main.MODID, "canola"));
    }

}
