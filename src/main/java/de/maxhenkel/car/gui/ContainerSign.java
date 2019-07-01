package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import net.minecraft.inventory.Inventory;

public class ContainerSign extends ContainerBase {

    private TileEntitySign sign;

    public ContainerSign(int id, TileEntitySign sign) {
        super(Main.SIGN_CONTAINER_TYPE, id, new Inventory(0), null);
        this.sign = sign;
    }

    public TileEntitySign getSign() {
        return sign;
    }
}
