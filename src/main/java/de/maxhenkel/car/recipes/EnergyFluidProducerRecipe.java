package de.maxhenkel.car.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class EnergyFluidProducerRecipe implements IRecipe<IInventory> {
    protected final IRecipeType<?> type;
    protected final ResourceLocation id;
    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final String group;
    protected final int fluidAmount;
    protected final int energy;
    protected final int duration;

    public EnergyFluidProducerRecipe(IRecipeType<?> type, ResourceLocation idIn, String group, Ingredient ingredientIn, ItemStack result, int fluidAmount, int energy, int duration) {
        this.type = type;
        this.id = idIn;
        this.group = group;
        this.ingredient = ingredientIn;
        this.result = result;
        this.fluidAmount = fluidAmount;
        this.energy = energy;
        this.duration = duration;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return ingredient.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return result;
    }

    @Override
    public boolean canFit(int width, int height) {
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
    public boolean isDynamic() {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return result;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(IInventory inv) {
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
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeType<?> getType() {
        return type;
    }
}