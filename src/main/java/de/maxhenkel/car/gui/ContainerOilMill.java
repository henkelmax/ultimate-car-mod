package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityEnergyFluidProducer;
import net.minecraft.inventory.IInventory;

public class ContainerOilMill extends ContainerEnergyFluidProducer {

	public ContainerOilMill(IInventory playerInv, TileEntityEnergyFluidProducer tile) {
		super(playerInv, tile);
		
	}

}
