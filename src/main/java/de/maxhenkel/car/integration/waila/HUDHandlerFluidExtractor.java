package de.maxhenkel.car.integration.waila;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class HUDHandlerFluidExtractor implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    public static final HUDHandlerFluidExtractor INSTANCE = new HUDHandlerFluidExtractor();

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Main.MODID, "fluid_extractor");

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("filter")) {
            iTooltip.add(Component.translatable("tooltip.waila.fluid_extractor.filter", Component.translatable(blockAccessor.getServerData().getStringOr("filter", ""))));
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        TileEntityFluidExtractor extractor = (TileEntityFluidExtractor) blockAccessor.getBlockEntity();
        Fluid filter = extractor.getFilterFluid();
        if (filter != null) {
            compoundTag.putString("filter", extractor.getFilterFluid().getFluidType().getDescriptionId());
        }
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }
}