package de.maxhenkel.car.recipes;

import de.maxhenkel.car.Main;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class OilMillRecipe extends EnergyFluidProducerRecipe {

    public OilMillRecipe(String group, Ingredient ingredientIn, ItemStack result, int fluidAmount, int energy, int duration) {
        super(Main.RECIPE_TYPE_OIL_MILL.get(), group, ingredientIn, result, fluidAmount, energy, duration);
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return Main.CRAFTING_OIL_MILL.get();
    }

}
