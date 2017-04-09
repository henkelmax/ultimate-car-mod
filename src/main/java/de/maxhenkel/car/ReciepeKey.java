package de.maxhenkel.car;

import java.util.UUID;
import de.maxhenkel.car.items.ItemKey;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class ReciepeKey implements IRecipe{

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		return getCraftingResult(inv)!=null;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack key=null;
		ItemStack iron=null;
		
		for(int i=0; i<inv.getSizeInventory(); i++){
			ItemStack stack=inv.getStackInSlot(i);
			
			if(ItemTools.isStackEmpty(stack)){
				continue;
			}
			
			if(stack.getItem().equals(ModItems.KEY)){
				if(key!=null){
					return null;
				}
				key=stack;
			}else if(stack.getItem().equals(Items.IRON_INGOT)){
				if(iron!=null){
					return null;
				}
				iron=stack;
			}
		}
		
		if(key==null||iron==null){
			return null;
		}
		
		UUID uuid=ItemKey.getCar(key);
		
		if(uuid==null){
			return new ItemStack(ModItems.KEY);
		}
		
		ItemStack out=ItemKey.getKeyForCar(uuid);
		
		return out;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(ModItems.KEY);
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		
		for(int i=0; i<inv.getSizeInventory(); i++){
			ItemStack stack=inv.getStackInSlot(i);
			
			if(ItemTools.isStackEmpty(stack)){
				continue;
			}
			
			if(stack.getItem().equals(Items.IRON_INGOT)){
				ItemStack s=ItemTools.decrItemStack(stack, null);
				inv.setInventorySlotContents(i, s);
			}
		}
		
		return new ItemStack[0];
	}

}
