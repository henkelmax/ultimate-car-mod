package de.maxhenkel.car.recipes;

import java.util.UUID;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.items.ItemKey;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class KeyRecipe extends CustomRecipe {

    public KeyRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public ItemStack getResultItem() {
        return new ItemStack(ModItems.KEY.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Main.CRAFTING_SPECIAL_KEY.get();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        NonNullList<ItemStack> list = NonNullList.create();

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);

            if (stack.isEmpty()) {
                continue;
            }

            if (stack.getItem().equals(Items.IRON_INGOT)) {
                stack.shrink(1);
                inv.setItem(i, stack);
            }
        }

        return list;
    }

    @Override
    public boolean matches(CraftingContainer inv, Level worldIn) {
        return assemble(inv) != null;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv) {
        ItemStack key = null;
        ItemStack iron = null;

        for (int i = 0; i < inv.getContainerSize(); i++) {
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

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        if (width > 1 && height > 1) {
            return true;
        }

        return false;
    }

}
