package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class ContainerGasStationAdmin extends ContainerBase {

    private TileEntityGasStation gasStation;

    public ContainerGasStationAdmin(int id, TileEntityGasStation gasStation, Inventory playerInv) {
        super(Main.GAS_STATION_ADMIN_CONTAINER_TYPE, id, playerInv, null);
        this.gasStation = gasStation;
        addSlot(new Slot(gasStation.getTradingInventory(), 0, 26, 22));

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new Slot(gasStation.getInventory(), k + j * 9, 8 + k * 18, 49 + j * 18));
            }
        }

        addPlayerInventorySlots();
    }

    public TileEntityGasStation getGasStation() {
        return gasStation;
    }

    @Override
    public int getInvOffset() {
        return 31;
    }

}
