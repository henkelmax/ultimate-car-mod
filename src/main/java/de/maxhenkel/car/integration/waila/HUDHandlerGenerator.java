package de.maxhenkel.car.integration.waila;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class HUDHandlerGenerator implements IBlockComponentProvider, IServerDataProvider<BlockEntity> {

    public static final HUDHandlerGenerator INSTANCE = new HUDHandlerGenerator();

    private static final ResourceLocation UID = new ResourceLocation(Main.MODID, "generator");

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        int energy = blockAccessor.getServerData().getInt("energy");
        int maxEnergy = blockAccessor.getServerData().getInt("max_energy");
        int fluid = blockAccessor.getServerData().getInt("fluid");
        int maxFluid = blockAccessor.getServerData().getInt("max_fluid");

        iTooltip.add(Component.translatable("tooltip.waila.generator.energy", energy, maxEnergy));
        iTooltip.add(Component.translatable("tooltip.waila.generator.fluid", fluid, maxFluid));
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, ServerPlayer serverPlayer, Level level, BlockEntity blockEntity, boolean b) {
        TileEntityGenerator generator = (TileEntityGenerator) blockEntity;
        compoundTag.putInt("energy", generator.getEnergyStored());
        compoundTag.putInt("max_energy", generator.getMaxEnergyStored());
        compoundTag.putInt("fluid", generator.getFluidInTank(0).getAmount());
        compoundTag.putInt("max_fluid", generator.getTankCapacity(0));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }
}