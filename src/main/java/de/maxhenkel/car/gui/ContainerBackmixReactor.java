package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityBackmixReactor;
import net.minecraft.entity.player.PlayerInventory;

public class ContainerBackmixReactor extends ContainerBase {

    private TileEntityBackmixReactor backmixReactor;

    public ContainerBackmixReactor(int id, TileEntityBackmixReactor backmixReactor, PlayerInventory playerInv) {
        super(Main.BACKMIX_REACTOR_CONTAINER_TYPE, id, playerInv, backmixReactor);
        this.backmixReactor = backmixReactor;
        addPlayerInventorySlots();
    }

    public TileEntityBackmixReactor getBackmixReactor() {
        return backmixReactor;
    }

}
