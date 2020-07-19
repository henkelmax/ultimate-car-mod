package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityEnergyFluidProducer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;

public abstract class ContainerEnergyFluidProducer extends ContainerBase {

    private TileEntityEnergyFluidProducer tile;

    public ContainerEnergyFluidProducer(ContainerType containerType, int id, TileEntityEnergyFluidProducer tile, PlayerInventory playerInv) {
        super(containerType, id, playerInv, tile);
        this.tile = tile;

        this.addSlot(new Slot(inventory, 0, 56, 34));
        this.addSlot(new SlotResult(inventory, 1, 116, 35));

        addPlayerInventorySlots();
    }

    public TileEntityEnergyFluidProducer getTile() {
        return tile;
    }

}
