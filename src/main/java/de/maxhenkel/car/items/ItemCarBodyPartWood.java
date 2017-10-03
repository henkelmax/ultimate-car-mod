package de.maxhenkel.car.items;

import de.maxhenkel.car.ModCreativeTabs;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCarBodyPartWood extends Item {

	public ItemCarBodyPartWood() {
		setUnlocalizedName("car_body_part_wood");
		setRegistryName("car_body_part_wood");
		setCreativeTab(ModCreativeTabs.TAB_CAR);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getMetadata();
		return super.getUnlocalizedName() + "." + EnumType.byMetadata(i).getUnlocalizedName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		for (int i = 0; i < EnumType.values().length; i++) {
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}

}
