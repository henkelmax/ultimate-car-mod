package de.maxhenkel.car.items;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.entity.car.base.EntityCarDamageBase;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.tools.ItemTools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;

public class ItemRepairKit extends Item {

	private final float repairAmount;

	public ItemRepairKit() {
		setMaxStackSize(1);
		setUnlocalizedName("repair_kit");
		setRegistryName("repair_kit");
		setCreativeTab(ModCreativeTabs.TAB_CAR);
		repairAmount = Config.repairKitRepairAmount;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {

		if (entity instanceof EntityCarDamageBase) {
			EntityCarDamageBase car = (EntityCarDamageBase) entity;
			if (car.getDamage() >= 90) {

				player.setHeldItem(EnumHand.MAIN_HAND, ItemTools.decrItemStack(stack, player));

				float damage = car.getDamage() - repairAmount;
				if (damage >= 0) {
					car.setDamage(damage);
				}
				ModSounds.playSound(SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, player.world, entity.getPosition(), null,
						SoundCategory.NEUTRAL);
				return true;
			} else {
				return true;
			}
		}

		return super.onLeftClickEntity(stack, player, entity);
	}

}
