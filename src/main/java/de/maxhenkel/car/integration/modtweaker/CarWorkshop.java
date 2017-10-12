package de.maxhenkel.car.integration.modtweaker;

import java.util.UUID;
import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.reciepe.ShapedCarRecipe;
import de.maxhenkel.car.registries.CarCraftingRegistry;
import de.maxhenkel.car.registries.CarRegistry;
import de.maxhenkel.car.registries.ICar;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.car.CarWorkshop")
@ModOnly(Main.MODID)
@ZenRegister
public class CarWorkshop {

	@ZenMethod
	public static void addRecipe(String carID, IItemStack[][] input/* , @Optional IItemStack secondaryOutput */) {
		ICar car=CarRegistry.REGISTRY.getEntry(carID);
		if(car==null) {
			throw new IllegalArgumentException("Car with ID '" +carID +"' not found");
		}
		
		if(input.length!=3||input[0].length!=5) {
			throw new IllegalArgumentException("Wrong pattern size");
		}
		
		ItemStack[] stacks=new ItemStack[3*5];
		int index=0;
		for(int i=0; i<input.length; i++) {
			for(int j=0; j<input[i].length; j++) {
				stacks[index]=InputHelper.toStack(input[i][j]);
				index++;
			}
		}
		
		String s=UUID.randomUUID().toString();
		CarCraftingRegistry.REGISTRY.register(s, new ShapedCarRecipe(stacks, car));
	}

	@ZenMethod
	public static void removeRecipe(String carID) {
		CarCraftingRegistry.REGISTRY.unregister(carID);
	}

}
