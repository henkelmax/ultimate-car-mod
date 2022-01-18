package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;

public class ContainerSign extends ContainerBase {

    private TileEntitySign sign;

    public ContainerSign(int id, TileEntitySign sign, Inventory playerInv) {
        super(Main.SIGN_CONTAINER_TYPE, id, playerInv, new SimpleContainer(0));
        this.sign = sign;
    }

    public TileEntitySign getSign() {
        return sign;
    }

}
