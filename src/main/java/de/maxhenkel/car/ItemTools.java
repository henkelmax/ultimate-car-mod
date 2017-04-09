package de.maxhenkel.car;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ItemTools {

	public static boolean isStackEmpty(ItemStack stack) {
		if (stack == null) {
			return true;
		}

		if (stack.getItem().equals(Blocks.AIR)) {
			return true;
		}

		if (stack.stackSize <= 0) {
			return true;
		}

		return false;
	}

	public static boolean areItemsEqualWithEmpty(ItemStack stack1, ItemStack stack2) {
		if (isStackEmpty(stack1) && isStackEmpty(stack2)) {
			return true;
		}

		return ItemStack.areItemsEqual(stack1, stack2);
	}

	public static ItemStack decrItemStack(int amount, ItemStack stack, EntityPlayer player) {
		if (player!=null&&player.capabilities.isCreativeMode) {
			return stack;
		}

		stack.stackSize -= amount;
		if (stack.stackSize <= 0) {
			stack.stackSize = 0;
			return null;
		}

		return stack;
	}

	public static ItemStack decrItemStack(ItemStack stack, EntityPlayer player) {
		return decrItemStack(1, stack, player);
	}

	public static void saveInventory(NBTTagCompound compound, String name, IInventory inv) {
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inv.getSizeInventory(); i++) {
			if (inv.getStackInSlot(i) != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setInteger("Slot", i);
				inv.getStackInSlot(i).writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		compound.setTag(name, nbttaglist);
	}

	public static void readInventory(NBTTagCompound compound, String name, IInventory inv) {
		if(!compound.hasKey(name)){
			return;
		}
		
		NBTTagList nbttaglist = compound.getTagList(name, 10);

		if(nbttaglist==null){
			return;
		}
		
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getInteger("Slot");

			if (j >= 0 && j < inv.getSizeInventory()) {
				inv.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound));
			}
		}
	}

}
