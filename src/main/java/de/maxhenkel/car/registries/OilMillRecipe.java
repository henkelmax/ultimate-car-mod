package de.maxhenkel.car.registries;

import de.maxhenkel.tools.ItemStackSelector;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

@Deprecated
public class OilMillRecipe extends IForgeRegistryEntry.Impl<OilMillRecipe>{

	public static final IForgeRegistry<OilMillRecipe> REGISTRY=new RegistryBuilder<OilMillRecipe>().create();
	
	private ItemStackSelector input;
	private ItemStackSelector output;
	private int outputAmount;
	
	public OilMillRecipe(ItemStack input, ItemStack output, int outputAmount) {
		this.input=new ItemStackSelector(input.getItem(), input.getMetadata());
		this.output=new ItemStackSelector(output.getItem(), output.getMetadata());
		this.outputAmount=outputAmount;
	}
	
	public OilMillRecipe(Item input, ItemStack output, int outputAmount) {
		this.input=new ItemStackSelector(input);
		this.output=new ItemStackSelector(output.getItem(), output.getMetadata());
		this.outputAmount=outputAmount;
	}
	
	public OilMillRecipe(ItemStackSelector input, ItemStackSelector output, int outputAmount) {
		this.input=input;
		this.output=output;
		this.outputAmount=outputAmount;
	}
	
	public ItemStackSelector getInput() {
		return input;
	}

	public ItemStackSelector getOutput() {
		return output;
	}

	public int getOutputAmount() {
		return outputAmount;
	}

}
