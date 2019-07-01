package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityBackmixReactor;
import net.minecraft.entity.player.PlayerInventory;

public class ContainerBackmixReactor extends ContainerBase {

    private TileEntityBackmixReactor backmixReactor;

    public ContainerBackmixReactor(int id, TileEntityBackmixReactor backmixReactor, PlayerInventory playerInv) {
        super(Main.BACKMIX_REACTOR_CONTAINER_TYPE, id, backmixReactor, playerInv);
        this.backmixReactor = backmixReactor;
        addInvSlots();
    }

    public TileEntityBackmixReactor getBackmixReactor() {
        return backmixReactor;
    }
}
