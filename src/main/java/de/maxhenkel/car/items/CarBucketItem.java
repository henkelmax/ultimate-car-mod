package de.maxhenkel.car.items;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.wrappers.FluidBucketWrapper;

public class CarBucketItem extends BucketItem {

    public CarBucketItem(Fluid fluid, Properties properties) {
        super(fluid, properties.craftRemainder(Items.BUCKET).stacksTo(1));
    }

    public static IFluidHandlerItem getFluidHandler(ItemStack stack) {
        return new FluidBucketWrapper(stack);
    }

}
