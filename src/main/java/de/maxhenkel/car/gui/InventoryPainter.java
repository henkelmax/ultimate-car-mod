package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class InventoryPainter implements IInventory{

	private boolean isYellow;
	
	public InventoryPainter(boolean isYellow) {
		this.isYellow=isYellow;
	}
	
	@Override
	public String getName() {
		return "gui.painter";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(getName());
	}

	@Override
	public int getSizeInventory() {
		return 18;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if(index>=0&&index<ModBlocks.PAINTS.length){
			if(isYellow){
				return new ItemStack(ModBlocks.YELLOW_PAINTS[index]);
			}else{
				return new ItemStack(ModBlocks.PAINTS[index]);
			}
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {
		
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		
	}

}
