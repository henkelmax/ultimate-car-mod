package de.maxhenkel.car.items;

import java.util.List;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemRepairKit extends Item {

    public ItemRepairKit() {
        super(new Item.Properties().maxStackSize(1).group(ModItemGroups.TAB_CAR));
        setRegistryName(new ResourceLocation(Main.MODID, "repair_kit"));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("tooltip.repair_kit").func_240699_a_(TextFormatting.GRAY));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
