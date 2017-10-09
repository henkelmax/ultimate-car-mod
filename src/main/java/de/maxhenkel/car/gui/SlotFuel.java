package de.maxhenkel.car.gui;

import de.maxhenkel.car.entity.car.base.EntityCarFuelBase;
import de.maxhenkel.car.items.ItemCanister;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;

public class SlotFuel extends Slot{
	
	private EntityCarFuelBase car;
	
	public SlotFuel(EntityCarFuelBase car, int index, int xPosition, int yPosition) {
		super(new InventoryBasic("", false, 1), index, xPosition, yPosition);
		this.car=car;
	}
	
	@Override
	public void putStack(ItemStack stack) {
		if(stack==null){
			return;
		}
		
		if(!stack.getItem().equals(ModItems.CANISTER)){
			return;
		}
		
		boolean success=ItemCanister.fuelFluidHandler(stack, car);
		
		if(success){
			ModSounds.playSound(SoundEvents.BLOCK_BREWING_STAND_BREW, car.world, car.getPosition(), null, SoundCategory.NEUTRAL);
		}
		
		EntityPlayer player=car.getDriver();
		
		if(player==null){
			InventoryHelper.spawnItemStack(car.world, car.posX, car.posY, car.posZ, stack);
			return;
		}
		
		if(!player.inventory.addItemStackToInventory(stack)){
			InventoryHelper.spawnItemStack(car.world, car.posX, car.posY, car.posZ, stack);
		}
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem().equals(ModItems.CANISTER);
	}
	
}
