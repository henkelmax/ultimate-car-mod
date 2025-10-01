package de.maxhenkel.car.integration.waila;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

public class DataProviderGenerator implements IServerDataProvider<BlockAccessor> {

    public static final DataProviderGenerator INSTANCE = new DataProviderGenerator();

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "generator_data");

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        TileEntityGenerator generator = (TileEntityGenerator) blockAccessor.getBlockEntity();
        compoundTag.putInt("energy", generator.getAmountAsInt());
        compoundTag.putInt("max_energy", generator.getCapacityAsInt());
        compoundTag.putInt("fluid", generator.getAmountAsInt(0));
        compoundTag.putInt("max_fluid", generator.getCapacityAsInt(0, generator.getResource(0)));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }
}