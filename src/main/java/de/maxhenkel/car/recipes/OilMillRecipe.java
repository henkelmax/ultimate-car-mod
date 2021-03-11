package de.maxhenkel.car.recipes;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class OilMillRecipe extends EnergyFluidProducerRecipe {

    public OilMillRecipe(ResourceLocation idIn, String group, Ingredient ingredientIn, ItemStack result, int fluidAmount, int energy, int duration) {
        super(Main.RECIPE_TYPE_OIL_MILL, idIn, group, ingredientIn, result, fluidAmount, energy, duration);
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.OIL_MILL);
    }


    @Override
    public IRecipeSerializer<?> getSerializer() {
        return Main.CRAFTING_OIL_MILL;
    }
}
