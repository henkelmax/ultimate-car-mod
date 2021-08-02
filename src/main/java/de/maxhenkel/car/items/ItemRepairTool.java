package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemRepairTool extends Item {

    public ItemRepairTool(String name) {
        super(new Item.Properties().stacksTo(1).durability(200).tab(ModItemGroups.TAB_CAR));
        setRegistryName(new ResourceLocation(Main.MODID, name));
    }

}
