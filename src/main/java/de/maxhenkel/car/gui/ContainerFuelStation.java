package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;

public class ContainerFuelStation extends ContainerBase {

    private TileEntityFuelStation fuelStation;

    public ContainerFuelStation(int id, TileEntityFuelStation fuelStation, PlayerInventory playerInv) {
        super(Main.FUEL_STATION_CONTAINER_TYPE, id, fuelStation, playerInv);
        this.fuelStation=fuelStation;

        addSlot(new SlotPresent(fuelStation.getTradingInventory().getStackInSlot(0), 18, 99));
        addSlot(new Slot(fuelStation.getTradingInventory(), 1, 38, 99));

        addInvSlots();
    }

    public TileEntityFuelStation getFuelStation() {
        return fuelStation;
    }

    @Override
    public int getInvOffset() {
        return 51;
    }
}
