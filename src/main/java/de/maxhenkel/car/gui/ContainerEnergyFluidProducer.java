package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityEnergyFluidProducer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

public abstract class ContainerEnergyFluidProducer extends ContainerBase {

    private TileEntityEnergyFluidProducer tile;

    public ContainerEnergyFluidProducer(MenuType containerType, int id, TileEntityEnergyFluidProducer tile, Inventory playerInv) {
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
