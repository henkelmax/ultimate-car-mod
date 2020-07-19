package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ContainerGenerator extends ContainerBase {

    private TileEntityGenerator generator;

    public ContainerGenerator(int id, TileEntityGenerator generator, PlayerInventory playerInv, IIntArray fields) {
        super(Main.GENERATOR_CONTAINER_TYPE, id, playerInv, generator);
        this.generator = generator;

        addPlayerInventorySlots();

        trackIntArray(fields);
    }

    public ContainerGenerator(int id, TileEntityGenerator generator, PlayerInventory playerInv) {
        this(id, generator, playerInv, new IntArray(2));
    }

    public TileEntityGenerator getGenerator() {
        return generator;
    }

}
