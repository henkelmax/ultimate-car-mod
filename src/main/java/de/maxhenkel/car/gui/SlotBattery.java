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
    private PlayerEntity player;

    public SlotBattery(EntityCarBatteryBase car, int index, int xPosition, int yPosition, PlayerEntity player) {
        super(new Inventory(1), index, xPosition, yPosition);
        this.car = car;
        this.player = player;
    }

    @Override
    public void set(ItemStack stack) {
        if (!stack.getItem().equals(ModItems.BATTERY)) {
            return;
        }

        int energy = stack.getMaxDamage() - stack.getDamageValue();

        int energyToFill = car.getMaxBatteryLevel() - car.getBatteryLevel();

        int fill = Math.min(energy, energyToFill);

        stack.setDamageValue(stack.getMaxDamage() - (energy - fill));

        car.setBatteryLevel(car.getBatteryLevel() + fill);

        if (!player.inventory.add(stack)) {
            InventoryHelper.dropItemStack(car.level, car.getX(), car.getY(), car.getZ(), stack);
        }
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem().equals(ModItems.BATTERY);
    }

}
