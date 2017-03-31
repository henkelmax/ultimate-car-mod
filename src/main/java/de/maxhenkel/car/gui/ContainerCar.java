package de.maxhenkel.car.gui;

import de.maxhenkel.car.entity.car.base.EntityCarInventoryBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerCar extends ContainerBase {

	private EntityCarInventoryBase car;
	
	
	public ContainerCar(IInventory playerInv, EntityCarInventoryBase car) {
		this.car=car;
		
		int numRows = car.getSizeInventory() / 9;

		for (int j = 0; j < numRows; j++) {
			for (int k = 0; k < 9; k++) {
				this.addSlotToContainer(new Slot(car, k + j * 9, 8 + k * 18, 72 + j * 18));
			}
		}
		
		addSlotToContainer(new SlotFuel(car, 0, 116, 40));

		int i = 37;

		for (int l = 0; l < 3; l++) {
			for (int j1 = 0; j1 < 9; j1++) {
				this.addSlotToContainer(new Slot(playerInv, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
			}
		}

		for (int i1 = 0; i1 < 9; i1++) {
			this.addSlotToContainer(new Slot(playerInv, i1, 8 + i1 * 18, 161 + i));
		}
	}

	@Override
	protected int getSizeInventory() {
		return car.getSizeInventory();
	}

}
