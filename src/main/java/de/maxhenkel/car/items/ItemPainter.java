package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.BlockPaint;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.gui.ContainerPainter;
import de.maxhenkel.car.gui.SlotPainter;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPainter extends Item {

    private boolean isYellow;

    public ItemPainter(boolean isYellow) {
        super(new Item.Properties().maxStackSize(1).maxDamage(1024).group(ModItemGroups.TAB_CAR));
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

        ItemStack stack1 = SlotPainter.getPainterStack(context.getPlayer());

        if (stack1 == null) {
            return ActionResultType.FAIL;
        }

        BlockPaint block = getSelectedPaint(SlotPainter.getPainterID(stack1));

        if (block == null) {
            return ActionResultType.FAIL;
        }

        BlockState state = block.getDefaultState().with(BlockPaint.FACING, context.getPlayer().getHorizontalFacing());

        context.getWorld().setBlockState(context.getPos().up(), state);

        if (context.getWorld().isRemote) {
            return ActionResultType.SUCCESS;
        }

        stack1.damageItem(1, context.getPlayer(), playerEntity -> {
            playerEntity.sendBreakAnimation(context.getHand());
        });

        return ActionResultType.SUCCESS;
    }

    private BlockPaint getSelectedPaint(int id) {
        if (id < 0 || id >= ModBlocks.PAINTS.length) {
            return null;
        }

        BlockPaint block;

        if (isYellow) {
            block = ModBlocks.YELLOW_PAINTS[id];
        } else {
            block = ModBlocks.PAINTS[id];
        }
        return block;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> textComponents, ITooltipFlag tooltipFlag) {
        BlockPaint paint = getSelectedPaint(SlotPainter.getPainterID(stack));
        if (paint != null) {
            textComponents.add(new TranslationTextComponent("tooltip.painter", new TranslationTextComponent(paint.getTranslationKey()).func_240699_a_(TextFormatting.DARK_GRAY)).func_240699_a_(TextFormatting.GRAY));
        }
        super.addInformation(stack, world, textComponents, tooltipFlag);
    }
}
