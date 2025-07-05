package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityBackmixReactor;
import net.minecraft.world.entity.player.Inventory;

public class ContainerBackmixReactor extends ContainerBase {

    private TileEntityBackmixReactor backmixReactor;

    public ContainerBackmixReactor(int id, TileEntityBackmixReactor backmixReactor, Inventory playerInv) {
        super(CarMod.BACKMIX_REACTOR_CONTAINER_TYPE.get(), id, playerInv, backmixReactor);
        this.backmixReactor = backmixReactor;
        addPlayerInventorySlots();
    }

    public TileEntityBackmixReactor getBackmixReactor() {
        return backmixReactor;
    }

}
