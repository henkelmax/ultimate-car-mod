package de.maxhenkel.car.recipes;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class BlastFurnaceRecipe extends EnergyFluidProducerRecipe {

    public BlastFurnaceRecipe(ResourceLocation idIn, String group, Ingredient ingredientIn, ItemStack result, int fluidAmount, int energy, int duration) {
        super(Main.RECIPE_TYPE_BLAST_FURNACE, idIn, group, ingredientIn, result, fluidAmount, energy, duration);
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.BLAST_FURNACE);
    }


    @Override
    public IRecipeSerializer<?> getSerializer() {
        return Main.CRAFTING_BLAST_FURNACE;
    }
}
