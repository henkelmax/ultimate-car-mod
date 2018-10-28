package de.maxhenkel.car.items;

import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.entity.car.parts.Part;
import de.maxhenkel.car.entity.car.parts.PartRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public abstract class AbstractItemCarPart extends Item implements ICarPart{

    public AbstractItemCarPart(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTabs.TAB_CAR_PARTS);
    }

}