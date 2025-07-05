package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;

public class ContainerSign extends ContainerBase {

    private TileEntitySign sign;

    public ContainerSign(int id, TileEntitySign sign, Inventory playerInv) {
        super(CarMod.SIGN_CONTAINER_TYPE.get(), id, playerInv, new SimpleContainer(0));
        this.sign = sign;
    }

    public TileEntitySign getSign() {
        return sign;
    }

}
