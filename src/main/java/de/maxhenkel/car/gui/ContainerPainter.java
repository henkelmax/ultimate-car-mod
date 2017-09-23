package de.maxhenkel.car.gui;

import net.minecraft.entity.player.EntityPlayer;

public class ContainerPainter extends ContainerBase{
	
	protected EntityPlayer player;
	
	public ContainerPainter(EntityPlayer player, boolean isYellow) {
		super(new InventoryPainter(isYellow), null);
		this.player=player;
		
		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 9; k++) {
				this.addSlotToContainer(new SlotPainter(player, tileInventory, k + j * 9, 8 + k * 18, 18 + j * 18));
			}
		}
	}

}
