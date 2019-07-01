package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractItemCarPart extends Item implements ICarPart{

    public AbstractItemCarPart(String name) {
        super(new Item.Properties().group(ModCreativeTabs.TAB_CAR_PARTS));
        setRegistryName(new ResourceLocation(Main.MODID, name));
    }

}