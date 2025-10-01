package de.maxhenkel.car.gui;

import de.maxhenkel.car.entity.car.base.EntityCarFuelBase;
import de.maxhenkel.car.items.ItemCanister;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.access.ItemAccess;

public class SlotFuel extends Slot {

    private EntityCarFuelBase car;
    private Player player;

    public SlotFuel(EntityCarFuelBase car, int index, int xPosition, int yPosition, Player player) {
        super(new SimpleContainer(1), index, xPosition, yPosition);
        this.car = car;
        this.player = player;
    }

    @Override
    public void set(ItemStack stack) {
        if (!stack.getItem().equals(ModItems.CANISTER.get())) {
            return;
        }
        boolean success = ItemCanister.fuelFluidHandler(ItemAccess.forStack(stack), car);

        if (success) {
            ModSounds.playSound(SoundEvents.BREWING_STAND_BREW, car.level(), car.blockPosition(), null, SoundSource.MASTER);
        }

        if (!player.getInventory().add(stack)) {
            Containers.dropItemStack(car.level(), car.getX(), car.getY(), car.getZ(), stack);
        }
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem().equals(ModItems.CANISTER.get());
    }

}
