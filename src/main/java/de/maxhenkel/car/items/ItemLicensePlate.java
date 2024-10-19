package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.gui.ContainerLicensePlate;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemLicensePlate extends ItemCraftingComponent {

    public ItemLicensePlate(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        String text = getText(stack);

        if (!text.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.license_plate_text", Component.literal(text).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY));
        }

        super.appendHoverText(stack, context, tooltip, flagIn);
    }

    @Override
    public InteractionResult use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (playerIn instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return ItemLicensePlate.this.getName(playerIn.getItemInHand(handIn));
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
                    return new ContainerLicensePlate(i, playerIn.getItemInHand(handIn));
                }
            });
        }
        return InteractionResult.SUCCESS;
    }

    public static void setText(ItemStack stack, String text) {
        stack.set(Main.LICENSE_PLATE_TEXT_DATA_COMPONENT, text);
    }

    @Nonnull
    public static String getText(ItemStack stack) {
        return stack.getOrDefault(Main.LICENSE_PLATE_TEXT_DATA_COMPONENT, "");
    }

}
