package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;

public class ContainerFluidExtractor extends ContainerBase {

    protected TileEntityFluidExtractor tile;
    protected Inventory playerInventory;

    public ContainerFluidExtractor(int id, TileEntityFluidExtractor tile, Inventory playerInventory) {
        super(Main.FLUID_EXTRACTOR_CONTAINER_TYPE, id, playerInventory, new SimpleContainer(1));
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
