package de.maxhenkel.car.recipes;

import java.util.UUID;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.items.ItemKey;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class KeyRecipe extends SpecialRecipe {

    public KeyRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ModItems.KEY);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return Main.CRAFTING_SPECIAL_KEY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        NonNullList<ItemStack> list = NonNullList.create();

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);

            if (stack.isEmpty()) {
                continue;
            }

            if (stack.getItem().equals(Items.IRON_INGOT)) {
                stack.shrink(1);
                inv.setInventorySlotContents(i, stack);
            }
        }

        return list;
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        return getCraftingResult(inv) != null;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack key = null;
        ItemStack iron = null;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);

            if (stack.isEmpty()) {
                continue;
            }

            if (stack.getItem().equals(ModItems.KEY)) {
                if (key != null) {
                    return null;
                }
                key = stack;
            } else if (stack.getItem().equals(Items.IRON_INGOT)) {
                if (iron != null) {
                    return null;
                }
                iron = stack;
            }
        }

        if (key == null || iron == null) {
            return null;
        }

        UUID uuid = ItemKey.getCar(key);

        if (uuid == null) {
            return new ItemStack(ModItems.KEY);
        }

        ItemStack out = ItemKey.getKeyForCar(uuid);

        return out;
    }

    @Override
    public boolean canFit(int width, int height) {
        if (width > 1 && height > 1) {
            return true;
        }

        return false;
    }

}
