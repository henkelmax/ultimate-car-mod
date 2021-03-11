package de.maxhenkel.car.items;

import de.maxhenkel.car.ModItemGroups;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

import javax.annotation.Nullable;

public class CarBucketItem extends BucketItem {

    public CarBucketItem(Fluid containedFluidIn, ResourceLocation registryName) {
        super(() -> containedFluidIn, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ModItemGroups.TAB_CAR));
        setRegistryName(registryName);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new FluidBucketWrapper(stack);
    }
}
