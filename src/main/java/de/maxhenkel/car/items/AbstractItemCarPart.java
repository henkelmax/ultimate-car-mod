package de.maxhenkel.car.items;

import net.minecraft.world.item.Item;

public abstract class AbstractItemCarPart extends Item implements ICarPart {

    public AbstractItemCarPart() {
        super(new Item.Properties()/*.tab(ModItemGroups.TAB_CAR_PARTS)*/); // TODO Fix creative tab
    }

}