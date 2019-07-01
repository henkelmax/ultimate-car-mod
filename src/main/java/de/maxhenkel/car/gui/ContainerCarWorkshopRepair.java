package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;

public class ContainerCarWorkshopRepair extends ContainerBase {

    protected PlayerInventory playerInventory;
    protected TileEntityCarWorkshop tile;

    public ContainerCarWorkshopRepair(int id, TileEntityCarWorkshop tile, PlayerInventory playerInventory) {
        super(Main.CAR_WORKSHOP_REPAIR_CONTAINER_TYPE, id, tile, playerInventory);
        this.playerInventory = playerInventory;
        this.tile = tile;

        this.addSlot(new SlotOneItem(tile.getRepairInventory(), 0, 50, 61, ModItems.SCREW_DRIVER));
        this.addSlot(new SlotOneItem(tile.getRepairInventory(), 1, 80, 61, ModItems.WRENCH));
        this.addSlot(new SlotOneItem(tile.getRepairInventory(), 2, 110, 61, ModItems.HAMMER));

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
