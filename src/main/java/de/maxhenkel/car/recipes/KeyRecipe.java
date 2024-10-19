package de.maxhenkel.car.recipes;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.items.ItemKey;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class KeyRecipe extends CustomRecipe {

    public KeyRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return Main.CRAFTING_SPECIAL_KEY.get();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput inv) {
        NonNullList<ItemStack> list = NonNullList.withSize(inv.size(), ItemStack.EMPTY);

        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getItem(i);

            if (stack.isEmpty()) {
                continue;
            }

            if (stack.getItem().equals(ModItems.KEY.get())) {
                list.set(i, stack.copy());
            }
        }

        return list;
    }

    @Override
    public boolean matches(CraftingInput inv, Level worldIn) {
        return assemble(inv) != null;
    }

    @Override
    public ItemStack assemble(CraftingInput craftingContainer, HolderLookup.Provider provider) {
        return assemble(craftingContainer);
    }

    public ItemStack assemble(CraftingInput inv) {
        ItemStack key = null;
        ItemStack iron = null;

        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getItem(i);

            if (stack.isEmpty()) {
                continue;
            }

            if (stack.getItem().equals(ModItems.KEY.get())) {
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
            return new ItemStack(ModItems.KEY.get());
        }

        ItemStack out = ItemKey.getKeyForCar(uuid);

        return out;
    }

}
