package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.gui.GuiHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemLicensePlate extends ItemCraftingComponent {

    public ItemLicensePlate(String name) {
        super(name);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        String text = getText(stack);

        if (text != null && !text.isEmpty()) {
            tooltip.add(new TranslationTextComponent("tooltip.license_plate_text", text));
        }

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        // TODO GUI
        // playerIn.openGui(Main.MODID, GuiHandler.GUI_NUMBER_PLATE, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
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
