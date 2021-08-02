package de.maxhenkel.car.items;

import de.maxhenkel.car.gui.ContainerLicensePlate;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemLicensePlate extends ItemCraftingComponent {

    public ItemLicensePlate(String name) {
        super(name);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        String text = getText(stack);

        if (!text.isEmpty()) {
            tooltip.add(new TranslatableComponent("tooltip.license_plate_text", new TextComponent(text).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY));
        }

        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (playerIn instanceof ServerPlayer) {
            NetworkHooks.openGui((ServerPlayer) playerIn, new MenuProvider() {
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
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    public static void setText(ItemStack stack, String text) {
        CompoundTag compound = stack.getOrCreateTag();

        compound.putString("plate_text", text);
    }

    @Nonnull
    public static String getText(ItemStack stack) {
        if (!stack.hasTag()) {
            return "";
        }
        CompoundTag compound = stack.getTag();
        if (!compound.contains("plate_text")) {
            return "";
        }
        return compound.getString("plate_text");
    }

}
