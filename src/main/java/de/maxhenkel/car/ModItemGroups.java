package de.maxhenkel.car;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups {

    public static final CreativeModeTab TAB_CAR = new CreativeModeTab("car") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.ASPHALT.get());
        }
    };

    public static final CreativeModeTab TAB_CAR_PARTS = new CreativeModeTab("car_parts") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.OAK_BODY.get());
        }
    };

}
