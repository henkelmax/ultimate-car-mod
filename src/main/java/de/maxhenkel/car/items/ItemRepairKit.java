package de.maxhenkel.car.items;

import java.util.List;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.entity.car.base.EntityCarDamageBase;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.tools.ItemTools;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemRepairKit extends Item {

	public ItemRepairKit() {
		setMaxStackSize(1);
		setUnlocalizedName("repair_kit");
		setRegistryName("repair_kit");
		setCreativeTab(ModCreativeTabs.TAB_CAR);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TextComponentTranslation("tooltip.repair_kit_1").getUnformattedText());
		tooltip.add(new TextComponentTranslation("tooltip.repair_kit_2").getUnformattedText());
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
