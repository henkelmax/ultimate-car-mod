package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerFuelStation extends ContainerBase{

	public ContainerFuelStation(TileEntityFuelStation fuelStation, IInventory playerInv) {
		super(fuelStation, playerInv);

        addSlotToContainer(new SlotPresent(fuelStation.getTradingInventory().getStackInSlot(0),18, 99));
        addSlotToContainer(new Slot(fuelStation.getTradingInventory(), 1,38, 99));

		addInvSlots();
	}

    @Override
    public int getInvOffset() {
        return 51;
    }
}
