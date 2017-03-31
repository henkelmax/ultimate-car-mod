package de.maxhenkel.car.integration.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CarRecipeHandler implements IRecipeHandler<CarRecipeWrapper>{

	@Override
	public String getRecipeCategoryUid(CarRecipeWrapper wrapper) {
		return JEIPlugin.CATEGORY_CAR_WORKSHOP;
	}

	@Override
	public Class<CarRecipeWrapper> getRecipeClass() {
		return CarRecipeWrapper.class;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(CarRecipeWrapper wrapper) {
		return wrapper;
	}

	@Override
	public boolean isRecipeValid(CarRecipeWrapper recipe) {
		return true;
	}

	@Override
	public String getRecipeCategoryUid() {
		return JEIPlugin.CATEGORY_CAR_WORKSHOP;
	}

}
