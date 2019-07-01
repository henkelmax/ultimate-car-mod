package de.maxhenkel.car.gui;

import de.maxhenkel.tools.ItemTools;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotInputOredict extends Slot {

    private String name;

    public SlotInputOredict(IInventory inventoryIn, int index, int xPosition, int yPosition, String name) {
        super(inventoryIn, index, xPosition, yPosition);
        this.name = name;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return ItemTools.matchesOredict(stack, name);
    }

}
