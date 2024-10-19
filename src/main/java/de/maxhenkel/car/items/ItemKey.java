package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.PredicateUUID;
import de.maxhenkel.car.entity.car.base.EntityCarLockBase;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.UUID;

public class ItemKey extends Item {

    public ItemKey(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResult use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        UUID carUUID = getCar(stack);

        if (carUUID == null) {
            if (worldIn.isClientSide) {
                playerIn.displayClientMessage(Component.translatable("message.key_no_car"), true);
            }
            return InteractionResult.PASS;
        } else if (worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        List<EntityCarLockBase> cars = worldIn.getEntitiesOfClass(EntityCarLockBase.class, new AABB(playerIn.getX() - 25D, playerIn.getY() - 25D, playerIn.getZ() - 25D, playerIn.getX() + 25D, playerIn.getY() + 25D, playerIn.getZ() + 25D), new PredicateUUID(carUUID));

        if (cars.isEmpty()) {
            playerIn.displayClientMessage(Component.translatable("message.car_out_of_range"), true);
            return InteractionResult.SUCCESS;
        }

        EntityCarLockBase car = cars.get(0);

        if (car.getPassengers().stream().anyMatch(entity -> entity == playerIn)) {
            return InteractionResult.SUCCESS;
        }

        car.setLocked(!car.isLocked(), true);

        return InteractionResult.SUCCESS;
    }

    public static void setCar(ItemStack stack, UUID carUUID) {
        stack.set(Main.CAR_UUID_DATA_COMPONENT, carUUID);
    }

    public static UUID getCar(ItemStack stack) {
        return stack.get(Main.CAR_UUID_DATA_COMPONENT);
    }

    public static ItemStack getKeyForCar(UUID car) {
        ItemStack stack = new ItemStack(ModItems.KEY.get());

        setCar(stack, car);

        return stack;
    }

}
