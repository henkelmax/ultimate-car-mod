package de.maxhenkel.car.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotOneItem extends Slot{

	private Item item;
	
	public SlotOneItem(IInventory inventoryIn, int index, int xPosition, int yPosition, Item item) {
		super(inventoryIn, index, xPosition, yPosition);
		this.item=item;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(stack.getItem().equals(item)){
			return true;
		}else{
			return false;
		}
	}
	
}
