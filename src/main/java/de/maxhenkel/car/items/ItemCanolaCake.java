package de.maxhenkel.car.items;

import de.maxhenkel.corelib.item.ItemUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ItemCanolaCake extends Item {

    public ItemCanolaCake() {
        super(new Item.Properties()/*.tab(ModItemGroups.TAB_CAR)*/); // TODO Fix creative tab
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
        if (!(target instanceof Animal)) {
            return super.interactLivingEntity(stack, playerIn, target, hand);
        }

        Animal animal = (Animal) target;

        if (!animal.isFood(new ItemStack(Items.WHEAT))) {
            return super.interactLivingEntity(stack, playerIn, target, hand);
        }

        if (animal.getAge() == 0 && !animal.isInLove()) {
            ItemUtils.decrItemStack(stack, playerIn);
            animal.setInLove(playerIn);
            return InteractionResult.CONSUME;
        }

        if (animal.isBaby()) {
            ItemUtils.decrItemStack(stack, playerIn);
            animal.ageUp((int) ((float) (-animal.getAge() / 20) * 0.1F), true);
            return InteractionResult.CONSUME;
        }
        return super.interactLivingEntity(stack, playerIn, target, hand);
    }

}
