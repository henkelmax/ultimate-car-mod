package de.maxhenkel.car.items;

import de.maxhenkel.car.entity.car.parts.Part;
import net.minecraft.world.item.ItemStack;

public interface ICarPart {

    Part getPart(ItemStack stack);

}
