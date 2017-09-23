package de.maxhenkel.car.integration.jei;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import de.maxhenkel.tools.MathTools;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.reciepe.ICarRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CarRecipeWrapper implements IRecipeWrapper{

	private ICarRecipe recipe;
	private EntityCarBase car;
	private float rotoation;
	
	public CarRecipeWrapper(ICarRecipe recipe) {
		this.recipe=recipe;
		this.car=recipe.getCraftingResult().build(Minecraft.getMinecraft().theWorld);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		MathTools.drawCarOnScreen(recipeWidth-30, recipeHeight-54/4, 18, rotoation, car);
		
		float parts = Minecraft.getMinecraft().getRenderPartialTicks();
		rotoation += parts / 4;
		if (!(rotoation < 360)) {
			rotoation = 0F;
		}
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, Arrays.asList(recipe.getInputs()));
	}

	@Override
	public List<String> getTooltipStrings(int arg0, int arg1) {
		return Collections.emptyList();
	}

	@Override
	public boolean handleClick(Minecraft arg0, int arg1, int arg2, int arg3) {
		return false;
	}

	public ICarRecipe getRecipe() {
		return recipe;
	}

	@Override
	public List<ItemStack> getInputs() {
		return Arrays.asList(recipe.getInputs());
	}

	@Override
	public List<ItemStack> getOutputs() {
		return Collections.emptyList();
	}

	@Override
	public List<FluidStack> getFluidInputs() {
		return Collections.emptyList();
	}

	@Override
	public List<FluidStack> getFluidOutputs() {
		return Collections.emptyList();
	}

	@Override
	public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {
		
	}

}
