package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityEnergyFluidProducer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;

public abstract class ContainerEnergyFluidProducer extends ContainerBase {

    protected TileEntityEnergyFluidProducer tile;

    public ContainerEnergyFluidProducer(ContainerType containerType, int id, TileEntityEnergyFluidProducer tile, PlayerInventory playerInv) {
        super(containerType, id, tile, playerInv);
        this.tile = tile;

        this.addSlot(new SlotInputEnergyFluidProducer(tile, 0, 56, 34, tile));
        this.addSlot(new SlotResult(tile, 1, 116, 35));

        addInvSlots();
    }

    public TileEntityEnergyFluidProducer getTile() {
        return tile;
    }

}
