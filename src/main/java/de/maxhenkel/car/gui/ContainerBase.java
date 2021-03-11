package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;

public abstract class ContainerBase extends de.maxhenkel.corelib.inventory.ContainerBase {

    public ContainerBase(ContainerType type, int id, PlayerInventory playerInventory, IInventory tileInventory) {
        super(type, id, playerInventory, tileInventory);
        if (tileInventory instanceof TileEntityBase) {
            addDataSlots(((TileEntityBase) tileInventory).getFields());
        }
    }

}
