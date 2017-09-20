package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityBackmixReactor;
import net.minecraft.inventory.IInventory;

public class ContainerBackmixReactor extends ContainerBase{
	
	public ContainerBackmixReactor(TileEntityBackmixReactor tileInv, IInventory playerInv) {
		super(tileInv, playerInv);
	}

}
