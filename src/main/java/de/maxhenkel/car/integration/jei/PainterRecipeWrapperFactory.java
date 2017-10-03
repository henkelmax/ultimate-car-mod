package de.maxhenkel.car.integration.jei;

import de.maxhenkel.car.blocks.BlockPaint;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class PainterRecipeWrapperFactory implements IRecipeWrapperFactory<BlockPaint>{

	@Override
	public IRecipeWrapper getRecipeWrapper(BlockPaint recipe) {
		return new PainterRecipeWrapper(recipe);
	}

}
