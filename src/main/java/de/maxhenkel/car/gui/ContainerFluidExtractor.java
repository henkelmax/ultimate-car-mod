package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;

public class ContainerFluidExtractor extends ContainerBase{
	
	protected TileEntityFluidExtractor tile;
	protected EntityPlayer player;
	
	public ContainerFluidExtractor(TileEntityFluidExtractor tile, EntityPlayer player) {
		super(new InventoryBasic("", false, 1), player.inventory);
		this.tile=tile;
		this.player=player;
		
		addSlotToContainer(new SlotFluidFilter(tileInventory, 0, 26, 25, tile, player));
		
		addInvSlots();
	}
	
	@Override
	public int getInvOffset() {
		return -27;
	}

}
