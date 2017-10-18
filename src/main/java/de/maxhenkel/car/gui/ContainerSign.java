package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import net.minecraft.inventory.InventoryBasic;

public class ContainerSign extends ContainerBase{

	public ContainerSign(TileEntitySign sign) {
		super(new InventoryBasic("", false, 0), null);
	}

}
