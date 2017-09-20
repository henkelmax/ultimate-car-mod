package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityEnergyFluidProducer;
import net.minecraft.inventory.IInventory;

public abstract class ContainerEnergyFluidProducer extends ContainerBase {

	protected TileEntityEnergyFluidProducer tile;
	
	public ContainerEnergyFluidProducer(TileEntityEnergyFluidProducer tile, IInventory playerInv) {
		super(tile, playerInv);
		this.tile=tile;

		this.addSlotToContainer(new SlotInput(tile, 0, 56, 34, tile.getInputItems()));
		this.addSlotToContainer(new SlotResult(tile, 1, 116, 35));
	}
	
	public TileEntityEnergyFluidProducer getTile(){
		return tile;
	}

}
