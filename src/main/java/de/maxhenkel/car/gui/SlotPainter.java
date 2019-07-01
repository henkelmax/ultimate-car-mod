package de.maxhenkel.car.gui;

import de.maxhenkel.car.items.ItemPainter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class SlotPainter extends Slot {

    private PlayerEntity player;
    private int index;

    public SlotPainter(PlayerEntity player, IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
        this.player = player;
        this.index = index;
    }

    @Override
    public boolean canTakeStack(PlayerEntity player) {
        setPainterID(index);
        if (player.world.isRemote) {
            Minecraft.getInstance().displayGuiScreen(null);
        }
        return false;
    }

    public void setPainterID(int index) {
        ItemStack stack = player.getHeldItemMainhand();
        if (stack == null) {
            return;
        }

        Item i = stack.getItem();

        if (!(i instanceof ItemPainter)) {
            return;
        }

        CompoundNBT compound = stack.getOrCreateTag();

        compound.putInt("index", index);
    }

    public static int getPainterID(PlayerEntity player) {
        ItemStack stack = getPainterStack(player);

        if (stack == null) {
            return 0;
        }

        if (!stack.hasTag()) {
            return 0;
        }

        CompoundNBT compound = stack.getOrCreateTag();

        if (!compound.contains("index")) {
            return 0;
        }

        return compound.getInt("index");
    }

    public static ItemStack getPainterStack(PlayerEntity player) {
        ItemStack stack = player.getHeldItemMainhand();
        if (stack == null) {
            return null;
        }

        Item i = stack.getItem();

        if (!(i instanceof ItemPainter)) {
            return null;
        }

        return stack;
    }

}
