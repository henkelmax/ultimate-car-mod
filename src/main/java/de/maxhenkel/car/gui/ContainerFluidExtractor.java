package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;

public class ContainerFluidExtractor extends ContainerBase{
	
	public ContainerFluidExtractor(TileEntityFluidExtractor tile, EntityPlayer player) {
		super(new InventoryBasic("", false, 1), player.inventory);
		
		addSlotToContainer(new SlotFluidFilter(tileInventory, 0, 26, 25, tile, player));
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 57 + i * 18));
			}
		}

		for (int k = 0; k < 9; k++) {
			this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 115));
		}
	}
	
	@Override
	public boolean hasCustomInvPos() {
		return true;
	}

}
