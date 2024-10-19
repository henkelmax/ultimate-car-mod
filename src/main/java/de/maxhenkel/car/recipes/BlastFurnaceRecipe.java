package de.maxhenkel.car.recipes;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class BlastFurnaceRecipe extends EnergyFluidProducerRecipe {

    public BlastFurnaceRecipe(String group, Ingredient ingredientIn, ItemStack result, int fluidAmount, int energy, int duration) {
        super(Main.RECIPE_TYPE_BLAST_FURNACE.get(), group, ingredientIn, result, fluidAmount, energy, duration);
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return Main.CRAFTING_BLAST_FURNACE.get();
    }

}
