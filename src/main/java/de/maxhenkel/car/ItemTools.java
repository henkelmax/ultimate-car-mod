package de.maxhenkel.car;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemTools {

	public static boolean isStackEmpty(ItemStack stack){
		if(stack==null){
			return true;
		}
		
		if(stack.getItem().equals(Blocks.AIR)){
			return true;
		}
		
		if(stack.stackSize<=0){
			return true;
		}
		
		return false;	
	}
	
	public static boolean areItemsEqualWithEmpty(ItemStack stack1, ItemStack stack2){
		if(isStackEmpty(stack1)&&isStackEmpty(stack2)){
			return true;
		}
		
		return ItemStack.areItemsEqual(stack1, stack2);
	}
	
	public static ItemStack decrItemStack(int amount, ItemStack stack, EntityPlayer player){
		if(player.capabilities.isCreativeMode){
			return stack;
		}
		
		stack.stackSize-=amount;
		if(amount<=0){
			amount=0;
			return null;
		}
		
		return stack;
	}
	
	public static ItemStack decrItemStack(ItemStack stack, EntityPlayer player){
		return decrItemStack(1, stack, player);
	}
	
}
