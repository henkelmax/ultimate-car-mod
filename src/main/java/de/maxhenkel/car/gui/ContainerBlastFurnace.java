package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityEnergyFluidProducer;
import net.minecraft.inventory.IInventory;

public class ContainerBlastFurnace extends ContainerEnergyFluidProducer{

	public ContainerBlastFurnace(IInventory playerInv, TileEntityEnergyFluidProducer tile) {
		super(playerInv, tile);
		
	}

}
