package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import de.maxhenkel.corelib.item.ItemUtils;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class SlotFluidFilter extends Slot {

    private TileEntityFluidExtractor tile;
    private int index;

    public SlotFluidFilter(Container inv, int index, int xPosition, int yPosition, TileEntityFluidExtractor tile) {
        super(inv, index, xPosition, yPosition);
        this.tile = tile;
        this.index = index;

        setFluidContained(tile.getFilter());
    }

    @Override
    public boolean mayPickup(Player playerIn) {
        tile.setFilter(null);
        ItemUtils.removeStackFromSlot(container, index);
        setChanged();
        return false;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        setFluidContained(stack);
        return false;
    }

    private void setFluidContained(ItemStack stack) {
        if (stack == null) {
            ItemUtils.removeStackFromSlot(container, index);
            setChanged();
            return;
        }

        FluidStack fluidStack = FluidUtil.getFluidContained(stack).orElse(null);

        if (fluidStack == null || fluidStack.getAmount() <= 0) {
            ItemUtils.removeStackFromSlot(container, index);
            setChanged();
            return;
        }

        tile.setFilter(stack);

        Item i = fluidStack.getFluid().getBucket();

        if (i == null) {
            container.setItem(index, stack.copy());
            setChanged();
        } else {
            container.setItem(index, new ItemStack(i));
            setChanged();
        }
    }

}
