package de.maxhenkel.car.items;

import java.util.List;
import java.util.UUID;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.PredicateUUID;
import de.maxhenkel.car.entity.car.base.EntityCarLockBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemKey extends Item {

    public ItemKey() {
        super(new Item.Properties().stacksTo(1).tab(ModItemGroups.TAB_CAR));
        setRegistryName(new ResourceLocation(Main.MODID, "key"));
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        UUID carUUID = getCar(stack);

        if (carUUID == null) {
            if (worldIn.isClientSide) {
                playerIn.displayClientMessage(new TranslationTextComponent("message.key_no_car"), true);
            }
            return new ActionResult<>(ActionResultType.PASS, stack);
        } else if (worldIn.isClientSide) {
            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        }

        List<EntityCarLockBase> cars = worldIn.getEntitiesOfClass(EntityCarLockBase.class, new AxisAlignedBB(playerIn.getX() - 25D, playerIn.getY() - 25D, playerIn.getZ() - 25D, playerIn.getX() + 25D, playerIn.getY() + 25D, playerIn.getZ() + 25D), new PredicateUUID(carUUID));

        if (cars.isEmpty()) {
            playerIn.displayClientMessage(new TranslationTextComponent("message.car_out_of_range"), true);
            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        }

        EntityCarLockBase car = cars.get(0);

        if (car == null) {
            playerIn.sendMessage(new TranslationTextComponent("message.car_out_of_range"), Util.NIL_UUID);
            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        }

        car.setLocked(!car.isLocked(), true);

        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }

    public static void setCar(ItemStack stack, UUID carUUID) {
        if (!stack.hasTag()) {
            stack.setTag(new CompoundNBT());
        }

        CompoundNBT comp = stack.getTag();

        comp.putUUID("car", carUUID);
    }

    public static UUID getCar(ItemStack stack) {
        if (stack == null) {
            return null;
        }

        if (!stack.hasTag()) {
            return null;
        }

        CompoundNBT comp = stack.getTag();

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
