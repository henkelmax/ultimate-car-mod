package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityEnergyFluidProducer;
import net.minecraft.world.entity.player.Inventory;

public class ContainerOilMill extends ContainerEnergyFluidProducer {

    public ContainerOilMill(int id, TileEntityEnergyFluidProducer tile, Inventory playerInv) {
        super(CarMod.OIL_MILL_CONTAINER_TYPE.get(), id, tile, playerInv);
    }

}
