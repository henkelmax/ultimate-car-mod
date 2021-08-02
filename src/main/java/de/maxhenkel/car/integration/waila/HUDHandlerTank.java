package de.maxhenkel.car.integration.waila;

import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import mcp.mobius.waila.api.BlockAccessor;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.config.IPluginConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;

public class HUDHandlerTank implements IComponentProvider, IServerDataProvider<BlockEntity> {

    public static final HUDHandlerTank INSTANCE = new HUDHandlerTank();

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        FluidStack stack = FluidStack.loadFluidStackFromNBT(blockAccessor.getServerData().getCompound("fluid"));

        if (stack.isEmpty()) {
            iTooltip.add(new TranslatableComponent("tooltip.waila.tank.no_fluid"));
        } else {
            iTooltip.add(new TranslatableComponent("tooltip.waila.tank.fluid", stack.getDisplayName()));
            iTooltip.add(new TranslatableComponent("tooltip.waila.tank.amount", stack.getAmount(), TileEntityTank.CAPACITY));
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, ServerPlayer serverPlayer, Level level, BlockEntity blockEntity, boolean b) {
        TileEntityTank tank = (TileEntityTank) blockEntity;
        compoundTag.put("fluid", tank.getFluid().writeToNBT(new CompoundTag()));
    }
}