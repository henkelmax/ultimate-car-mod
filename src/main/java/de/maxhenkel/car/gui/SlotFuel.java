package de.maxhenkel.car.gui;

import de.maxhenkel.car.entity.car.base.EntityCarFuelBase;
import de.maxhenkel.car.items.ItemCanister;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

public class SlotFuel extends Slot {

    private EntityCarFuelBase car;
    private PlayerEntity player;

    public SlotFuel(EntityCarFuelBase car, int index, int xPosition, int yPosition, PlayerEntity player) {
        super(new Inventory(1), index, xPosition, yPosition);
        this.car = car;
        this.player = player;
    }

    @Override
    public void putStack(ItemStack stack) {
        if (stack == null) {
            return;
        }

        if (!stack.getItem().equals(ModItems.CANISTER)) {
            return;
        }

        boolean success = ItemCanister.fuelFluidHandler(stack, car);

        if (success) {
            ModSounds.playSound(SoundEvents.BLOCK_BREWING_STAND_BREW, car.world, car.getPosition(), null, SoundCategory.NEUTRAL);
        }

        if (!player.inventory.addItemStackToInventory(stack)) {
            InventoryHelper.spawnItemStack(car.world, car.posX, car.posY, car.posZ, stack);
        }
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem().equals(ModItems.CANISTER);
    }

}
