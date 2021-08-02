package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemCanola extends Item {

    public ItemCanola() {
        super(new Item.Properties().tab(ModItemGroups.TAB_CAR));
        setRegistryName(new ResourceLocation(Main.MODID, "canola"));
    }

}
