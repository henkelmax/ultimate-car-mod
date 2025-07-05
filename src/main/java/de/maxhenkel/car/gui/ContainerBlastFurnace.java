package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityBlastFurnace;
import net.minecraft.world.entity.player.Inventory;

public class ContainerBlastFurnace extends ContainerEnergyFluidProducer {

    public ContainerBlastFurnace(int id, TileEntityBlastFurnace tile, Inventory playerInv) {
        super(CarMod.BLAST_FURNACE_CONTAINER_TYPE.get(), id, tile, playerInv);
    }
}
