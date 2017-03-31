package de.maxhenkel.car.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerBase extends Container{

	public ContainerBase() {
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {

		if (!supportsShiftClick(player, slotIndex)) {
			return null;
		}

		ItemStack stack = null;
		Slot slot = inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			stack = stackInSlot.copy();

			if (!performMerge(player, slotIndex, stackInSlot)) {
				return null;
			}

			slot.onSlotChange(stackInSlot, stack);

			if (stackInSlot.stackSize <= 0) {
				slot.putStack(null);
			} else {
				slot.putStack(stackInSlot);
			}

			if (stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}
	
	protected boolean supportsShiftClick(int slotIndex) {

		return true;
	}
	
	protected boolean supportsShiftClick(EntityPlayer player, int slotIndex) {

		return supportsShiftClick(slotIndex);
	}
	
	protected boolean performMerge(EntityPlayer player, int slotIndex, ItemStack stack) {

		return performMerge(slotIndex, stack);
	}

	protected boolean performMerge(int slotIndex, ItemStack stack) {

		int invBase = getSizeInventory();
		int invFull = inventorySlots.size();

		if (slotIndex < invBase) {
			return mergeItemStack(stack, invBase, invFull, true);
		}
		return mergeItemStack(stack, 0, invBase, false);
	}
	
	protected abstract int getSizeInventory();
	
}
