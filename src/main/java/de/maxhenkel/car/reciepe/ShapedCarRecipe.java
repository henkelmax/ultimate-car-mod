package de.maxhenkel.car.reciepe;

import de.maxhenkel.car.ItemTools;
import net.minecraft.item.ItemStack;

public class ShapedCarRecipe implements ICarRecipe{

	private ItemStack[] pattern;
	private ICarbuilder result;
	
	public ShapedCarRecipe(ItemStack[] pattern, ICarbuilder result) {
		this.pattern=pattern;
		this.result=result;
	}
	
	@Override
	public ItemStack[] getInputs() {
		ItemStack[] ret=new ItemStack[pattern.length];
		for(int i=0; i<pattern.length; i++){
			if(pattern[i]!=null){
				ret[i]=pattern[i].copy();
			}
		}
		return ret;
	}
	
	@Override
	public boolean matches(ICarCraftingInventory inv) {
		if(inv.getSizeInventory()<pattern.length){
			return false;
		}
		
		for(int i=0; i<pattern.length; i++){
			if(!ItemTools.areItemsEqualWithEmpty(inv.getStackInSlot(i), pattern[i])){
				return false;
			}
		}
		
		return true;
	}

	@Override
	public ICarbuilder getCraftingResult() {
		return result;
	}

	@Override
	public String getName() {
		return result.getName();
	}

}
