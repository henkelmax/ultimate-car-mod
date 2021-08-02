package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public abstract class AbstractItemCarPart extends Item implements ICarPart {

    public AbstractItemCarPart(String name) {
        super(new Item.Properties().tab(ModItemGroups.TAB_CAR_PARTS));
        setRegistryName(new ResourceLocation(Main.MODID, name));
    }

}