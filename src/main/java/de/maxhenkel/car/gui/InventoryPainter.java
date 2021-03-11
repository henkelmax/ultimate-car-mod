package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.BlockPaint;
import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryPainter implements IInventory {

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
        if (index >= 0 && index < ModBlocks.PAINTS.length) {
            if (isYellow) {
                return new ItemStack(ModBlocks.YELLOW_PAINTS[index]);
            } else {
                return new ItemStack(ModBlocks.PAINTS[index]);
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
    public boolean stillValid(PlayerEntity player) {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void startOpen(PlayerEntity player) {

    }

    @Override
    public void stopOpen(PlayerEntity player) {

    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return false;
    }

    @Override
    public void clearContent() {

    }

}
