package de.maxhenkel.car.integration.waila;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

public class DataProviderFluidExtractor implements IServerDataProvider<BlockAccessor> {

    public static final DataProviderFluidExtractor INSTANCE = new DataProviderFluidExtractor();

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Main.MODID, "fluid_extractor_data");

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