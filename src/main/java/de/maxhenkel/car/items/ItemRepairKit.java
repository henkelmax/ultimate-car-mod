package de.maxhenkel.car.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class ItemRepairKit extends Item {

    public ItemRepairKit(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag flag) {
        consumer.accept(Component.translatable("tooltip.repair_kit").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltipDisplay, consumer, flag);
    }

}
