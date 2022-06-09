package de.maxhenkel.car.items;

import java.util.List;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public class ItemCanister extends Item {

    public ItemCanister() {
        super(new Item.Properties().stacksTo(1).tab(ModItemGroups.TAB_CAR));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!context.getPlayer().isShiftKeyDown()) {
            return super.useOn(context);
        }

        BlockState state = context.getLevel().getBlockState(context.getClickedPos());

        BlockEntity te;

        if (state.getBlock().equals(ModBlocks.GAS_STATION_TOP.get())) {
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
                ModSounds.playSound(SoundEvents.BREWING_STAND_BREW, context.getLevel(), context.getClickedPos(), null, SoundSource.BLOCKS);
            }
            return InteractionResult.SUCCESS;
        }

        if (te instanceof IFluidHandler) {
            IFluidHandler handler = (IFluidHandler) te;

            boolean success = fuelFluidHandler(context.getPlayer().getItemInHand(context.getHand()), handler);
            if (success) {
                ModSounds.playSound(SoundEvents.BREWING_STAND_BREW, context.getLevel(), context.getClickedPos(), null, SoundSource.BLOCKS);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (stack.hasTag()) {
            CompoundTag comp = stack.getTag();

            if (comp.contains("fuel")) {
                CompoundTag fuel = comp.getCompound("fuel");

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

    private void addInfo(String fluid, int amount, List<Component> tooltip) {
        tooltip.add(Component.translatable("canister.fluid", Component.literal(fluid).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("canister.amount", Component.literal(String.valueOf(amount)).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY));
    }

    public static boolean fillCanister(ItemStack canister, IFluidHandler handler) {
        CompoundTag comp = canister.getOrCreateTag();

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
            comp.put("fuel", result.writeToNBT(new CompoundTag()));
            return true;
        }

        if (result.getFluid().equals(fluid.getFluid())) {
            fluid.setAmount(fluid.getAmount() + result.getAmount());
            comp.put("fuel", fluid.writeToNBT(new CompoundTag()));
        }
        return true;
    }

    public static boolean fuelFluidHandler(ItemStack canister, IFluidHandler handler) {
        if (!canister.hasTag()) {
            return false;
        }

        CompoundTag comp = canister.getTag();

        if (!comp.contains("fuel")) {
            return false;
        }

        CompoundTag fluid = comp.getCompound("fuel");

        FluidStack stack = FluidStack.loadFluidStackFromNBT(fluid);

        if (stack == null || stack.isEmpty()) {
            return false;
        }

        int fueledAmount = handler.fill(stack, IFluidHandler.FluidAction.EXECUTE);

        stack.setAmount(stack.getAmount() - fueledAmount);

        if (stack.getAmount() <= 0) {
            comp.put("fuel", new CompoundTag());
            return true;
        }

        CompoundTag f = new CompoundTag();
        stack.writeToNBT(f);
        comp.put("fuel", f);
        return true;
    }

}
