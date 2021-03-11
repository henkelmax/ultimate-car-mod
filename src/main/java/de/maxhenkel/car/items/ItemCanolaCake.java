package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.corelib.item.ItemUtils;
import de.maxhenkel.car.ModItemGroups;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;

public class ItemCanolaCake extends Item {

    public ItemCanolaCake() {
        super(new Item.Properties().tab(ModItemGroups.TAB_CAR));
        setRegistryName(new ResourceLocation(Main.MODID, "canola_cake"));

    }

    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (!(target instanceof AnimalEntity)) {
            return super.interactLivingEntity(stack, playerIn, target, hand);
        }

        AnimalEntity animal = (AnimalEntity) target;

        if (!animal.isFood(new ItemStack(Items.WHEAT))) {
            return super.interactLivingEntity(stack, playerIn, target, hand);
        }

        if (animal.getAge() == 0 && !animal.isInLove()) {
            ItemUtils.decrItemStack(stack, playerIn);
            animal.setInLove(playerIn);
            return ActionResultType.CONSUME;
        }

        if (animal.isBaby()) {
            ItemUtils.decrItemStack(stack, playerIn);
            animal.ageUp((int) ((float) (-animal.getAge() / 20) * 0.1F), true);
            return ActionResultType.CONSUME;
        }
        return super.interactLivingEntity(stack, playerIn, target, hand);
    }

}
