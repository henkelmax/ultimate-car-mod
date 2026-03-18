package de.maxhenkel.car.recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public abstract class EnergyFluidProducerRecipe implements Recipe<RecipeInput> {

    protected final Ingredient ingredient;
    protected final ItemStackTemplate result;
    protected final String group;
    protected final int fluidAmount;
    protected final int energy;
    protected final int duration;

    public EnergyFluidProducerRecipe(String group, Ingredient ingredientIn, ItemStackTemplate result, int fluidAmount, int energy, int duration) {
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
    public ItemStack assemble(RecipeInput input) {
        return result.create();
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    public ItemStack getResultItem() {
        return result.create();
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
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(ingredient);
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

}