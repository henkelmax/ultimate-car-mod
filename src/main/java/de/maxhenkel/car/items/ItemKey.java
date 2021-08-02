package de.maxhenkel.car.items;

import java.util.List;
import java.util.UUID;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.PredicateUUID;
import de.maxhenkel.car.entity.car.base.EntityCarLockBase;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class ItemKey extends Item {

    public ItemKey() {
        super(new Item.Properties().stacksTo(1).tab(ModItemGroups.TAB_CAR));
        setRegistryName(new ResourceLocation(Main.MODID, "key"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        UUID carUUID = getCar(stack);

        if (carUUID == null) {
            if (worldIn.isClientSide) {
                playerIn.displayClientMessage(new TranslatableComponent("message.key_no_car"), true);
            }
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        } else if (worldIn.isClientSide) {
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
        }

        List<EntityCarLockBase> cars = worldIn.getEntitiesOfClass(EntityCarLockBase.class, new AABB(playerIn.getX() - 25D, playerIn.getY() - 25D, playerIn.getZ() - 25D, playerIn.getX() + 25D, playerIn.getY() + 25D, playerIn.getZ() + 25D), new PredicateUUID(carUUID));

        if (cars.isEmpty()) {
            playerIn.displayClientMessage(new TranslatableComponent("message.car_out_of_range"), true);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
        }

        EntityCarLockBase car = cars.get(0);

        if (car == null) {
            playerIn.sendMessage(new TranslatableComponent("message.car_out_of_range"), Util.NIL_UUID);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
        }

        car.setLocked(!car.isLocked(), true);

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    public static void setCar(ItemStack stack, UUID carUUID) {
        if (!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }

        CompoundTag comp = stack.getTag();

        comp.putUUID("car", carUUID);
    }

    public static UUID getCar(ItemStack stack) {
        if (stack == null) {
            return null;
        }

        if (!stack.hasTag()) {
            return null;
        }

        CompoundTag comp = stack.getTag();

        if (comp == null) {
            return null;
        }

        return comp.getUUID("car");
    }

    public static ItemStack getKeyForCar(UUID car) {
        ItemStack stack = new ItemStack(ModItems.KEY);

        setCar(stack, car);

        return stack;
    }

}
