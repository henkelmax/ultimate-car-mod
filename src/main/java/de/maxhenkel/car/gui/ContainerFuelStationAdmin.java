package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerFuelStationAdmin extends ContainerBase{

	public ContainerFuelStationAdmin(TileEntityFuelStation fuelStation, IInventory playerInv) {
		super(fuelStation, playerInv);

        addSlotToContainer(new Slot(fuelStation.getTradingInventory(), 0,26, 7));

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                this.addSlotToContainer(new Slot(fuelStation.getInventory(), k + j * 9, 8 + k * 18, 34 + j * 18));
            }
        }

		addInvSlots();
	}

    @Override
    public int getInvOffset() {
        return 16;
    }
}
