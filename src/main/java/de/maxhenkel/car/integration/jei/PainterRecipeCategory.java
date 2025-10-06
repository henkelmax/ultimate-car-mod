package de.maxhenkel.car.integration.jei;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.items.ModItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class PainterRecipeCategory implements IRecipeCategory<PainterRecipe> {

    protected static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/gui/jei_painter.png");

    protected final IGuiHelper helper;
    protected final IDrawableStatic background;

    public PainterRecipeCategory(IGuiHelper helper) {
        this.helper = helper;
        background = helper.createDrawable(BACKGROUND, 0, 0, getWidth(), getHeight());
    }

    @Override
    public void draw(PainterRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.PAINTER.get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PainterRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 19).add(VanillaTypes.ITEM_STACK, new ItemStack(recipe.getInput()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 59, 19).add(VanillaTypes.ITEM_STACK, new ItemStack(recipe.getOutput()));
    }

    @Override
    public IRecipeType<PainterRecipe> getRecipeType() {
        return JEIPlugin.CATEGORY_PAINTER;
    }

    @Override
    public Component getTitle() {
        return ModItems.PAINTER.get().getName();
    }

    @Override
    public int getWidth() {
        return 80;
    }

    @Override
    public int getHeight() {
        return 54;
    }
}
