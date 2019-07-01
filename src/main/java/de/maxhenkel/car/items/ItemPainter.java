package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.BlockPaint;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.gui.SlotPainter;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.*;
import net.minecraft.world.World;

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
            int id;
            if (isYellow) {
                //id = GuiHandler.GUI_PAINTER_YELLOW;
            } else {
                // id = GuiHandler.GUI_PAINTER;
            }
            // TODO GUI
            //playerIn.openGui(Main.instance(), id, worldIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ());
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
