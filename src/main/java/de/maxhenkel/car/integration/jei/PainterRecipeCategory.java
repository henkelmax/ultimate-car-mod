package de.maxhenkel.car.integration.jei;
/*
import de.maxhenkel.car.Main;
import de.maxhenkel.car.items.ModItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class PainterRecipeCategory implements IRecipeCategory<PainterRecipe> {

    private IGuiHelper helper;

    public PainterRecipeCategory(IGuiHelper helper) {
        this.helper = helper;
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(new ResourceLocation(Main.MODID,
                "textures/gui/jei_painter.png"), 0, 0, 80, 54);
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(new ItemStack(ModItems.PAINTER));
    }


    @Override
    public void setIngredients(PainterRecipe recipe, IIngredients iIngredients) {
        iIngredients.setInput(VanillaTypes.ITEM, new ItemStack(recipe.getInput()));
        iIngredients.setOutput(VanillaTypes.ITEM, new ItemStack(recipe.getOutput()));
    }

    @Override
    public String getTitle() {
        return ModItems.PAINTER.getName().getFormattedText();
    }

    @Override
    public ResourceLocation getUid() {
        return JEIPlugin.CATEGORY_PAINTER;
    }

    @Override
    public Class<? extends PainterRecipe> getRecipeClass() {
        return PainterRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayout layout, PainterRecipe wrapper, IIngredients ingredients) {
        IGuiItemStackGroup group = layout.getItemStacks();

        group.init(0, true, 0, 18);
        group.set(0, new ItemStack(wrapper.getInput()));

        group.init(1, true, 58, 18);
        group.set(1, new ItemStack(wrapper.getOutput()));

    }
}
*/