package de.maxhenkel.car.reciepe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface ICarCraftingInventory extends IInventory{

	public ItemStack getStackInRowAndColumn(int row, int column);
	
}
