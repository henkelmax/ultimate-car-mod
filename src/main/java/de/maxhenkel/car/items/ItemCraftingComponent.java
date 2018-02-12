package de.maxhenkel.car.items;

import de.maxhenkel.car.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCraftingComponent extends Item{

	public ItemCraftingComponent(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTabs.TAB_CAR);
	}

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if(this==ModItems.CONTAINER){
            tooltip.add(new TextComponentTranslation("tooltip.container").getFormattedText());
        }
    }
	
}
