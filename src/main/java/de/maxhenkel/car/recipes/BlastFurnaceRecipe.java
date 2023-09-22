package de.maxhenkel.car.recipes;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BlastFurnaceRecipe extends EnergyFluidProducerRecipe {

    public BlastFurnaceRecipe(String group, Ingredient ingredientIn, ItemStack result, int fluidAmount, int energy, int duration) {
        super(Main.RECIPE_TYPE_BLAST_FURNACE.get(), group, ingredientIn, result, fluidAmount, energy, duration);
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.BLAST_FURNACE.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Main.CRAFTING_BLAST_FURNACE.get();
    }
}
