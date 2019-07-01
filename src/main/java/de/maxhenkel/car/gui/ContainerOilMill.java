package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityEnergyFluidProducer;
import net.minecraft.entity.player.PlayerInventory;

public class ContainerOilMill extends ContainerEnergyFluidProducer {

    public ContainerOilMill(int id, TileEntityEnergyFluidProducer tile, PlayerInventory playerInv) {
        super(null, id, tile, playerInv);
    }

}
