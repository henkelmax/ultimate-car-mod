package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;

public class ContainerFluidExtractor extends ContainerBase{

	private IInventory playerInv;
	//private TileEntityFluidExtractor tile;
	
	public ContainerFluidExtractor(EntityPlayer player, TileEntityFluidExtractor tile) {
		//this.tile=tile;
		this.playerInv=player.inventory;
		
		addSlotToContainer(new SlotFluidFilter(new InventoryBasic("", false, 1), 0, 26, 25, tile, player));
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 57 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 115));
		}
		
	}
	@Override
	protected int getSizeInventory() {
		return 1;
	}

}
