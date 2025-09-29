package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.items.ItemPainter;
import net.minecraft.client.Minecraft;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlotPainter extends Slot {

    private Player player;
    private int index;

    public SlotPainter(Player player, Container inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
        this.player = player;
        this.index = index;
    }

    @Override
    public boolean mayPickup(Player player) {
        setPainterID(index);
        if (player.level().isClientSide()) {
            Minecraft.getInstance().execute(() -> {
                Minecraft.getInstance().setScreen(null);
            });
        }
        return false;
    }

    public void setPainterID(int index) {
        ItemStack stack = player.getMainHandItem();

        Item i = stack.getItem();

        if (!(i instanceof ItemPainter)) {
            return;
        }
        stack.set(CarMod.PAINTER_INDEX_DATA_COMPONENT, index);
    }

    public static int getPainterID(ItemStack stack) {
        return stack.getOrDefault(CarMod.PAINTER_INDEX_DATA_COMPONENT, 0);
    }

}
