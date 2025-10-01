package de.maxhenkel.car.items;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;

public class CarBucketItem extends BucketItem {

    public CarBucketItem(Fluid fluid, Properties properties) {
        super(fluid, properties.craftRemainder(Items.BUCKET).stacksTo(1));
    }

}
