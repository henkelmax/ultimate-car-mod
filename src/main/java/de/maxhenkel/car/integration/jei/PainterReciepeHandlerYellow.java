package de.maxhenkel.car.integration.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class PainterReciepeHandlerYellow implements IRecipeHandler<PainterRecipeWrapperYellow>{

	@Override
	public String getRecipeCategoryUid(PainterRecipeWrapperYellow wrapper) {
		return JEIPlugin.CATEGORY_PAINTER_YELLOW;
	}

	@Override
	public Class<PainterRecipeWrapperYellow> getRecipeClass() {
		return PainterRecipeWrapperYellow.class;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(PainterRecipeWrapperYellow wrapper) {
		return wrapper;
	}

	@Override
	public boolean isRecipeValid(PainterRecipeWrapperYellow recipe) {
		return true;
	}

	@Override
	public String getRecipeCategoryUid() {
		return JEIPlugin.CATEGORY_PAINTER_YELLOW;
	}

}
