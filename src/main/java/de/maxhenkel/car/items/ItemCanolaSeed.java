package de.maxhenkel.car.items;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.corelib.item.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.neoforged.neoforge.common.SpecialPlantable;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

public class ItemCanolaSeed extends ItemNameBlockItem implements SpecialPlantable {

    public ItemCanolaSeed() {
        super(ModBlocks.CANOLA_CROP.get(), new Item.Properties());
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
        if (!(target instanceof Animal)) {
            return super.interactLivingEntity(stack, playerIn, target, hand);
        }

        Animal animal = (Animal) target;

        if (!animal.isFood(new ItemStack(Items.WHEAT_SEEDS))) {
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

    @Override
    public boolean canPlacePlantAtPosition(ItemStack itemStack, LevelReader level, BlockPos pos, @Nullable Direction direction) {
        return level.getBlockState(pos.below()).is(Tags.Blocks.VILLAGER_FARMLANDS);
    }

    @Override
    public void spawnPlantAtPosition(ItemStack itemStack, LevelReader levelReader, BlockPos pos, @Nullable Direction direction) {
        if (!(levelReader instanceof Level level)) {
            return;
        }
        level.setBlockAndUpdate(pos, ModBlocks.CANOLA_CROP.get().defaultBlockState());
    }

    @Override
    public boolean villagerCanPlantItem(Villager villager) {
        return true;
    }

}
