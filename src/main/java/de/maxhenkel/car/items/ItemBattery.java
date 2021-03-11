package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.corelib.energy.EnergyUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBattery extends Item {

    public ItemBattery() {
        super(new Item.Properties().stacksTo(1).tab(ModItemGroups.TAB_CAR).durability(500));
        setRegistryName(new ResourceLocation(Main.MODID, "battery"));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        int damage = getMaxDamage(stack) - getDamage(stack);

        tooltip.add(new TranslationTextComponent("tooltip.battery_energy", new StringTextComponent(String.valueOf(damage)).withStyle(TextFormatting.DARK_GRAY)).withStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("tooltip.battery").withStyle(TextFormatting.GRAY));

        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        if (context.getLevel().getBlockState(context.getClickedPos()).getBlock().equals(ModBlocks.GENERATOR)) {
            IEnergyStorage storage = EnergyUtils.getEnergyStorage(context.getLevel(), context.getClickedPos(), context.getClickedFace());
            if (storage != null) {
                ItemStack stack = context.getPlayer().getItemInHand(context.getHand());

                int energyToFill = stack.getDamageValue();

                int amount = storage.extractEnergy(energyToFill, false);

                stack.setDamageValue(energyToFill - amount);
                context.getPlayer().setItemInHand(context.getHand(), stack);
                return ActionResultType.SUCCESS;
            }
        }

        return super.useOn(context);
    }

}
