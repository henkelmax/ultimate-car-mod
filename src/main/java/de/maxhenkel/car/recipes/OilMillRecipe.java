package de.maxhenkel.car.recipes;

import de.maxhenkel.car.CarMod;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class OilMillRecipe extends EnergyFluidProducerRecipe {

    public OilMillRecipe(String group, Ingredient ingredientIn, ItemStack result, int fluidAmount, int energy, int duration) {
        super(CarMod.RECIPE_TYPE_OIL_MILL.get(), group, ingredientIn, result, fluidAmount, energy, duration);
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return CarMod.CRAFTING_OIL_MILL.get();
    }

}
