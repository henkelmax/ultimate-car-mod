package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerFuelStation extends ContainerBase{

	public ContainerFuelStation(TileEntityFuelStation fuelStation, IInventory playerInv) {
		super(fuelStation, playerInv);

        addSlotToContainer(new SlotPresent(fuelStation.getTradingInventory().getStackInSlot(0),18, 74));
        addSlotToContainer(new Slot(fuelStation.getTradingInventory(), 1,38, 74));

		addInvSlots();
	}

    @Override
    public int getInvOffset() {
        return 26;
    }
}
