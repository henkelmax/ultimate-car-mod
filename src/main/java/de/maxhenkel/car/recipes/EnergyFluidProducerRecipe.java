package de.maxhenkel.car.recipes;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public abstract class EnergyFluidProducerRecipe implements Recipe<Container> {
    protected final RecipeType<?> type;
    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final String group;
    protected final int fluidAmount;
    protected final int energy;
    protected final int duration;

    public EnergyFluidProducerRecipe(RecipeType<?> type, String group, Ingredient ingredientIn, ItemStack result, int fluidAmount, int energy, int duration) {
        this.type = type;
        this.group = group;
        this.ingredient = ingredientIn;
        this.result = result;
        this.fluidAmount = fluidAmount;
        this.energy = energy;
        this.duration = duration;
    }

    @Override
    public boolean matches(Container inv, Level worldIn) {
        return ingredient.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(ingredient);
        return list;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return getResultItem();
    }

    public ItemStack getResultItem() {
        return result.copy();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(Container inv) {
        return null;
    }

    @Override
    public String getGroup() {
        return group;
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
    public RecipeType<?> getType() {
        return type;
    }
}