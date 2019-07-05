package de.maxhenkel.car.integration.jei;

import de.maxhenkel.car.blocks.BlockPaint;
import net.minecraft.item.Item;

public class PainterRecipe {

	private Item input;
	private BlockPaint output;
	
	public PainterRecipe(Item input, BlockPaint output) {
		this.input=input;
		this.output=output;
	}

	public Item getInput() {
		return input;
	}

	public BlockPaint getOutput() {
		return output;
	}
}
