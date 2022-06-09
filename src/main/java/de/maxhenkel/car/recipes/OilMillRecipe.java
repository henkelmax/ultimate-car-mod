package de.maxhenkel.car.recipes;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class OilMillRecipe extends EnergyFluidProducerRecipe {

    public OilMillRecipe(ResourceLocation idIn, String group, Ingredient ingredientIn, ItemStack result, int fluidAmount, int energy, int duration) {
        super(Main.RECIPE_TYPE_OIL_MILL.get(), idIn, group, ingredientIn, result, fluidAmount, energy, duration);
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.OIL_MILL.get());
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return Main.CRAFTING_OIL_MILL.get();
    }
}
