package de.maxhenkel.car.items;

import de.maxhenkel.car.entity.car.parts.Part;
import net.minecraft.world.item.ItemStack;

public class ItemCarPart extends AbstractItemCarPart {

    private Part part;

    public ItemCarPart(String name, Part part) {
        super(name);
        this.part = part;
    }

    @Override
    public Part getPart(ItemStack stack) {
        return part;
    }
}
