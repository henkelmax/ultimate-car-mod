package de.maxhenkel.tools;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ItemTools {

	public static final ItemStack EMPTY=null;
	
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
	
	public static boolean areItemsEqual(ItemStack stack1, ItemStack stack2) {
		return ItemStack.areItemsEqual(stack1, stack2);
	}
	
	public static boolean contains(List<ItemStack> list, ItemStack item){
		for(ItemStack i:list){
			if(ItemTools.areItemsEqual(item, i)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Changes the Itemstack amount. If a player is provided and the player is in Creative Mode, the stack wont be changed
	 * @param amount The amount to change
	 * @param stack The Item Stack
	 * @param player The player. Can be null
	 * @return The Itemstack with the changed amount
	 */
	public static ItemStack itemStackAmount(int amount, ItemStack stack, EntityPlayer player) {
		if(stack==null){
			return null;
		}
		
		if (player!=null&&player.capabilities.isCreativeMode) {
			return stack;
		}

		stack.stackSize += amount;
		if (stack.stackSize <= 0) {
			stack.stackSize = 0;
			return null;
		}
		
		if(stack.stackSize>stack.getMaxStackSize()){
			stack.stackSize=stack.getMaxStackSize();
		}

		return stack;
	}

	public static ItemStack decrItemStack(ItemStack stack, EntityPlayer player) {
		return itemStackAmount(-1, stack, player);
	}
	
	public static ItemStack incrItemStack(ItemStack stack, EntityPlayer player) {
		return itemStackAmount(1, stack, player);
	}
	
	public static ItemStack damageStack(ItemStack stack, int amount, EntityLivingBase entity){
		stack.damageItem(amount, entity);
		return stack;
	}
	
	public static void removeStackFromSlot(IInventory inventory, int index){
		inventory.setInventorySlotContents(index, null);
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
