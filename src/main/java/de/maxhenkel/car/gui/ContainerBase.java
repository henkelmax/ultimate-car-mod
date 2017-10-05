package de.maxhenkel.car.gui;

import javax.annotation.Nullable;
import de.maxhenkel.tools.ItemTools;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerBase extends Container {

	protected IInventory tileInventory;
	protected IInventory playerInventory;

	public ContainerBase(IInventory tileInventory, IInventory playerInventory) {
		this.tileInventory = tileInventory;
		this.playerInventory = playerInventory;
	}
	
	protected void addInvSlots(){
		if(playerInventory!=null){
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 9; j++) {
					this.addSlotToContainer(
							new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + getInvOffset()));
				}
			}

			for (int k = 0; k < 9; k++) {
				this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142 + getInvOffset()));
			}
		}
	}

	public int getInvOffset() {
		return 0;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	@Nullable
	public IInventory getPlayerInventory() {
		return playerInventory;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {

		if (!supportsShiftClick(player, slotIndex)) {
			return ItemTools.EMPTY;
		}

		ItemStack stack = ItemTools.EMPTY;
		Slot slot = inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			stack = stackInSlot.copy();

			if (!performMerge(player, slotIndex, stackInSlot)) {
				return ItemTools.EMPTY;
			}

			slot.onSlotChange(stackInSlot, stack);

			if (stackInSlot.getCount() <= 0) {
				slot.putStack(ItemTools.EMPTY);
			} else {
				slot.putStack(stackInSlot);
			}

			if (stackInSlot.getCount() == stack.getCount()) {
				return ItemTools.EMPTY;
			}
			slot.onTake(player, stackInSlot);
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

	protected int getSizeInventory() {
		if(tileInventory==null){
			return 0;
		}
		return tileInventory.getSizeInventory();
	}

	//
	private int[] fields = new int[0];

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		int fieldCount = tileInventory.getFieldCount();

		for (int i = 0; i < this.listeners.size(); i++) {
			IContainerListener icontainerlistener = listeners.get(i);

			for (int field = 0; field < fieldCount; field++) {
				if (field < fields.length && fields[field] != tileInventory.getField(field)) {
					icontainerlistener.sendWindowProperty(this, field, tileInventory.getField(field));
				}
			}
		}

		fields = new int[fieldCount];

		for (int field = 0; field < fieldCount; field++) {
			fields[field] = tileInventory.getField(field);
		}
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		for (int field = 0; field < fields.length; field++) {
			listener.sendWindowProperty(this, field, fields[field]);
		}
	}

	@Override
	public void updateProgressBar(int id, int data) {
		tileInventory.setField(id, data);
	}

	public int getField(int i) {
		if (i >= fields.length) {
			return 0;
		}
		return fields[i];
	}
}
