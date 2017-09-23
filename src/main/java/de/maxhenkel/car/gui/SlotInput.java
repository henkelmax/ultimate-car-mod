package de.maxhenkel.car.gui;

import java.util.ArrayList;
import java.util.List;
import de.maxhenkel.tools.ItemTools;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotInput extends Slot{

	private List<ItemStack> insertItems;
	
	public SlotInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		this.insertItems=new ArrayList<ItemStack>();
	}
	
	public SlotInput(IInventory inventoryIn, int index, int xPosition, int yPosition, List<ItemStack> insertItems) {
		super(inventoryIn, index, xPosition, yPosition);
		this.insertItems=insertItems;
	}
	
	public SlotInput(IInventory inventoryIn, int index, int xPosition, int yPosition, ItemStack insertItem) {
		super(inventoryIn, index, xPosition, yPosition);
		this.insertItems=new ArrayList<ItemStack>();
		this.insertItems.add(insertItem);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		for(ItemStack i:insertItems){
			if(ItemTools.areItemsEqual(stack, i)){
				return true;
			}
		}
		
		return false;
	}
	
}
