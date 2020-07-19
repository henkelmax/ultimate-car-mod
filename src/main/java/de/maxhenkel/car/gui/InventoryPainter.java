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
    public int getSizeInventory() {
        return BlockPaint.EnumPaintType.values().length;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
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
    public ItemStack decrStackSize(int index, int count) {
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {

    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void openInventory(PlayerEntity player) {

    }

    @Override
    public void closeInventory(PlayerEntity player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }

    @Override
    public void clear() {

    }

}
