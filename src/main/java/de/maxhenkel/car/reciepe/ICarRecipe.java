package de.maxhenkel.car.reciepe;

import de.maxhenkel.car.registries.ICar;
import net.minecraft.item.ItemStack;

public interface ICarRecipe{

	public ItemStack[] getInputs();
	
	public boolean matches(ICarCraftingInventory inv);
	
	public ICar getCraftingResult();
	
}
