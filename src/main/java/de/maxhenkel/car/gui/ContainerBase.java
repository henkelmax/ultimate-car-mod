package de.maxhenkel.car.gui;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerBase extends Container {

    protected IInventory tileInventory;
    protected PlayerInventory playerInventory;

    public ContainerBase(ContainerType type, int id, IInventory tileInventory, PlayerInventory playerInventory) {
        super(type, id);
        this.playerInventory = playerInventory;
        this.tileInventory = tileInventory;
    }

    protected void addInvSlots() {
        if (playerInventory != null) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                    addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + getInvOffset()));
                }
            }

            for (int k = 0; k < 9; k++) {
                addSlot(new Slot(playerInventory, k, 8 + k * 18, 142 + getInvOffset()));
            }
        }
    }

    public int getInvOffset() {
        return 0;
    }

    public int getInventorySize(){
        if(tileInventory==null){
            return 0;
        }
        return tileInventory.getSizeInventory();
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Nullable
    public IInventory getPlayerInventory() {
        return playerInventory;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < getInventorySize()) {
                if (!this.mergeItemStack(itemstack1, getInventorySize(), inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, getInventorySize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    //TODO fields
    /*
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
	}*/
}
