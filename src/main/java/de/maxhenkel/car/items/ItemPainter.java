package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.BlockPaint;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.gui.ContainerPainter;
import de.maxhenkel.car.gui.SlotPainter;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPainter extends Item {

    private boolean isYellow;

    public ItemPainter(boolean isYellow) {
        super(new Item.Properties().stacksTo(1).durability(1024).tab(ModItemGroups.TAB_CAR));
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
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (playerIn.isShiftKeyDown()) {
            if (playerIn instanceof ServerPlayer) {
                NetworkHooks.openGui((ServerPlayer) playerIn, new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return ItemPainter.this.getName(playerIn.getItemInHand(handIn));
                    }

                    @Nullable
                    @Override
                    public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
                        return new ContainerPainter(i, playerInventory, isYellow);
                    }
                }, packetBuffer -> packetBuffer.writeBoolean(isYellow));
            }
        }
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer().isShiftKeyDown()) {
            return InteractionResult.PASS;
        }

        if (!context.getClickedFace().equals(Direction.UP)) {
            return InteractionResult.FAIL;
        }

        if (!BlockPaint.canPlaceBlockAt(context.getLevel(), context.getClickedPos().above())) {
            return InteractionResult.FAIL;
        }

        if (!context.getLevel().isEmptyBlock(context.getClickedPos().above())) {
            return InteractionResult.FAIL;
        }

        ItemStack stack1 = SlotPainter.getPainterStack(context.getPlayer());

        if (stack1.isEmpty()) {
            return InteractionResult.FAIL;
        }

        BlockPaint block = getSelectedPaint(SlotPainter.getPainterID(stack1));

        if (block == null) {
            return InteractionResult.FAIL;
        }

        BlockState state = block.defaultBlockState().setValue(BlockPaint.FACING, context.getPlayer().getDirection());

        context.getLevel().setBlockAndUpdate(context.getClickedPos().above(), state);

        stack1.hurtAndBreak(1, context.getPlayer(), playerEntity -> playerEntity.broadcastBreakEvent(context.getHand()));

        return InteractionResult.SUCCESS;
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
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> textComponents, TooltipFlag tooltipFlag) {
        BlockPaint paint = getSelectedPaint(SlotPainter.getPainterID(stack));
        if (paint != null) {
            textComponents.add(new TranslatableComponent("tooltip.painter", new TranslatableComponent(paint.getDescriptionId()).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, world, textComponents, tooltipFlag);
    }

}
