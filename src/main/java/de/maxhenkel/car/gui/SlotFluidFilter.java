package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import de.maxhenkel.corelib.item.ItemUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class SlotFluidFilter extends Slot {

    private TileEntityFluidExtractor tile;
    private int index;

    public SlotFluidFilter(IInventory inv, int index, int xPosition, int yPosition, TileEntityFluidExtractor tile) {
        super(inv, index, xPosition, yPosition);
        this.tile = tile;
        this.index = index;

        setFluidContained(tile.getFilter());
    }

    @Override
    public boolean canTakeStack(PlayerEntity playerIn) {
        tile.setFilter(null);
        ItemUtils.removeStackFromSlot(inventory, index);
        onSlotChanged();
        return false;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        setFluidContained(stack);
        return false;
    }

    private void setFluidContained(ItemStack stack) {
        if (stack == null) {
            ItemUtils.removeStackFromSlot(inventory, index);
            onSlotChanged();
            return;
        }

        FluidStack fluidStack = FluidUtil.getFluidContained(stack).orElse(null);

        if (fluidStack == null || fluidStack.getAmount() <= 0) {
            ItemUtils.removeStackFromSlot(inventory, index);
            onSlotChanged();
            return;
        }

        tile.setFilter(stack);

        Item i = fluidStack.getFluid().getFilledBucket();

        if (i == null) {
            inventory.setInventorySlotContents(index, stack.copy());
            onSlotChanged();
        } else {
            inventory.setInventorySlotContents(index, new ItemStack(i));
            onSlotChanged();
        }
    }

}
