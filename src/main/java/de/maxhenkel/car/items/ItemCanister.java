package de.maxhenkel.car.items;

import java.util.List;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
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
        super(new Item.Properties().stacksTo(1).tab(ModItemGroups.TAB_CAR));
        setRegistryName(new ResourceLocation(Main.MODID, "canister"));
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        if (!context.getPlayer().isShiftKeyDown()) {
            return super.useOn(context);
        }

        BlockState state = context.getLevel().getBlockState(context.getClickedPos());

        TileEntity te;

        if (state.getBlock().equals(ModBlocks.FUEL_STATION_TOP)) {
            te = context.getLevel().getBlockEntity(context.getClickedPos().below());
        } else {
            te = context.getLevel().getBlockEntity(context.getClickedPos());
        }

        if (te == null) {
            return super.useOn(context);
        }

        if (te instanceof TileEntityGasStation) {
            TileEntityGasStation fuel = (TileEntityGasStation) te;
            boolean success = fillCanister(context.getPlayer().getItemInHand(context.getHand()), fuel);
            if (success) {
                ModSounds.playSound(SoundEvents.BREWING_STAND_BREW, context.getLevel(), context.getClickedPos(), null, SoundCategory.BLOCKS);
            }
            return ActionResultType.SUCCESS;
        }

        if (te instanceof IFluidHandler) {
            IFluidHandler handler = (IFluidHandler) te;

            boolean success = fuelFluidHandler(context.getPlayer().getItemInHand(context.getHand()), handler);
            if (success) {
                ModSounds.playSound(SoundEvents.BREWING_STAND_BREW, context.getLevel(), context.getClickedPos(), null, SoundCategory.BLOCKS);
            }
            return ActionResultType.SUCCESS;
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTag()) {
            CompoundNBT comp = stack.getTag();

            if (comp.contains("fuel")) {
                CompoundNBT fuel = comp.getCompound("fuel");

                FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(fuel);
                if (fluidStack == null || fluidStack.isEmpty()) {
                    addInfo("-", 0, tooltip);
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                    return;
                }

                addInfo(fluidStack.getDisplayName().getString(), fluidStack.getAmount(), tooltip);
                super.appendHoverText(stack, worldIn, tooltip, flagIn);
                return;
            }
            addInfo("-", 0, tooltip);
            super.appendHoverText(stack, worldIn, tooltip, flagIn);
            return;
        }
        addInfo("-", 0, tooltip);

        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    private void addInfo(String fluid, int amount, List<ITextComponent> tooltip) {
        tooltip.add(new TranslationTextComponent("canister.fluid", new StringTextComponent(fluid).withStyle(TextFormatting.DARK_GRAY)).withStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("canister.amount", new StringTextComponent(String.valueOf(amount)).withStyle(TextFormatting.DARK_GRAY)).withStyle(TextFormatting.GRAY));
    }

    public static boolean fillCanister(ItemStack canister, IFluidHandler handler) {
        CompoundNBT comp = canister.getOrCreateTag();

        FluidStack fluid = null;

        if (comp.contains("fuel")) {
            fluid = FluidStack.loadFluidStackFromNBT(comp.getCompound("fuel"));
        }

        int maxAmount = Main.SERVER_CONFIG.canisterMaxFuel.get();

        if (fluid != null) {
            maxAmount = Main.SERVER_CONFIG.canisterMaxFuel.get() - fluid.getAmount();
        }

        if (maxAmount <= 0) {
            return false;
        }

        FluidStack resultSim = handler.drain(maxAmount, IFluidHandler.FluidAction.SIMULATE);
        if (resultSim.getAmount() <= 0) {
            return false;
        }

        if (fluid != null && !fluid.isEmpty() && !resultSim.getFluid().equals(fluid.getFluid())) {
            return false;
        }

        FluidStack result = handler.drain(maxAmount, IFluidHandler.FluidAction.EXECUTE);

        if (result.isEmpty()) {
            return false;
        }

        if (fluid == null || fluid.isEmpty()) {
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

        if (stack == null || stack.isEmpty()) {
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
