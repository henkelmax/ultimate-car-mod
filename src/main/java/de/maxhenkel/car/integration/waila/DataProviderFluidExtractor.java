package de.maxhenkel.car.integration.waila;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.Fluid;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

public class DataProviderFluidExtractor implements IServerDataProvider<BlockAccessor> {

    public static final DataProviderFluidExtractor INSTANCE = new DataProviderFluidExtractor();

    private static final Identifier UID = Identifier.fromNamespaceAndPath(CarMod.MODID, "fluid_extractor_data");

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        TileEntityFluidExtractor extractor = (TileEntityFluidExtractor) blockAccessor.getBlockEntity();
        Fluid filter = extractor.getFilterFluid();
        if (filter != null) {
            compoundTag.putString("filter", extractor.getFilterFluid().getFluidType().getDescriptionId());
        }
    }

    @Override
    public Identifier getUid() {
        return UID;
    }
}