package de.maxhenkel.car.reciepe;

import net.minecraft.item.ItemStack;

public interface ICarRecipe {

	public String getName();
	
	public ItemStack[] getInputs();
	
	public boolean matches(ICarCraftingInventory inv);
	
	public ICarbuilder getCraftingResult();
	
}
