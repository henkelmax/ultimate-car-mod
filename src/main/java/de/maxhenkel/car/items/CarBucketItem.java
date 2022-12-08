package de.maxhenkel.car.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

import javax.annotation.Nullable;

public class CarBucketItem extends BucketItem {

    public CarBucketItem(Fluid fluid) {
        super(fluid, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)/*.tab(ModItemGroups.TAB_CAR)*/); // TODO Fix creative tab
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new FluidBucketWrapper(stack);
    }

}
