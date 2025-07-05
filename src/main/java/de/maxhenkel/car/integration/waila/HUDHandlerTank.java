package de.maxhenkel.car.integration.waila;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.corelib.codec.ValueInputOutputUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.TagValueInput;
import net.neoforged.neoforge.fluids.FluidStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class HUDHandlerTank implements IBlockComponentProvider {

    public static final HUDHandlerTank INSTANCE = new HUDHandlerTank();

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "tank");

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        CompoundTag tag = blockAccessor.getServerData();
        TagValueInput valueInput = ValueInputOutputUtils.createValueInput(blockAccessor.getBlockEntity(), blockAccessor.getBlockEntity().getLevel().registryAccess(), tag);
        FluidStack stack = valueInput.read("fluid", FluidStack.CODEC).orElse(FluidStack.EMPTY);

        if (stack.isEmpty()) {
            iTooltip.add(Component.translatable("tooltip.waila.tank.no_fluid"));
        } else {
            iTooltip.add(Component.translatable("tooltip.waila.tank.fluid", stack.getHoverName()));
            iTooltip.add(Component.translatable("tooltip.waila.tank.amount", stack.getAmount(), TileEntityTank.CAPACITY));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }
}