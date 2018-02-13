package de.maxhenkel.car.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SlotPresent extends Slot{

	private ItemStack stack;

	public SlotPresent(ItemStack s, int xPosition, int yPosition) {
		super(null, 0, xPosition, yPosition);
		this.stack=s.copy();
		if(!stack.hasTagCompound()){
		    stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound compound=stack.getTagCompound();

		compound.setBoolean("trading_item", true);
	}

    @Override
    public ItemStack getStack() {
        return stack;
    }

    @Override
    public void putStack(ItemStack stack) {

    }

    @Override
    public void onSlotChanged() {

    }

    @Override
    public int getSlotStackLimit() {
        return 0;//TODO maybe fix
    }

    @Override
    public ItemStack decrStackSize(int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isHere(IInventory inv, int slotIn) {
        return false;
    }

    @Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {
        return false;
    }
}
