package de.maxhenkel.car.items;

import de.maxhenkel.car.gui.ContainerLicensePlate;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class ItemLicensePlate extends ItemCraftingComponent {

    public ItemLicensePlate(String name) {
        super(name);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        String text = getText(stack);

        if (!text.isEmpty()) {
            tooltip.add(new TranslationTextComponent("tooltip.license_plate_text", new StringTextComponent(text).func_240699_a_(TextFormatting.DARK_GRAY)).func_240699_a_(TextFormatting.GRAY));
        }

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (playerIn instanceof ServerPlayerEntity) {
            NetworkHooks.openGui((ServerPlayerEntity) playerIn, new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return ItemLicensePlate.this.getDisplayName(playerIn.getHeldItem(handIn));
                }

                @Nullable
                @Override
                public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                    return new ContainerLicensePlate(i, playerIn.getHeldItem(handIn));
                }
            });
        }
        return new ActionResult(ActionResultType.SUCCESS, stack);
    }

    public static void setText(ItemStack stack, String text) {
        CompoundNBT compound = stack.getOrCreateTag();

        compound.putString("plate_text", text);
    }

    public static String getText(ItemStack stack) {
        if (!stack.hasTag()) {
            return "";
        }
        CompoundNBT compound = stack.getTag();
        if (!compound.contains("plate_text")) {
            return "";
        }
        return compound.getString("plate_text");
    }
}
