package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.corelib.energy.EnergyUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemBattery extends Item {

    public ItemBattery() {
        super(new Item.Properties().stacksTo(1).tab(ModItemGroups.TAB_CAR).durability(500));
        setRegistryName(new ResourceLocation(Main.MODID, "battery"));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("tooltip.battery_energy", new StringTextComponent(String.valueOf(getEnergy(stack))).withStyle(TextFormatting.DARK_GRAY)).withStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("tooltip.battery").withStyle(TextFormatting.GRAY));

        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        if (context.getLevel().getBlockState(context.getClickedPos()).getBlock().equals(ModBlocks.GENERATOR)) {
            IEnergyStorage storage = EnergyUtils.getEnergyStorage(context.getLevel(), context.getClickedPos(), context.getClickedFace());
            if (storage != null) {
                ItemStack stack = context.getPlayer().getItemInHand(context.getHand());

                int energyToFill = stack.getDamageValue();

                int amount = storage.extractEnergy(energyToFill, false);

                stack.setDamageValue(energyToFill - amount);
                context.getPlayer().setItemInHand(context.getHand(), stack);
                return ActionResultType.SUCCESS;
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

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if (cap == CapabilityEnergy.ENERGY) {
                    return LazyOptional.of(() -> new BatteryEnergyStorage(stack)).cast();
                }
                return LazyOptional.empty();
            }
        };
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
