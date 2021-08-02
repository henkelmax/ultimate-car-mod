package de.maxhenkel.car.integration.waila;

import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
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

public class HUDHandlerGenerator implements IComponentProvider, IServerDataProvider<BlockEntity> {

    public static final HUDHandlerGenerator INSTANCE = new HUDHandlerGenerator();

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        int energy = blockAccessor.getServerData().getInt("energy");
        int maxEnergy = blockAccessor.getServerData().getInt("max_energy");
        int fluid = blockAccessor.getServerData().getInt("fluid");
        int maxFluid = blockAccessor.getServerData().getInt("max_fluid");

        iTooltip.add(new TranslatableComponent("tooltip.waila.generator.energy", energy, maxEnergy));
        iTooltip.add(new TranslatableComponent("tooltip.waila.generator.fluid", fluid, maxFluid));
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, ServerPlayer serverPlayer, Level level, BlockEntity blockEntity, boolean b) {
        TileEntityGenerator generator = (TileEntityGenerator) blockEntity;
        compoundTag.putInt("energy", generator.getEnergyStored());
        compoundTag.putInt("max_energy", generator.getMaxEnergyStored());
        compoundTag.putInt("fluid", generator.getFluidInTank(0).getAmount());
        compoundTag.putInt("max_fluid", generator.getTankCapacity(0));
    }
}