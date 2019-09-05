package de.maxhenkel.tools;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class ItemTools {

    public static boolean matchesTag(ItemStack stack, ResourceLocation tag) {
        return stack.getItem().getTags().contains(tag);
    }

    /**
     * Compares two stacks except NBT data
     *
     * @param stack1
     * @param stack2
     * @return if the two stacks are equal
     */
    public static boolean areItemsEqual(ItemStack stack1, ItemStack stack2) {
        if (stack1 == null || stack2 == null) {
            return false;
        }

        if (stack1.getItem() == null || stack2.getItem() == null) {
            return false;
        }

        if (stack1.getItem() == stack2.getItem()) {
            return stack1.getDamage() == stack2.getDamage();
        }

        return false;
    }

    /**
     * Checks if the list contains the provided item
     * Doesn't comapre NBT data
     *
     * @param list
     * @param item
     * @return
     */
    public static boolean contains(List<ItemStack> list, ItemStack item) {
        return list.stream().anyMatch(stack -> areItemsEqual(stack, item));
    }

    /**
     * Changes the Itemstack amount. If a player is provided and the player is in
     * Creative Mode, the stack wont be changed
     *
     * @param amount The amount to change
     * @param stack  The Item Stack
     * @param player The player. Can be null
     * @return The Itemstack with the changed amount
     */
    public static ItemStack itemStackAmount(int amount, ItemStack stack, PlayerEntity player) {
        if (stack == null || stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (player != null && player.abilities.isCreativeMode) {
            return stack;
        }

        stack.setCount(stack.getCount() + amount);
        if (stack.getCount() <= 0) {
            stack.setCount(0);
            return ItemStack.EMPTY;
        }

        if (stack.getCount() > stack.getMaxStackSize()) {
            stack.setCount(stack.getMaxStackSize());
        }

        return stack;
    }

    public static ItemStack decrItemStack(ItemStack stack, PlayerEntity player) {
        return itemStackAmount(-1, stack, player);
    }

    public static ItemStack incrItemStack(ItemStack stack, PlayerEntity player) {
        return itemStackAmount(1, stack, player);
    }

    public static void removeStackFromSlot(IInventory inventory, int index) {
        inventory.setInventorySlotContents(index, ItemStack.EMPTY);
    }

    public static void saveInventory(CompoundNBT compound, String name, IInventory inv) {
        ListNBT nbttaglist = new ListNBT();

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            if (!inv.getStackInSlot(i).isEmpty()) {
                CompoundNBT nbttagcompound = new CompoundNBT();
                nbttagcompound.putInt("Slot", i);
                inv.getStackInSlot(i).write(nbttagcompound);
                nbttaglist.add(nbttagcompound);
            }
        }

        compound.put(name, nbttaglist);
    }

    public static void readInventory(CompoundNBT compound, String name, IInventory inv) {
        if (!compound.contains(name)) {
            return;
        }

        ListNBT nbttaglist = compound.getList(name, 10);

        if (nbttaglist == null) {
            return;
        }

        for (int i = 0; i < nbttaglist.size(); i++) {
            CompoundNBT nbttagcompound = nbttaglist.getCompound(i);
            int j = nbttagcompound.getInt("Slot");

            if (j >= 0 && j < inv.getSizeInventory()) {
                inv.setInventorySlotContents(j, ItemStack.read(nbttagcompound));
            }
        }
    }

}
