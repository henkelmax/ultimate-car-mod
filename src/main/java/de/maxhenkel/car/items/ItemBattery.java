package de.maxhenkel.car.items;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.corelib.energy.EnergyUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.function.Consumer;

public class ItemBattery extends Item {

    public ItemBattery(Properties properties) {
        super(properties.stacksTo(1).durability(500));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag flag) {
        consumer.accept(Component.translatable("tooltip.battery_energy", Component.literal(String.valueOf(getEnergy(stack))).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY));
        consumer.accept(Component.translatable("tooltip.battery").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltipDisplay, consumer, flag);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().getBlockState(context.getClickedPos()).getBlock().equals(ModBlocks.GENERATOR.get())) {
            IEnergyStorage storage = EnergyUtils.getEnergyStorage(context.getLevel(), context.getClickedPos(), context.getClickedFace());
            if (storage != null) {
                ItemStack stack = context.getPlayer().getItemInHand(context.getHand());

                int energyToFill = stack.getDamageValue();

                int amount = storage.extractEnergy(energyToFill, false);

                stack.setDamageValue(energyToFill - amount);
                context.getPlayer().setItemInHand(context.getHand(), stack);
                return InteractionResult.SUCCESS;
            }
        }

        return super.useOn(context);
    }

    public int getEnergy(ItemStack stack) {
        return getMaxDamage(stack) - getDamage(stack);
    }

    public void setEnergy(ItemStack stack, int energy) {
        setDamage(stack, Math.max(getMaxDamage(stack) - energy, 0));
    }

    public IEnergyStorage getEnergyHandler(ItemStack stack) {
        return new BatteryEnergyStorage(stack);
    }

    public class BatteryEnergyStorage implements IEnergyStorage {

        private ItemStack stack;

        public BatteryEnergyStorage(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            int amount = Math.min(maxReceive, getMaxDamage(stack) - getEnergy(stack));
            if (!simulate) {
                setEnergy(stack, getEnergy(stack) + amount);
            }
            return amount;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            return 0;
        }

        @Override
        public int getEnergyStored() {
            return getEnergy(stack);
        }

        @Override
        public int getMaxEnergyStored() {
            return getMaxDamage(stack);
        }

        @Override
        public boolean canExtract() {
            return false;
        }

        @Override
        public boolean canReceive() {
            return true;
        }
    }

}
