package de.maxhenkel.car.integration.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.tools.EntityTools;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CarRecipeCategory implements IRecipeCategory<CarRecipe> {

    private IGuiHelper helper;

    private static final int RECIPE_WIDTH = 175;
    private static final int RECIPE_HEIGHT = 54;

    public CarRecipeCategory(IGuiHelper helper) {
        this.helper = helper;
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(new ResourceLocation(Main.MODID, "textures/gui/jei_car_workshop_crafting.png"), 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT);
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(new ItemStack(ModBlocks.CAR_WORKSHOP));
    }


    @Override
    public void setIngredients(CarRecipe recipe, IIngredients iIngredients) {
        iIngredients.setInputs(VanillaTypes.ITEM, recipe.getInputs());
    }

    @Override
    public String getTitle() {
        return ModBlocks.CAR_WORKSHOP.getTranslatedName().getString();
    }

    @Override
    public ResourceLocation getUid() {
        return JEIPlugin.CATEGORY_CAR_WORKSHOP;
    }

    @Override
    public Class<? extends CarRecipe> getRecipeClass() {
        return CarRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayout layout, CarRecipe wrapper, IIngredients ingredients) {
        IGuiItemStackGroup group = layout.getItemStacks();

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 5; x++) {
                group.init(x + y * 5, true, x * 18, y * 18);
                int index = x + y * 5;
                if (index >= wrapper.getInputs().size()) {
                    continue;
                }
                ItemStack stack = wrapper.getInputs().get(index);
                if (!stack.isEmpty()) {
                    group.set(index, stack);
                }
            }
        }
    }

    private EntityTools.SimulatedCarRenderer renderer = new EntityTools.SimulatedCarRenderer();

    @Override
    public void draw(CarRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        renderer.render(matrixStack, recipe.getCar(), RECIPE_WIDTH - 30, RECIPE_HEIGHT - 54 / 4, 18);
    }
}
