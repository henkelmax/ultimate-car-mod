package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerGenerator extends ContainerBase {

    private TileEntityGenerator generator;

    public ContainerGenerator(int id, TileEntityGenerator generator, Inventory playerInv, ContainerData fields) {
        super(CarMod.GENERATOR_CONTAINER_TYPE.get(), id, playerInv, generator);
        this.generator = generator;

        addPlayerInventorySlots();

        addDataSlots(fields);
    }

    public ContainerGenerator(int id, TileEntityGenerator generator, Inventory playerInv) {
        this(id, generator, playerInv, new SimpleContainerData(2));
    }

    public TileEntityGenerator getGenerator() {
        return generator;
    }

}
