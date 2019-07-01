package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;

public class ContainerCarWorkshopCrafting extends ContainerBase {

    protected PlayerInventory playerInventory;
    protected TileEntityCarWorkshop tile;

    public ContainerCarWorkshopCrafting(int id, TileEntityCarWorkshop tile, PlayerInventory playerInventory) {
        super(null, id, tile, playerInventory);
        this.playerInventory = playerInventory;
        this.tile = tile;

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 5; x++) {
                this.addSlot(new Slot(tile, x + y * 5, 8 + x * 18, 73 + y * 18));
            }
        }

        addInvSlots();
    }

    @Override
    public int getInvOffset() {
        return 56;
    }

    public PlayerEntity getPlayer() {
        return playerInventory.player;
    }

    public TileEntityCarWorkshop getTile() {
        return tile;
    }

}
