package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.BlockPaint;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class InventoryPainter implements Container {

    private boolean isYellow;

    public InventoryPainter(boolean isYellow) {
        this.isYellow = isYellow;
    }

    @Override
    public int getContainerSize() {
        return BlockPaint.EnumPaintType.values().length;
    }

    @Override
    public ItemStack getItem(int index) {
        if (index >= 0 && index < ModItems.PAINTS.length) {
            if (isYellow) {
                return new ItemStack(ModItems.YELLOW_PAINTS[index].get());
            } else {
                return new ItemStack(ModItems.PAINTS[index].get());
            }
        }
        return null;
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return null;
    }

    @Override
    public void setItem(int index, ItemStack stack) {

    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return false;
    }

    @Override
    public void clearContent() {

    }

}
