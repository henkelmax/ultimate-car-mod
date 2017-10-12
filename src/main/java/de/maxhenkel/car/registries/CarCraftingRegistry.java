package de.maxhenkel.car.registries;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import de.maxhenkel.car.reciepe.ICarCraftingInventory;
import de.maxhenkel.car.reciepe.ICarRecipe;
import de.maxhenkel.car.reciepe.ICarbuilder;
import de.maxhenkel.car.reciepe.ShapedCarRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CarCraftingRegistry {
	
	public static final StringRegistry<ICarRecipe> REGISTRY=new StringRegistry<ICarRecipe>();
	
	public static ICarRecipe getRecipe(ICar car, Object... recipeComponents) {

		String pattern=new String();
		
		int offset=0;
		
		while (recipeComponents[offset] instanceof String) {
			pattern=pattern+recipeComponents[offset];
			
			offset++;
			
			if(offset>=recipeComponents.length){
				break;
			}
		}
		
		Map<Character, ItemStack> map=new HashMap<Character, ItemStack>();

		for(int i=offset; i<recipeComponents.length; i+=2){
			Character character = (Character) recipeComponents[i];
			ItemStack itemstack = null;

			if (recipeComponents[i + 1] instanceof Item) {
				itemstack = new ItemStack((Item) recipeComponents[i + 1]);
			} else if (recipeComponents[i + 1] instanceof Block) {
				itemstack = new ItemStack((Block) recipeComponents[i + 1]);
			} else if (recipeComponents[i + 1] instanceof ItemStack) {
				itemstack = (ItemStack) recipeComponents[i + 1];
			}
			
			if(itemstack!=null){
				map.put(character, itemstack);
			}
		}

		ItemStack[] stackPattern = new ItemStack[pattern.length()];

		for (int i=0; i<stackPattern.length; i++) {
			Character character = Character.valueOf(pattern.charAt(i));

			if (map.containsKey(character)) {
				stackPattern[i] = map.get(character).copy();
			} else {
				stackPattern[i] = null;
			}
		}

		ShapedCarRecipe recipe=new ShapedCarRecipe(stackPattern, car);
		return recipe;
	}

	@Nullable
	public static ICarbuilder findMatchingRecipe(ICarCraftingInventory craftMatrix) {
		for (ICarRecipe irecipe : REGISTRY) {
			if (irecipe.matches(craftMatrix)) {
				return irecipe.getCraftingResult().getBuilder();
			}
		}
		return null;
	}
	
}
