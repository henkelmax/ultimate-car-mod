package de.maxhenkel.car.recipes;

import de.maxhenkel.car.CarMod;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;

public class OilMillRecipe extends EnergyFluidProducerRecipe {

    public OilMillRecipe(String group, Ingredient ingredientIn, ItemStackTemplate result, int fluidAmount, int energy, int duration) {
        super(group, ingredientIn, result, fluidAmount, energy, duration);
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return CarMod.CRAFTING_OIL_MILL.get();
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return CarMod.RECIPE_TYPE_OIL_MILL.get();
    }
}
