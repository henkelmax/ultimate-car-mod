package de.maxhenkel.car.integration.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class PainterReciepeHandler implements IRecipeHandler<PainterRecipeWrapper> {

	@Override
	public String getRecipeCategoryUid(PainterRecipeWrapper wrapper) {
		return JEIPlugin.CATEGORY_PAINTER;
	}

	@Override
	public Class<PainterRecipeWrapper> getRecipeClass() {
		return PainterRecipeWrapper.class;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(PainterRecipeWrapper wrapper) {
		return wrapper;
	}

	@Override
	public boolean isRecipeValid(PainterRecipeWrapper recipe) {
		return true;
	}

	@Override
	public String getRecipeCategoryUid() {
		return JEIPlugin.CATEGORY_PAINTER;
	}

}
