package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntitySplitTank;
import net.minecraft.entity.player.PlayerInventory;

public class ContainerSplitTank extends ContainerBase {

    private TileEntitySplitTank splitTank;

    public ContainerSplitTank(int id, TileEntitySplitTank splitTank, PlayerInventory playerInv) {
        super(null, id, splitTank, playerInv);
        this.splitTank = splitTank;
        addInvSlots();
    }


    public TileEntitySplitTank getSplitTank() {
        return splitTank;
    }
}
