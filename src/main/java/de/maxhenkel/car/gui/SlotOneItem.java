package de.maxhenkel.car.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotOneItem extends Slot {

    private Item item;

    public SlotOneItem(IInventory inventoryIn, int index, int xPosition, int yPosition, Item item) {
        super(inventoryIn, index, xPosition, yPosition);
        this.item = item;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem().equals(item);
    }

}
