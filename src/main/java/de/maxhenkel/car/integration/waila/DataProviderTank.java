package de.maxhenkel.car.integration.waila;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.corelib.codec.ValueInputOutputUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.TagValueOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

public class DataProviderTank implements IServerDataProvider<BlockAccessor> {

    public static final DataProviderTank INSTANCE = new DataProviderTank();

    private static final Identifier UID = Identifier.fromNamespaceAndPath(CarMod.MODID, "tank_data");


    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        TileEntityTank tank = (TileEntityTank) blockAccessor.getBlockEntity();
        if (!tank.getFluid().isEmpty()) {
            TagValueOutput valueOutput = ValueInputOutputUtils.createValueOutput(tank, tank.getLevel().registryAccess());
            valueOutput.store("fluid", FluidStack.CODEC, tank.getFluid());
            compoundTag.merge(ValueInputOutputUtils.toTag(valueOutput));
        }
    }

    @Override
    public Identifier getUid() {
        return UID;
    }
}