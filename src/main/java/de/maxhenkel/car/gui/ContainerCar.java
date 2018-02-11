package de.maxhenkel.car.gui;

import de.maxhenkel.car.entity.car.base.EntityCarInventoryBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerCar extends ContainerBase {

	protected EntityCarInventoryBase car;
	
	public ContainerCar(EntityCarInventoryBase car, IInventory playerInv) {
		super(car, playerInv);
		this.car=car;
		
		int numRows = car.getSizeInventory() / 9;
		
		for (int j = 0; j < numRows; j++) {
			for (int k = 0; k < 9; k++) {
				this.addSlotToContainer(new Slot(car, k + j * 9, 8 + k * 18, 72 + j * 18));
			}
		}
		
		addSlotToContainer(new SlotFuel(car, 0, 98, 40));

		addSlotToContainer(new SlotBattery(car, 0, 134, 40));

		addInvSlots();
	}
	
	@Override
	public int getInvOffset() {
		return 56;
	}

}
