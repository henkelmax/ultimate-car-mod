package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ContainerGenerator extends ContainerBase {

    public ContainerGenerator(int id, IInventory tileInv, PlayerInventory playerInv, IIntArray fields) {
        super(Main.GENERATOR_CONTAINER_TYPE, id, tileInv, playerInv);

        addInvSlots();

        func_216961_a(fields);
    }

    public ContainerGenerator(int id, PlayerInventory playerInv){
        this(id, new Inventory(0), playerInv, new IntArray(2));
    }

}
