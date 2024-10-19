package de.maxhenkel.car.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public abstract class EnergyFluidProducerRecipe implements Recipe<RecipeInput> {

    protected final RecipeType<? extends Recipe<RecipeInput>> type;
    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final String group;
    protected final int fluidAmount;
    protected final int energy;
    protected final int duration;

    public EnergyFluidProducerRecipe(RecipeType<? extends Recipe<RecipeInput>> type, String group, Ingredient ingredientIn, ItemStack result, int fluidAmount, int energy, int duration) {
        this.type = type;
        this.group = group;
        this.ingredient = ingredientIn;
        this.result = result;
        this.fluidAmount = fluidAmount;
        this.energy = energy;
        this.duration = duration;
    }

    @Override
    public boolean matches(RecipeInput inv, Level worldIn) {
        return ingredient.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider provider) {
        return result.copy();
    }


    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    public ItemStack getResultItem() {
        return result.copy();
    }

    public int getFluidAmount() {
        return fluidAmount;
    }

    public int getEnergy() {
        return energy;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return type;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(ingredient);
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

}