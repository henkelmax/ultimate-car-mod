package de.maxhenkel.car.items;

import java.util.List;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public class ItemCanister extends Item {

    public ItemCanister() {
        super(new Item.Properties().maxStackSize(1).group(ModCreativeTabs.TAB_CAR));
        setRegistryName(new ResourceLocation(Main.MODID, "canister"));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (!context.getPlayer().isSneaking()) {
            return super.onItemUse(context);
        }

        BlockState state = context.getWorld().getBlockState(context.getPos());

        TileEntity te;

        if (state.getBlock().equals(ModBlocks.FUEL_STATION_TOP)) {
            te = context.getWorld().getTileEntity(context.getPos().down());
        } else {
            te = context.getWorld().getTileEntity(context.getPos());
        }

        if (te == null) {
            return super.onItemUse(context);
        }

        if (te instanceof TileEntityFuelStation) {
            TileEntityFuelStation fuel = (TileEntityFuelStation) te;
            boolean success = fillCanister(context.getPlayer().getHeldItem(context.getHand()), fuel);
            if (success) {
                ModSounds.playSound(SoundEvents.BLOCK_BREWING_STAND_BREW, context.getWorld(), context.getPos(), null, SoundCategory.BLOCKS);
            }
            return ActionResultType.SUCCESS;
        }

        if (te instanceof IFluidHandler) {
            IFluidHandler handler = (IFluidHandler) te;

            boolean success = fuelFluidHandler(context.getPlayer().getHeldItem(context.getHand()), handler);
            if (success) {
                ModSounds.playSound(SoundEvents.BLOCK_BREWING_STAND_BREW, context.getWorld(), context.getPos(), null, SoundCategory.BLOCKS);
            }
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTag()) {
            CompoundNBT comp = stack.getTag();

            if (comp.contains("fuel")) {
                CompoundNBT fuel = comp.getCompound("fuel");

                FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(fuel);
                if (fluidStack == null) {
                    addInfo("-", 0, tooltip);
                    super.addInformation(stack, worldIn, tooltip, flagIn);
                    return;
                }

                addInfo(fluidStack.getDisplayName().getFormattedText(), fluidStack.getAmount(), tooltip);
                super.addInformation(stack, worldIn, tooltip, flagIn);
                return;
            }
            addInfo("-", 0, tooltip);
            super.addInformation(stack, worldIn, tooltip, flagIn);
            return;
        }
        addInfo("-", 0, tooltip);

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    private void addInfo(String fluid, int amount, List<ITextComponent> tooltip) {
        tooltip.add(new TranslationTextComponent("canister.fluid", new StringTextComponent(fluid).applyTextStyle(TextFormatting.DARK_GRAY)).applyTextStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("canister.amount", new StringTextComponent(String.valueOf(amount)).applyTextStyle(TextFormatting.DARK_GRAY)).applyTextStyle(TextFormatting.GRAY));
    }

    public static boolean fillCanister(ItemStack canister, IFluidHandler handler) {
        CompoundNBT comp = canister.getOrCreateTag();

        FluidStack fluid = null;

        if (comp.contains("fuel")) {
            fluid = FluidStack.loadFluidStackFromNBT(comp.getCompound("fuel"));
        }

        int maxAmount = Config.canisterMaxFuel.get();

        if (fluid != null) {
            maxAmount = Config.canisterMaxFuel.get() - fluid.getAmount();
        }

        if (maxAmount <= 0) {
            return false;
        }

        if (fluid != null) {
            FluidStack resultSim = handler.drain(maxAmount, IFluidHandler.FluidAction.SIMULATE);
            if (resultSim == null || !resultSim.getFluid().equals(fluid.getFluid())) {
                return false;
            }
        }

        FluidStack result = handler.drain(maxAmount, IFluidHandler.FluidAction.EXECUTE);

        if (result == null) {
            return false;
        }

        if (fluid == null) {
            comp.put("fuel", result.writeToNBT(new CompoundNBT()));
            return true;
        }

        if (result.getFluid().equals(fluid.getFluid())) {
            fluid.setAmount(fluid.getAmount() + result.getAmount());
            comp.put("fuel", fluid.writeToNBT(new CompoundNBT()));
        }
        return true;
    }

    public static boolean fuelFluidHandler(ItemStack canister, IFluidHandler handler) {
        if (!canister.hasTag()) {
            return false;
        }

        CompoundNBT comp = canister.getTag();

        if (!comp.contains("fuel")) {
            return false;
        }

        CompoundNBT fluid = comp.getCompound("fuel");

        FluidStack stack = FluidStack.loadFluidStackFromNBT(fluid);

        if (stack == null) {
            return false;
        }

        int fueledAmount = handler.fill(stack, IFluidHandler.FluidAction.EXECUTE);

        stack.setAmount(stack.getAmount() - fueledAmount);

        if (stack.getAmount() <= 0) {
            comp.put("fuel", new CompoundNBT());
            return true;
        }

        CompoundNBT f = new CompoundNBT();
        stack.writeToNBT(f);
        comp.put("fuel", f);
        return true;
    }

}
