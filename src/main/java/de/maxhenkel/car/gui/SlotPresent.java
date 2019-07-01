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
    public ItemStack getStack() {
        return stack;
    }

    @Override
    public void putStack(ItemStack stack) {

    }

    @Override
    public void onSlotChanged() {

    }

    @Override
    public int getSlotStackLimit() {
        return 0;//TODO maybe fix
    }

    @Override
    public ItemStack decrStackSize(int amount) {
        return ItemStack.EMPTY;
    }


    /*
    @Override
    public boolean isHere(IInventory inv, int slotIn) {
        return false;
    }*/

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canTakeStack(PlayerEntity playerIn) {
        return false;
    }
}
