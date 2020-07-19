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
        super(new Item.Properties().maxStackSize(1).group(ModItemGroups.TAB_CAR).maxDamage(500));
        setRegistryName(new ResourceLocation(Main.MODID, "battery"));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        int damage = getMaxDamage(stack) - getDamage(stack);

        tooltip.add(new TranslationTextComponent("tooltip.battery_energy", new StringTextComponent(String.valueOf(damage)).func_240699_a_(TextFormatting.DARK_GRAY)).func_240699_a_(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("tooltip.battery").func_240699_a_(TextFormatting.GRAY));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getWorld().getBlockState(context.getPos()).getBlock().equals(ModBlocks.GENERATOR)) {
            IEnergyStorage storage = EnergyUtils.getEnergyStorage(context.getWorld(), context.getPos(), context.getFace());
            if (storage != null) {
                ItemStack stack = context.getPlayer().getHeldItem(context.getHand());

                int energyToFill = stack.getDamage();

                int amount = storage.extractEnergy(energyToFill, false);

                stack.setDamage(energyToFill - amount);
                context.getPlayer().setHeldItem(context.getHand(), stack);
                return ActionResultType.SUCCESS;
            }
        }

        return super.onItemUse(context);
    }

}
