package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.BlockPaint;
import net.minecraft.world.entity.player.Inventory;

public class ContainerPainter extends ContainerBase {

    protected Inventory playerInventory;

    public ContainerPainter(int id, Inventory playerInventory, boolean isYellow) {
        super(CarMod.PAINTER_CONTAINER_TYPE.get(), id, null, new InventoryPainter(isYellow));
        this.playerInventory = playerInventory;

        for (int j = 0; j < 5; j++) {
            for (int k = 0; k < 9; k++) {
                int index = k + j * 9;
                if (index < BlockPaint.EnumPaintType.values().length) {
                    addSlot(new SlotPainter(playerInventory.player, inventory, index, 8 + k * 18, 18 + j * 18));
                }
            }
        }
    }

}
