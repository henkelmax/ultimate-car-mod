package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class ContainerCarWorkshopRepair extends ContainerBase {

    protected Inventory playerInventory;
    protected TileEntityCarWorkshop tile;

    public ContainerCarWorkshopRepair(int id, TileEntityCarWorkshop tile, Inventory playerInventory) {
        super(CarMod.CAR_WORKSHOP_REPAIR_CONTAINER_TYPE.get(), id, playerInventory, tile);
        this.playerInventory = playerInventory;
        this.tile = tile;

        this.addSlot(new SlotOneItem(tile.getRepairInventory(), 0, 50, 61, ModItems.SCREW_DRIVER.get()));
        this.addSlot(new SlotOneItem(tile.getRepairInventory(), 1, 80, 61, ModItems.WRENCH.get()));
        this.addSlot(new SlotOneItem(tile.getRepairInventory(), 2, 110, 61, ModItems.HAMMER.get()));

        addPlayerInventorySlots();
    }

    @Override
    public int getInvOffset() {
        return 56;
    }

    @Override
    public int getInventorySize() {
        return 3;
    }

    public Player getPlayer() {
        return playerInventory.player;
    }

    public TileEntityCarWorkshop getTile() {
        return tile;
    }

}
