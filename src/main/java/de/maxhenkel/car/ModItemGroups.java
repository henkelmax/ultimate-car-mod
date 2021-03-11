package de.maxhenkel.car;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {

    public static final ItemGroup TAB_CAR = new ItemGroup("car") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.ASPHALT);
        }
    };

    public static final ItemGroup TAB_CAR_PARTS = new ItemGroup("car_parts") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.OAK_BODY);
        }
    };

}
