package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityBase;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public abstract class ContainerBase extends de.maxhenkel.corelib.inventory.ContainerBase {

    public ContainerBase(MenuType type, int id, Inventory playerInventory, Container tileInventory) {
        super(type, id, playerInventory, tileInventory);
        if (tileInventory instanceof TileEntityBase) {
            addDataSlots(((TileEntityBase) tileInventory).getFields());
        }
    }

}
