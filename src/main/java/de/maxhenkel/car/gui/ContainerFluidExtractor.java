package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;

public class ContainerFluidExtractor extends ContainerBase {

    protected TileEntityFluidExtractor tile;
    protected PlayerInventory playerInventory;

    public ContainerFluidExtractor(int id, TileEntityFluidExtractor tile, PlayerInventory playerInventory) {
        super(Main.FLUID_EXTRACTOR_CONTAINER_TYPE, id, playerInventory, new Inventory(1));
        this.tile = tile;
        this.playerInventory = playerInventory;

        addSlot(new SlotFluidFilter(inventory, 0, 26, 25, tile));

        addPlayerInventorySlots();
    }

    public TileEntityFluidExtractor getTile() {
        return tile;
    }

    @Override
    public int getInvOffset() {
        return -27;
    }

}
