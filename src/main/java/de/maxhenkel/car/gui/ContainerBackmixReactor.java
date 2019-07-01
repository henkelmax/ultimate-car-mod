package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityBackmixReactor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;

public class ContainerBackmixReactor extends ContainerBase{
	
	public ContainerBackmixReactor(int id, TileEntityBackmixReactor tileInv, PlayerInventory playerInv) {
		super(null, id, tileInv, playerInv);
		addInvSlots();
	}
}
