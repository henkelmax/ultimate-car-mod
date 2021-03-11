package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarInventoryBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;

public class ContainerCarInventory extends ContainerBase {

    private EntityCarInventoryBase car;

    public ContainerCarInventory(int id, EntityCarInventoryBase car, PlayerInventory playerInventory) {
        super(Main.CAR_INVENTORY_CONTAINER_TYPE, id, playerInventory, car.getExternalInventory());
        this.car = car;

        int rows = getRows();

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < 9; y++) {
                addSlot(new Slot(car.getExternalInventory(), y + x * 9, 8 + y * 18, 18 + x * 18));
            }
        }

        addPlayerInventorySlots();
    }

    public int getRows() {
        return car.getExternalInventory().getContainerSize() / 9;
    }

    @Override
    public int getInvOffset() {
        return getRows() != 3 ? 56 : 0;
    }

    public EntityCarInventoryBase getCar() {
        return car;
    }

}
