package de.maxhenkel.car.integration.jei;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.items.ModItems;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class PainterRecipeCategory implements IRecipeCategory<PainterRecipeWrapper> {

	private IGuiHelper helper;

	public PainterRecipeCategory(IGuiHelper helper) {
		this.helper=helper;
	}
	
	@Override
	public IDrawable getBackground() {
		return helper.createDrawable(new ResourceLocation(Main.MODID,
				"textures/gui/jei_painter.png"), 0, 0, 80, 54);
	}

	@Override
	public IDrawable getIcon() {
		return helper.createDrawableIngredient(ModItems.PAINTER);
	}

	@Override
	public void setIngredients(PainterRecipeWrapper painterRecipeWrapper, IIngredients iIngredients) {

	}

	@Override
	public String getTitle() {
		return new TranslationTextComponent("item.car.painter").getFormattedText();
	}

	@Override
	public ResourceLocation getUid() {
		return JEIPlugin.CATEGORY_PAINTER;
	}

	@Override
	public Class<? extends PainterRecipeWrapper> getRecipeClass() {
		return PainterRecipeWrapper.class;
	}

	@Override
	public void setRecipe(IRecipeLayout layout, PainterRecipeWrapper wrapper, IIngredients ingredients) {
		IGuiItemStackGroup group = layout.getItemStacks();
		
		group.init(0, true,  0,  18);
		group.set(0, new ItemStack(ModItems.PAINTER));
		
		group.init(1, true, 58, 18);
//		group.set(1, new ItemStack(wrapper.getPaint())); //TODO
		
	}

}
