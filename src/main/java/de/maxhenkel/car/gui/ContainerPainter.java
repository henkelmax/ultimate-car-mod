package de.maxhenkel.car.gui;

import net.minecraft.entity.player.EntityPlayer;

public class ContainerPainter extends ContainerBase{

	private InventoryPainter inv;
	private EntityPlayer player;
	
	public ContainerPainter(EntityPlayer player, boolean isYellow) {
		this.player=player;
		this.inv=new InventoryPainter(isYellow);
		
		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 9; k++) {
				this.addSlotToContainer(new SlotPainter(player, inv, k + j * 9, 8 + k * 18, 18 + j * 18));
			}
		}
	}
	
	@Override
	protected int getSizeInventory() {
		return 0;
	}

}
