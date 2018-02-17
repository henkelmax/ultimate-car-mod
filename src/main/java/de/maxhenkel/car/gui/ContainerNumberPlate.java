package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import net.minecraft.inventory.InventoryBasic;

public class ContainerNumberPlate extends ContainerBase{

	public ContainerNumberPlate() {
		super(new InventoryBasic("", false, 0), null);
	}

}
