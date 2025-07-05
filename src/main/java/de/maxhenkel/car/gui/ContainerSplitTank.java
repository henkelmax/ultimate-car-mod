package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntitySplitTank;
import net.minecraft.world.entity.player.Inventory;

public class ContainerSplitTank extends ContainerBase {

    private TileEntitySplitTank splitTank;

    public ContainerSplitTank(int id, TileEntitySplitTank splitTank, Inventory playerInv) {
        super(CarMod.SPLIT_TANK_CONTAINER_TYPE.get(), id, playerInv, splitTank);
        this.splitTank = splitTank;
        addPlayerInventorySlots();
    }


    public TileEntitySplitTank getSplitTank() {
        return splitTank;
    }

}
