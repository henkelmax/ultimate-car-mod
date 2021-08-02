package de.maxhenkel.car.gui;

import de.maxhenkel.car.entity.car.base.EntityCarBatteryBase;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotBattery extends Slot {

    private EntityCarBatteryBase car;
    private Player player;

    public SlotBattery(EntityCarBatteryBase car, int index, int xPosition, int yPosition, Player player) {
        super(new SimpleContainer(1), index, xPosition, yPosition);
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

        if (!player.getInventory().add(stack)) {
            Containers.dropItemStack(car.level, car.getX(), car.getY(), car.getZ(), stack);
        }
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem().equals(ModItems.BATTERY);
    }

}
