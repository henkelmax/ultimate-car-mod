package de.maxhenkel.car.items;

import de.maxhenkel.car.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.List;

public class ItemBattery extends Item {

	public ItemBattery() {
		setMaxStackSize(1);
		setUnlocalizedName("battery");
		setRegistryName("battery");
		setCreativeTab(ModCreativeTabs.TAB_CAR);
		setMaxDamage(500);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		int damage=getMaxDamage(stack)-getDamage(stack);

		tooltip.add(new TextComponentTranslation("tooltip.battery_energy", damage).getFormattedText());
		tooltip.add(new TextComponentTranslation("tooltip.battery", damage).getFormattedText());

		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
