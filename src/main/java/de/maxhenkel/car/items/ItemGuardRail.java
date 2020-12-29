package de.maxhenkel.car.items;

import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemGuardRail extends BlockItem {

    public ItemGuardRail() {
        super(ModBlocks.GUARD_RAIL, new Item.Properties().group(ModItemGroups.TAB_CAR));
        setRegistryName(ModBlocks.GUARD_RAIL.getRegistryName());
    }

    @Override
    public ActionResultType tryPlace(BlockItemUseContext context) {
        BlockItemUseContext bc = getBlockItemUseContext(context);
        if (bc == null) {
            return super.tryPlace(context);
        }

        World world = context.getWorld();
        Direction face = bc.getFace();

        if (face.getYOffset() != 0) {
            face = bc.getPlacementHorizontalFacing();
        }

        BlockState clickedBlock = world.getBlockState(bc.getPos());

        if (clickedBlock.getBlock() != getBlock()) {
            return super.tryPlace(context);
        }

        BooleanProperty property = ModBlocks.GUARD_RAIL.getProperty(face);
        if (clickedBlock.get(property)) {
            return super.tryPlace(context);
        }

        BlockState place = clickedBlock.with(property, true);
        if (!placeBlock(bc, place)) {
            return ActionResultType.FAIL;
        }

        BlockPos blockpos = bc.getPos();
        PlayerEntity playerentity = bc.getPlayer();
        ItemStack itemstack = bc.getItem();
        BlockState placed = world.getBlockState(blockpos);
        Block block = placed.getBlock();
        if (block == place.getBlock()) {
            onBlockPlaced(blockpos, world, playerentity, itemstack, placed);
            block.onBlockPlacedBy(world, blockpos, placed, playerentity, itemstack);
            if (playerentity instanceof ServerPlayerEntity) {
                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerentity, blockpos, itemstack);
            }
        }

        SoundType soundtype = placed.getSoundType(world, blockpos, context.getPlayer());
        world.playSound(playerentity, blockpos, this.getPlaceSound(placed, world, blockpos, context.getPlayer()), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
        if (playerentity == null || !playerentity.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }

        return ActionResultType.func_233537_a_(world.isRemote);
    }

}
