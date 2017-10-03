package de.maxhenkel.car.integration.jei;

import de.maxhenkel.car.reciepe.ICarRecipe;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class CarRecipeWrapperFactory implements IRecipeWrapperFactory<ICarRecipe>{

	@Override
	public IRecipeWrapper getRecipeWrapper(ICarRecipe recipe) {
		return new CarRecipeWrapper(recipe);
	}

}
