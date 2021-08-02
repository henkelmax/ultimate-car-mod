package de.maxhenkel.car.gui;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlotOneItem extends Slot {

    private Item item;

    public SlotOneItem(Container inventoryIn, int index, int xPosition, int yPosition, Item item) {
        super(inventoryIn, index, xPosition, yPosition);
        this.item = item;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem().equals(item);
    }

}
