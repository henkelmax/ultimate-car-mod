package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;

public class ContainerFuelStationAdmin extends ContainerBase {

    private TileEntityFuelStation fuelStation;

    public ContainerFuelStationAdmin(int id, TileEntityFuelStation fuelStation, PlayerInventory playerInv) {
        super(Main.FUEL_STATION_ADMIN_CONTAINER_TYPE, id, fuelStation, playerInv);
        this.fuelStation = fuelStation;
        addSlot(new Slot(fuelStation.getTradingInventory(), 0, 26, 22));

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new Slot(fuelStation.getInventory(), k + j * 9, 8 + k * 18, 49 + j * 18));
            }
        }

        addInvSlots();
    }

    public TileEntityFuelStation getFuelStation() {
        return fuelStation;
    }

    @Override
    public int getInvOffset() {
        return 31;
    }
}
