package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;

public class ContainerFluidExtractor extends ContainerBase {

    protected TileEntityFluidExtractor tile;
    protected PlayerInventory playerInventory;

    public ContainerFluidExtractor(int id, TileEntityFluidExtractor tile, PlayerInventory playerInventory) {
        super(null, id, new Inventory(1), playerInventory);
        this.tile = tile;
        this.playerInventory = playerInventory;

        addSlot(new SlotFluidFilter(tileInventory, 0, 26, 25, tile, playerInventory.player));

        addInvSlots();
    }

    @Override
    public int getInvOffset() {
        return -27;
    }

}
