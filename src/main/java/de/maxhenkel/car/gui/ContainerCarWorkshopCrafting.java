package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public class ContainerCarWorkshopCrafting extends ContainerBase {

    protected Inventory playerInventory;
    protected TileEntityCarWorkshop tile;

    public ContainerCarWorkshopCrafting(int id, TileEntityCarWorkshop tile, Inventory playerInventory) {
        super(Main.CAR_WORKSHOP_CRAFTING_CONTAINER_TYPE, id, playerInventory, tile);
        this.playerInventory = playerInventory;
        this.tile = tile;

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 5; x++) {
                this.addSlot(new Slot(tile, x + y * 5, 8 + x * 18, 73 + y * 18));
            }
        }

        addPlayerInventorySlots();
    }

    @Override
    public int getInvOffset() {
        return 56;
    }

    public Player getPlayer() {
        return playerInventory.player;
    }

    public TileEntityCarWorkshop getTile() {
        return tile;
    }

}
