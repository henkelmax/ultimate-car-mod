package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityBlastFurnace;
import net.minecraft.world.entity.player.Inventory;

public class ContainerBlastFurnace extends ContainerEnergyFluidProducer {

    public ContainerBlastFurnace(int id, TileEntityBlastFurnace tile, Inventory playerInv) {
        super(Main.BLAST_FURNACE_CONTAINER_TYPE, id, tile, playerInv);
    }
}
