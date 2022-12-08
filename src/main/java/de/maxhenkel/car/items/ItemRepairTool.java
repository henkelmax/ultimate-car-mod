package de.maxhenkel.car.items;

import net.minecraft.world.item.Item;

public class ItemRepairTool extends Item {

    public ItemRepairTool() {
        super(new Item.Properties().stacksTo(1).durability(200)/*.tab(ModItemGroups.TAB_CAR)*/); // TODO Fix creative tab
    }

}
