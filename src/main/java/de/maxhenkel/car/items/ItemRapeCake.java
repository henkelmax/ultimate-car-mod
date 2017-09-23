package de.maxhenkel.car.items;

import de.maxhenkel.tools.ItemTools;
import de.maxhenkel.car.ModCreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ItemRapeCake extends Item {

	public ItemRapeCake() {
		this.setUnlocalizedName("rapecake");
		this.setRegistryName("rapecake");
		this.setCreativeTab(ModCreativeTabs.TAB_CAR);

	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target,
			EnumHand hand) {

		if (!(target instanceof EntityAnimal)) {
			return super.itemInteractionForEntity(stack, playerIn, target, hand);
		}

		EntityAnimal animal = (EntityAnimal) target;

		if (!animal.isBreedingItem(new ItemStack(Items.WHEAT))) {
			return super.itemInteractionForEntity(stack, playerIn, target, hand);
		}

		if (stack == null) {
			return super.itemInteractionForEntity(stack, playerIn, target, hand);
		}

		if (animal.getGrowingAge() == 0 && !animal.isInLove()) {
			ItemTools.decrItemStack(stack, playerIn);
			animal.setInLove(playerIn);
			return true;
		}

		if (animal.isChild()) {
			ItemTools.decrItemStack(stack, playerIn);
			animal.ageUp((int) ((float) (-animal.getGrowingAge() / 20) * 0.1F), true);
			return true;
		}

		return super.itemInteractionForEntity(stack, playerIn, target, hand);
	}

}
