package de.maxhenkel.car.items;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.tools.IntegerJournal;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

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
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().getBlockState(context.getClickedPos()).getBlock().equals(ModBlocks.GENERATOR.get())) {
            EnergyHandler energyHandler = context.getLevel().getCapability(Capabilities.Energy.BLOCK, context.getClickedPos(), context.getClickedFace());
            if (energyHandler != null) {
                ItemStack stack = context.getPlayer().getItemInHand(context.getHand());
                int energyToFill = stack.getDamageValue();
                int amount;
                try (Transaction transaction = Transaction.open(null)) {
                    amount = energyHandler.extract(energyToFill, transaction);
                    transaction.commit();
                }

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

    public EnergyHandler getEnergyHandler(ItemStack stack) {
        return new BatteryEnergyStorage(stack);
    }

    public class BatteryEnergyStorage implements EnergyHandler {

        private ItemStack stack;

        private final SnapshotJournal<Integer> energyJournal = new IntegerJournal(integer -> setEnergy(stack, integer), () -> getEnergy(stack));

        public BatteryEnergyStorage(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public long getAmountAsLong() {
            return getEnergy(stack);
        }

        @Override
        public long getCapacityAsLong() {
            return getMaxDamage(stack);
        }

        @Override
        public int insert(int amount, TransactionContext transaction) {
            int result = Math.min(amount, getMaxDamage(stack) - getEnergy(stack));
            energyJournal.updateSnapshots(transaction);
            setEnergy(stack, getEnergy(stack) + result);
            return result;
        }

        @Override
        public int extract(int amount, TransactionContext transaction) {
            return 0;
        }
    }

}
