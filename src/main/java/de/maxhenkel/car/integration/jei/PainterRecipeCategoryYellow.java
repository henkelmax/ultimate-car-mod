package de.maxhenkel.car.integration.jei;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.items.ModItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class PainterRecipeCategoryYellow implements IRecipeCategory<PainterRecipe> {

    private IGuiHelper helper;

    public PainterRecipeCategoryYellow(IGuiHelper helper) {
        this.helper = helper;
    }

    @Override
    public RecipeType<PainterRecipe> getRecipeType() {
        return JEIPlugin.CATEGORY_PAINTER_YELLOW;
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(new ResourceLocation(Main.MODID, "textures/gui/jei_painter.png"), 0, 0, 80, 54);
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModItems.PAINTER_YELLOW.get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PainterRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 19).addIngredient(VanillaTypes.ITEM, new ItemStack(recipe.getInput()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 59, 19).addIngredient(VanillaTypes.ITEM, new ItemStack(recipe.getOutput()));
    }

    @Override
    public Component getTitle() {
        return ModItems.PAINTER.get().getDescription();
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(Main.MODID, "painter_yellow");
    }

    @Override
    public Class<? extends PainterRecipe> getRecipeClass() {
        return PainterRecipe.class;
    }

}
