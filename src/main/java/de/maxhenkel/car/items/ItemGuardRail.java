package de.maxhenkel.car.items;

import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ItemGuardRail extends BlockItem {

    public ItemGuardRail() {
        super(ModBlocks.GUARD_RAIL.get(), new Item.Properties());
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        BlockPlaceContext bc = updatePlacementContext(context);
        if (bc == null) {
            return super.place(context);
        }

        Level world = context.getLevel();
        Direction face = bc.getClickedFace();

        if (face.getStepY() != 0) {
            face = bc.getHorizontalDirection();
        }

        BlockState clickedBlock = world.getBlockState(bc.getClickedPos());

        if (clickedBlock.getBlock() != getBlock()) {
            return super.place(context);
        }

        BooleanProperty property = ModBlocks.GUARD_RAIL.get().getProperty(face);
        if (clickedBlock.getValue(property)) {
            return super.place(context);
        }

        BlockState place = clickedBlock.setValue(property, true);
        if (!placeBlock(bc, place)) {
            return InteractionResult.FAIL;
        }

        BlockPos blockpos = bc.getClickedPos();
        Player playerentity = bc.getPlayer();
        ItemStack itemstack = bc.getItemInHand();
        BlockState placed = world.getBlockState(blockpos);
        Block block = placed.getBlock();
        if (block == place.getBlock()) {
            updateCustomBlockEntityTag(blockpos, world, playerentity, itemstack, placed);
            block.setPlacedBy(world, blockpos, placed, playerentity, itemstack);
            if (playerentity instanceof ServerPlayer) {
                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) playerentity, blockpos, itemstack);
            }
        }

        SoundType soundtype = placed.getSoundType(world, blockpos, context.getPlayer());
        world.playSound(playerentity, blockpos, this.getPlaceSound(placed, world, blockpos, context.getPlayer()), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
        if (playerentity == null || !playerentity.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResult.sidedSuccess(world.isClientSide);
    }

}
