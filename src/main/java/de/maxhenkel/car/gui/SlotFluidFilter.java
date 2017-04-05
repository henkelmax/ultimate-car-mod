package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class SlotFluidFilter extends Slot{
	
	private TileEntityFluidExtractor tile;
	//private IInventory inv;
	private int index;
	//private EntityPlayer player;
	
	public SlotFluidFilter(IInventory inv, int index, int xPosition, int yPosition, TileEntityFluidExtractor tile, EntityPlayer player) {
		super(inv, index, xPosition, yPosition);
		this.tile=tile;
	//	this.inv=inv;
		this.index=index;
	//	this.player=player;
		
		setFluidContained(tile.getFilter());
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer playerIn) {
		tile.setFilter(null);
		inventory.setInventorySlotContents(index, null);
		onSlotChanged();
		return false;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		setFluidContained(stack);
		return false;
	}

	private void setFluidContained(ItemStack stack){
		if(stack==null){
			inventory.setInventorySlotContents(index, null);
		    onSlotChanged();
			return;
		}
		
		FluidStack fluidStack=FluidUtil.getFluidContained(stack);
		
		if(fluidStack==null||fluidStack.amount<=0){
			inventory.setInventorySlotContents(index, null);
		    onSlotChanged();
			return;
		}
		
		tile.setFilter(stack);
		
		Block block=fluidStack.getFluid().getBlock();
		
		Item i=Item.getItemFromBlock(block);
		
		if(i==null){
			inventory.setInventorySlotContents(index, stack.copy());
		    onSlotChanged();
		}else{
			inventory.setInventorySlotContents(index, new ItemStack(i));
		    onSlotChanged();
		}
	}

}
