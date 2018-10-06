package de.maxhenkel.car.integration.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import de.maxhenkel.car.blocks.BlockPaint;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class PainterRecipeWrapper implements IRecipeWrapper{

	private BlockPaint paint;
	
	public PainterRecipeWrapper(BlockPaint paint) {
		this.paint=paint;
	}
	
	@Override
	public void drawInfo(Minecraft arg0, int arg1, int arg2, int arg3, int arg4) {
		
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, new ItemStack(ModItems.PAINTER));
		
		List<ItemStack> list=new ArrayList<ItemStack>();
		
		for(BlockPaint b:ModBlocks.PAINTS){
			list.add(new ItemStack(b));
		}
		
		ingredients.setOutputs(VanillaTypes.ITEM, list);
	}

	@Override
	public List<String> getTooltipStrings(int arg0, int arg1) {
		return Collections.emptyList();
	}

	@Override
	public boolean handleClick(Minecraft arg0, int arg1, int arg2, int arg3) {
		return false;
	}

	public BlockPaint getPaint() {
		return paint;
	}


}
