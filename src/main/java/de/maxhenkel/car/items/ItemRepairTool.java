package de.maxhenkel.car.items;

import net.minecraft.world.item.Item;

public class ItemRepairTool extends Item {

    public ItemRepairTool(Properties properties) {
        super(properties.stacksTo(1).durability(200));
    }

}
