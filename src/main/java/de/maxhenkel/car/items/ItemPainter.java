package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.BlockPaint;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.gui.ContainerPainter;
import de.maxhenkel.car.gui.SlotPainter;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class ItemPainter extends Item {

    private boolean isYellow;

    public ItemPainter(boolean isYellow) {
        super(new Item.Properties().maxStackSize(1).maxDamage(1024).group(ModCreativeTabs.TAB_CAR));
        setRegistryName(new ResourceLocation(Main.MODID, "painter" + (isYellow ? "_yellow" : "")));
        this.isYellow = isYellow;

    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.getBlock() instanceof BlockPaint) {
            return 50F;
        }
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (playerIn.isSneaking()) {
            if (playerIn instanceof ServerPlayerEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) playerIn, new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return ItemPainter.this.getDisplayName(playerIn.getHeldItem(handIn));
                    }

                    @Nullable
                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new ContainerPainter(i, playerInventory, isYellow);
                    }
                }, packetBuffer -> {
                    packetBuffer.writeBoolean(isYellow);
                });
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getPlayer().isSneaking()) {
            return ActionResultType.PASS;
        }

        if (!context.getFace().equals(Direction.UP)) {
            return ActionResultType.FAIL;
        }

        if (!BlockPaint.canPlaceBlockAt(context.getWorld(), context.getPos().up())) {
            return ActionResultType.FAIL;
        }

        if (!context.getWorld().isAirBlock(context.getPos().up())) {
            return ActionResultType.FAIL;
        }

        int index = SlotPainter.getPainterID(context.getPlayer());
        ItemStack stack1 = SlotPainter.getPainterStack(context.getPlayer());

        if (stack1 == null) {
            return ActionResultType.FAIL;
        }

        if (index < 0 || index >= ModBlocks.PAINTS.length) {
            return ActionResultType.FAIL;
        }

        BlockPaint block;

        if (isYellow) {
            block = ModBlocks.YELLOW_PAINTS[index];
        } else {
            block = ModBlocks.PAINTS[index];
        }


        BlockState state = block.getDefaultState().with(BlockPaint.FACING, context.getPlayer().getHorizontalFacing());

        context.getWorld().setBlockState(context.getPos().up(), state);

        stack1.damageItem(1, context.getPlayer(), playerEntity -> {
        });

        return ActionResultType.SUCCESS;
    }

}
