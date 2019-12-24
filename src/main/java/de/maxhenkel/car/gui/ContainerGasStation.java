package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;

public class ContainerGasStation extends ContainerBase {

    private TileEntityGasStation gasStation;

    public ContainerGasStation(int id, TileEntityGasStation gasStation, PlayerInventory playerInv) {
        super(Main.FUEL_STATION_CONTAINER_TYPE, id, gasStation, playerInv);
        this.gasStation = gasStation;

        addSlot(new SlotPresent(gasStation.getTradingInventory().getStackInSlot(0), 18, 99));
        addSlot(new Slot(gasStation.getTradingInventory(), 1, 38, 99));

        addInvSlots();
    }

    public TileEntityGasStation getGasStation() {
        return gasStation;
    }

    @Override
    public int getInvOffset() {
        return 51;
    }
}
