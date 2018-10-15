package de.maxhenkel.car.items;

import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.entity.car.parts.Part;
import de.maxhenkel.car.entity.car.parts.PartRegistry;
import net.minecraft.item.Item;
import javax.annotation.Nullable;

public class ItemCarPart extends Item {

    private String partName;

    public ItemCarPart(String name, String partName) {
        this.partName=partName;
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTabs.TAB_CAR);
    }

    @Nullable
    public Part getPart(){
        return PartRegistry.getPart(partName);
    }

    public String getPartName(){
        return partName;
    }

}