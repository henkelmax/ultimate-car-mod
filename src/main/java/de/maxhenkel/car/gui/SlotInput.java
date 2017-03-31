package de.maxhenkel.car.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotInput extends Slot{

	private List<Item> insertItems;
	
	public SlotInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		this.insertItems=new ArrayList<Item>();
	}
	
	public SlotInput(IInventory inventoryIn, int index, int xPosition, int yPosition, List<Item> insertItems) {
		super(inventoryIn, index, xPosition, yPosition);
		this.insertItems=insertItems;
	}
	
	public SlotInput(IInventory inventoryIn, int index, int xPosition, int yPosition, Item insertItem) {
		super(inventoryIn, index, xPosition, yPosition);
		this.insertItems=new ArrayList<Item>();
		this.insertItems.add(insertItem);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		for(Item i:insertItems){
			if(stack.getItem().equals(i)){
				return true;
			}
		}
		
		return false;
	}
	
}
