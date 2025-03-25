package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.function.Consumer;

public class ItemCanister extends Item {

    public ItemCanister(Properties properties) {
        super(properties.stacksTo(1));
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
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag flag) {
        SimpleFluidContent content = stack.get(Main.FUEL_DATA_COMPONENT);
        if (content == null || content.isEmpty()) {
            addInfo("-", 0, consumer);
            super.appendHoverText(stack, context, tooltipDisplay, consumer, flag);
            return;
        }
        FluidStack fluidStack = content.copy();
        addInfo(fluidStack.getHoverName().getString(), fluidStack.getAmount(), consumer);
        super.appendHoverText(stack, context, tooltipDisplay, consumer, flag);
    }

    private void addInfo(String fluid, int amount, Consumer<Component> consumer) {
        consumer.accept(Component.translatable("canister.fluid", Component.literal(fluid).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY));
        consumer.accept(Component.translatable("canister.amount", Component.literal(String.valueOf(amount)).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY));
    }

    public static boolean fillCanister(ItemStack canister, IFluidHandler handler) {
        SimpleFluidContent content = canister.get(Main.FUEL_DATA_COMPONENT);

        int maxAmount = Main.SERVER_CONFIG.canisterMaxFuel.get();

        if (content != null) {
            maxAmount = Main.SERVER_CONFIG.canisterMaxFuel.get() - content.getAmount();
        }

        if (maxAmount <= 0) {
            return false;
        }

        FluidStack resultSim = handler.drain(maxAmount, IFluidHandler.FluidAction.SIMULATE);
        if (resultSim.getAmount() <= 0) {
            return false;
        }

        if (content != null && !content.isEmpty() && !resultSim.getFluid().equals(content.getFluid())) {
            return false;
        }

        FluidStack result = handler.drain(maxAmount, IFluidHandler.FluidAction.EXECUTE);

        if (result.isEmpty()) {
            return false;
        }

        if (content == null || content.isEmpty()) {
            canister.set(Main.FUEL_DATA_COMPONENT, SimpleFluidContent.copyOf(result));
            return true;
        }

        if (result.getFluid().equals(content.getFluid())) {
            canister.set(Main.FUEL_DATA_COMPONENT, SimpleFluidContent.copyOf(new FluidStack(content.getFluid(), content.getAmount() + result.getAmount())));
        }
        return true;
    }

    public static boolean fuelFluidHandler(ItemStack canister, IFluidHandler handler) {
        SimpleFluidContent content = canister.get(Main.FUEL_DATA_COMPONENT);

        if (content == null || content.isEmpty()) {
            return false;
        }
        FluidStack result = content.copy();

        int fueledAmount = handler.fill(result, IFluidHandler.FluidAction.EXECUTE);

        result.setAmount(result.getAmount() - fueledAmount);

        if (result.getAmount() <= 0) {
            canister.remove(Main.FUEL_DATA_COMPONENT);
            return true;
        }

        canister.set(Main.FUEL_DATA_COMPONENT, SimpleFluidContent.copyOf(result));
        return true;
    }

}
