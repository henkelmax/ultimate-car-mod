package de.maxhenkel.car.gui;

import de.maxhenkel.car.items.ItemPainter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SlotPainter extends Slot{

	private EntityPlayer player;
	private int index;
	
	public SlotPainter(EntityPlayer player, IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		this.player=player;
		this.index=index;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		setPainterID(index);
		if(player.world.isRemote){
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
		return false;
	}
	
	public void setPainterID(int index){
		ItemStack stack=player.getHeldItemMainhand();
		if(stack==null){
			return;
		}
		
		Item i=stack.getItem();
		
		if(!(i instanceof ItemPainter)){
			return;
		}
		
		if(!stack.hasTagCompound()){
			stack.setTagCompound(new NBTTagCompound());
		}
		
		if(stack.getTagCompound()==null){
			return;
		}
		
		NBTTagCompound compound=stack.getTagCompound();
		
		compound.setInteger("index", index);
	}
	
	public static int getPainterID(EntityPlayer player){
		ItemStack stack=getPainterStack(player);
		
		if(stack==null){
			return 0;
		}
		
		if(!stack.hasTagCompound()){
			return 0;
		}
		
		if(stack.getTagCompound()==null){
			return 0;
		}
		
		NBTTagCompound compound=stack.getTagCompound();
		
		if(!compound.hasKey("index")){
			return 0;
		}
		
		return compound.getInteger("index");
	}
	
	public static ItemStack getPainterStack(EntityPlayer player){
		ItemStack stack=player.getHeldItemMainhand();
		if(stack==null){
			return null;
		}
		
		Item i=stack.getItem();
		
		if(!(i instanceof ItemPainter)){
			return null;
		}
		
		return stack;
	}
	
}
