package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.tools.ItemTools;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class ItemCanolaSeed extends BlockNamedItem implements IPlantable {

    public ItemCanolaSeed() {
        super(ModBlocks.CANOLA_CROP, new Item.Properties().group(ModItemGroups.TAB_CAR));
        setRegistryName(new ResourceLocation(Main.MODID, "canola_seeds"));
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        return ModBlocks.CANOLA_CROP.getDefaultState();
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.CROP;
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (!(target instanceof AnimalEntity)) {
            return super.itemInteractionForEntity(stack, playerIn, target, hand);
        }

        AnimalEntity animal = (AnimalEntity) target;

        if (!animal.isBreedingItem(new ItemStack(Items.WHEAT_SEEDS))) {
            return super.itemInteractionForEntity(stack, playerIn, target, hand);
        }

        if (animal.getGrowingAge() == 0 && !animal.isInLove()) {
            ItemTools.decrItemStack(stack, playerIn);
            animal.setInLove(playerIn);
            return ActionResultType.CONSUME;
        }

        if (animal.isChild()) {
            ItemTools.decrItemStack(stack, playerIn);
            animal.ageUp((int) ((float) (-animal.getGrowingAge() / 20) * 0.1F), true);
            return ActionResultType.CONSUME;
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}
