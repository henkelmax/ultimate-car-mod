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
        super(ModBlocks.GUARD_RAIL, new Item.Properties().tab(ModItemGroups.TAB_CAR));
        setRegistryName(ModBlocks.GUARD_RAIL.getRegistryName());
    }

    @Override
    public ActionResultType place(BlockItemUseContext context) {
        BlockItemUseContext bc = updatePlacementContext(context);
        if (bc == null) {
            return super.place(context);
        }

        World world = context.getLevel();
        Direction face = bc.getClickedFace();

        if (face.getStepY() != 0) {
            face = bc.getHorizontalDirection();
        }

        BlockState clickedBlock = world.getBlockState(bc.getClickedPos());

        if (clickedBlock.getBlock() != getBlock()) {
            return super.place(context);
        }

        BooleanProperty property = ModBlocks.GUARD_RAIL.getProperty(face);
        if (clickedBlock.getValue(property)) {
            return super.place(context);
        }

        BlockState place = clickedBlock.setValue(property, true);
        if (!placeBlock(bc, place)) {
            return ActionResultType.FAIL;
        }

        BlockPos blockpos = bc.getClickedPos();
        PlayerEntity playerentity = bc.getPlayer();
        ItemStack itemstack = bc.getItemInHand();
        BlockState placed = world.getBlockState(blockpos);
        Block block = placed.getBlock();
        if (block == place.getBlock()) {
            updateCustomBlockEntityTag(blockpos, world, playerentity, itemstack, placed);
            block.setPlacedBy(world, blockpos, placed, playerentity, itemstack);
            if (playerentity instanceof ServerPlayerEntity) {
                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerentity, blockpos, itemstack);
            }
        }

        SoundType soundtype = placed.getSoundType(world, blockpos, context.getPlayer());
        world.playSound(playerentity, blockpos, this.getPlaceSound(placed, world, blockpos, context.getPlayer()), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
        if (playerentity == null || !playerentity.abilities.instabuild) {
            itemstack.shrink(1);
        }

        return ActionResultType.sidedSuccess(world.isClientSide);
    }

}
