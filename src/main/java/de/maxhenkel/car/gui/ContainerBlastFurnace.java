package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityEnergyFluidProducer;
import net.minecraft.entity.player.PlayerInventory;

public class ContainerBlastFurnace extends ContainerEnergyFluidProducer {

    public ContainerBlastFurnace(int id, TileEntityEnergyFluidProducer tileInv, PlayerInventory playerInv) {
        super(null, id, tileInv, playerInv);
    }
}
