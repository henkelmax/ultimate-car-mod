package de.maxhenkel.car.gui;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotPresent extends Slot {

    private ItemStack stack;

    public SlotPresent(ItemStack s, int xPosition, int yPosition) {
        super(null, 0, xPosition, yPosition);
        this.stack = s.copy();
        CompoundTag compound = stack.getOrCreateTag();

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
    public boolean mayPickup(Player playerIn) {
        return false;
    }
}
