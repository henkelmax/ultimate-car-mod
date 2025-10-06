package de.maxhenkel.car.integration.jei;

import de.maxhenkel.car.items.ModItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.world.item.ItemStack;

public class PainterRecipeCategoryYellow extends PainterRecipeCategory {


    public PainterRecipeCategoryYellow(IGuiHelper helper) {
        super(helper);
    }

    @Override
    public IRecipeType<PainterRecipe> getRecipeType() {
        return JEIPlugin.CATEGORY_PAINTER_YELLOW;
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.PAINTER_YELLOW.get()));
    }

}
