package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class ContainerLicensePlate extends ContainerBase {

    private ItemStack licensePlate;

    public ContainerLicensePlate(int id, ItemStack licensePlate) {
        super(Main.LICENSE_PLATE_CONTAINER_TYPE, id, null, new SimpleContainer(0));
        this.licensePlate = licensePlate;
    }

    public ItemStack getLicensePlate() {
        return licensePlate;
    }

}
