package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class ContainerLicensePlate extends ContainerBase {

    private ItemStack licensePlate;

    public ContainerLicensePlate(int id, ItemStack licensePlate) {
        super(Main.LICENSE_PLATE_CONTAINER_TYPE, id, new Inventory(0), null);
        this.licensePlate=licensePlate;
    }

    public ItemStack getLicensePlate() {
        return licensePlate;
    }
}
