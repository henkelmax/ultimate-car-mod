package de.maxhenkel.car.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class SlotPresent extends Slot {

    private ItemStack stack;

    public SlotPresent(ItemStack s, int xPosition, int yPosition) {
        super(null, 0, xPosition, yPosition);
        this.stack = s.copy();
        CompoundNBT compound = stack.getOrCreateTag();

        compound.putBoolean("trading_item", true);
    }

    @Override
    public ItemStack getItem() {
        return stack;
    }

    @Override
    public void set(ItemStack stack) {

    }

    @Override
    public void setChanged() {

    }

    @Override
    public int getMaxStackSize() {
        return 0;
    }

    @Override
    public ItemStack remove(int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public boolean mayPickup(PlayerEntity playerIn) {
        return false;
    }
}
