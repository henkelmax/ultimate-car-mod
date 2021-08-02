package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.corelib.item.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class ItemCanolaSeed extends ItemNameBlockItem implements IPlantable {

    public ItemCanolaSeed() {
        super(ModBlocks.CANOLA_CROP, new Item.Properties().tab(ModItemGroups.TAB_CAR));
        setRegistryName(new ResourceLocation(Main.MODID, "canola_seeds"));
    }

    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        return ModBlocks.CANOLA_CROP.defaultBlockState();
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.CROP;
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

}
