package de.maxhenkel.car.integration.jei;

import de.maxhenkel.tools.ItemTools;
import de.maxhenkel.car.Main;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class CarRecipeCategory implements IRecipeCategory<CarRecipeWrapper>{

	private IGuiHelper helper;
	
	public CarRecipeCategory(IGuiHelper helper) {
		this.helper=helper;
	}
	
	@Override
	public IDrawable getBackground() {
		return helper.createDrawable(new ResourceLocation(Main.MODID,
				"textures/gui/jei_car_workshop_crafting.png"), 0, 0, 175, 54);
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("tile.car_workshop.name").getFormattedText();
	}

	@Override
	public String getUid() {
		return JEIPlugin.CATEGORY_CAR_WORKSHOP;
	}

	@Override
	public void setRecipe(IRecipeLayout layout, CarRecipeWrapper wrapper, IIngredients ingredients) {
		IGuiItemStackGroup group = layout.getItemStacks();

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 5; x++) {
				 group.init(x + y * 5, true,  x * 18,  y * 18);
				 ItemStack stack=wrapper.getRecipe().getInputs()[x + y * 5];
				 if(!ItemTools.isStackEmpty(stack)){
					 group.set(x + y * 5, stack);
				 }
			}
		}
	}

	@Override
	public String getModName() {
		return Main.MODID;
	}

	
}
