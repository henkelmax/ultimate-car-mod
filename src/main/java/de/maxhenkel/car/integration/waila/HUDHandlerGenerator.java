package de.maxhenkel.car.integration.waila;

import de.maxhenkel.car.Main;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class HUDHandlerGenerator implements IBlockComponentProvider {

    public static final HUDHandlerGenerator INSTANCE = new HUDHandlerGenerator();

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Main.MODID, "generator");

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        int energy = blockAccessor.getServerData().getIntOr("energy", 0);
        int maxEnergy = blockAccessor.getServerData().getIntOr("max_energy", 0);
        int fluid = blockAccessor.getServerData().getIntOr("fluid", 0);
        int maxFluid = blockAccessor.getServerData().getIntOr("max_fluid", 0);

        iTooltip.add(Component.translatable("tooltip.waila.generator.energy", energy, maxEnergy));
        iTooltip.add(Component.translatable("tooltip.waila.generator.fluid", fluid, maxFluid));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }
}