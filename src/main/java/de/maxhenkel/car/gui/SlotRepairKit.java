package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarDamageBase;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;

public class SlotRepairKit extends Slot {

    private EntityCarDamageBase car;
    private PlayerEntity player;

    public SlotRepairKit(EntityCarDamageBase car, int index, int xPosition, int yPosition, PlayerEntity player) {
        super(new Inventory(1), index, xPosition, yPosition);
        this.car = car;
        this.player = player;
    }

    @Override
    public void putStack(ItemStack stack) {
        if (!stack.getItem().equals(ModItems.REPAIR_KIT)) {
            return;
        }

        if (car.getDamage() >= 90) {

            stack.shrink(1);

            float damage = car.getDamage() - Main.SERVER_CONFIG.repairKitRepairAmount.get().floatValue();
            if (damage >= 0) {
                car.setDamage(damage);
            }
            ModSounds.playSound(ModSounds.RATCHET, car.world, car.func_233580_cy_(), null, SoundCategory.BLOCKS);
        }

        if (!player.inventory.addItemStackToInventory(stack)) {
            InventoryHelper.spawnItemStack(car.world, car.getPosX(), car.getPosY(), car.getPosZ(), stack);
        }
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem().equals(ModItems.REPAIR_KIT);
    }

}
