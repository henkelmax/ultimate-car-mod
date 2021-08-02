package de.maxhenkel.car.integration.waila;

import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import mcp.mobius.waila.api.BlockAccessor;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.config.IPluginConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class HUDHandlerFluidExtractor implements IComponentProvider, IServerDataProvider<BlockEntity> {

    public static final HUDHandlerFluidExtractor INSTANCE = new HUDHandlerFluidExtractor();

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("filter")) {
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(blockAccessor.getServerData().getString("filter")));
            iTooltip.add(new TranslatableComponent("tooltip.waila.fluid_extractor.filter", new FluidStack(fluid, 1).getDisplayName()));
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, ServerPlayer serverPlayer, Level level, BlockEntity blockEntity, boolean b) {
        TileEntityFluidExtractor extractor = (TileEntityFluidExtractor) blockEntity;
        Fluid filter = extractor.getFilterFluid();
        if (filter != null) {
            compoundTag.putString("filter", extractor.getFilterFluid().getRegistryName().toString());
        }
    }
}