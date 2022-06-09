package de.maxhenkel.car.items;

import java.util.List;

import de.maxhenkel.car.ModItemGroups;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class ItemRepairKit extends Item {

    public ItemRepairKit() {
        super(new Item.Properties().stacksTo(1).tab(ModItemGroups.TAB_CAR));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.repair_kit").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

}
