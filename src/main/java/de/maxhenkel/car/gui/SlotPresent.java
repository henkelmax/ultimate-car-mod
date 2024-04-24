package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import net.minecraft.util.Unit;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotPresent extends Slot {

    private final ItemStack stack;

    public SlotPresent(ItemStack s, int xPosition, int yPosition) {
        super(new SimpleContainer(1), 0, xPosition, yPosition);
        this.stack = s.copy();
        stack.set(Main.TRADING_ITEM_DATA_COMPONENT, Unit.INSTANCE);
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
