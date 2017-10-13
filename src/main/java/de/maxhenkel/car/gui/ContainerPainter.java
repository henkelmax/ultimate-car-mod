package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.BlockPaint;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerPainter extends ContainerBase{
	
	protected EntityPlayer player;
	
	public ContainerPainter(EntityPlayer player, boolean isYellow) {
		super(new InventoryPainter(isYellow), null);
		this.player=player;
		
		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < 9; k++) {
				int index=k + j * 9;
				if(index<BlockPaint.EnumPaintType.values().length) {
					this.addSlotToContainer(new SlotPainter(player, tileInventory, index, 8 + k * 18, 18 + j * 18));
				}
			}
		}
	}

}
