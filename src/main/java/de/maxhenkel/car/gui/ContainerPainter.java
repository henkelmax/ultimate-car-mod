package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.BlockPaint;
import net.minecraft.entity.player.PlayerInventory;

public class ContainerPainter extends ContainerBase {

    protected PlayerInventory playerInventory;

    public ContainerPainter(int id, PlayerInventory playerInventory, boolean isYellow) {
        super(Main.PAINTER_CONTAINER_TYPE, id, null, new InventoryPainter(isYellow));
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
