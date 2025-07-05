package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.entity.car.base.EntityCarInventoryBase;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class ContainerCar extends ContainerBase {

    protected EntityCarInventoryBase car;

    public ContainerCar(int id, EntityCarInventoryBase car, Inventory playerInv) {
        super(CarMod.CAR_CONTAINER_TYPE.get(), id, playerInv, car);
        this.car = car;

        int numRows = car.getContainerSize() / 9;

        for (int j = 0; j < numRows; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new Slot(car, k + j * 9, 8 + k * 18, 98 + j * 18));
            }
        }

        addSlot(new SlotFuel(car, 0, 98, 66, playerInv.player));

        addSlot(new SlotBattery(car, 0, 116, 66, playerInv.player));

        addSlot(new SlotRepairKit(car, 0, 134, 66, playerInv.player));

        addPlayerInventorySlots();
    }

    public EntityCarInventoryBase getCar() {
        return car;
    }

    @Override
    public int getInvOffset() {
        return 82;
    }

}
