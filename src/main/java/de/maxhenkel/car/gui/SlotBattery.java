package de.maxhenkel.car.gui;

import de.maxhenkel.car.entity.car.base.EntityCarBatteryBase;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotBattery extends Slot {

    private EntityCarBatteryBase car;

    public SlotBattery(EntityCarBatteryBase car, int index, int xPosition, int yPosition) {
        super(new Inventory(1), index, xPosition, yPosition);
        this.car = car;
    }

    @Override
    public void putStack(ItemStack stack) {
        if (stack == null) {
            return;
        }

        if (!stack.getItem().equals(ModItems.BATTERY)) {
            return;
        }

        int energy = stack.getMaxDamage() - stack.getDamage();

        int energyToFill = car.getMaxBatteryLevel() - car.getBatteryLevel();

        int fill = Math.min(energy, energyToFill);

        stack.setDamage(stack.getMaxDamage() - (energy - fill));

        car.setBatteryLevel(car.getBatteryLevel() + fill);

        PlayerEntity player = car.getDriver();

        if (player == null) {
            InventoryHelper.spawnItemStack(car.world, car.posX, car.posY, car.posZ, stack);
            return;
        }

        if (!player.inventory.addItemStackToInventory(stack)) {
            InventoryHelper.spawnItemStack(car.world, car.posX, car.posY, car.posZ, stack);
        }
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem().equals(ModItems.BATTERY);
    }

}
