package de.maxhenkel.car.items;

import de.maxhenkel.car.ModItemGroups;
import net.minecraft.world.item.Item;

public abstract class AbstractItemCarPart extends Item implements ICarPart {

    public AbstractItemCarPart() {
        super(new Item.Properties().tab(ModItemGroups.TAB_CAR_PARTS));
    }

}