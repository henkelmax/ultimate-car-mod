package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityEnergyFluidProducer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotInputEnergyFluidProducer extends Slot{

	private TileEntityEnergyFluidProducer tile;
	
	public SlotInputEnergyFluidProducer(IInventory inventoryIn, int index, int xPosition, int yPosition, TileEntityEnergyFluidProducer tile) {
		super(inventoryIn, index, xPosition, yPosition);
		this.tile=tile;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return tile.isValidItem(stack);
	}
	
}
