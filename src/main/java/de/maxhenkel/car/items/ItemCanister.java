package de.maxhenkel.car.items;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
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
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandlerUtil;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

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

        BlockPos tePos = context.getClickedPos();

        if (state.getBlock().equals(ModBlocks.GAS_STATION_TOP.get())) {
            tePos = context.getClickedPos().below();
        }

        BlockEntity te = context.getLevel().getBlockEntity(tePos);

        if (te == null) {
            return super.useOn(context);
        }

        ItemAccess itemAccess = ItemAccess.forPlayerInteraction(context.getPlayer(), context.getHand());

        if (te instanceof TileEntityGasStation gasStation) {
            if (gasStation.hasTrade()) {
                return super.useOn(context);
            }
            boolean success = fillCanister(itemAccess, gasStation.getFluidHandler());
            if (success) {
                ModSounds.playSound(SoundEvents.BREWING_STAND_BREW, context.getLevel(), context.getClickedPos(), null, SoundSource.BLOCKS);
            }
            return InteractionResult.SUCCESS;
        }

        ResourceHandler<FluidResource> capability = context.getLevel().getCapability(Capabilities.Fluid.BLOCK, tePos, context.getClickedFace());
        if (capability != null) {
            boolean success = fuelFluidHandler(itemAccess, capability);
            if (success) {
                ModSounds.playSound(SoundEvents.BREWING_STAND_BREW, context.getLevel(), context.getClickedPos(), null, SoundSource.BLOCKS);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag flag) {
        SimpleFluidContent content = stack.get(CarMod.FUEL_DATA_COMPONENT);
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

    public static boolean fillCanister(ItemAccess canister, ResourceHandler<FluidResource> handler) {
        ResourceHandler<FluidResource> canisterHandler = canister.getCapability(Capabilities.Fluid.ITEM);
        int moved = ResourceHandlerUtil.move(handler, canisterHandler, resource -> true, Integer.MAX_VALUE, null);
        return moved > 0;
    }

    public static boolean fuelFluidHandler(ItemAccess canister, ResourceHandler<FluidResource> handler) {
        ResourceHandler<FluidResource> canisterHandler = canister.getCapability(Capabilities.Fluid.ITEM);
        int moved = ResourceHandlerUtil.move(canisterHandler, handler, resource -> true, Integer.MAX_VALUE, null);
        return moved > 0;
    }

}
