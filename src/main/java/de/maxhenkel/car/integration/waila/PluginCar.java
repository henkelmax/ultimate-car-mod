package de.maxhenkel.car.integration.waila;

import de.maxhenkel.car.blocks.BlockFluidExtractor;
import de.maxhenkel.car.blocks.BlockGenerator;
import de.maxhenkel.car.blocks.BlockTank;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class PluginCar implements IWailaPlugin {

    public static final ResourceLocation OBJECT_NAME_TAG = ResourceLocation.fromNamespaceAndPath("jade", "object_name");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(HUDHandlerTank.INSTANCE, TileEntityTank.class);
        registration.registerBlockDataProvider(HUDHandlerGenerator.INSTANCE, TileEntityGenerator.class);
        registration.registerBlockDataProvider(HUDHandlerFluidExtractor.INSTANCE, TileEntityFluidExtractor.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(HUDHandlerTank.INSTANCE, BlockTank.class);
        registration.registerBlockComponent(HUDHandlerGenerator.INSTANCE, BlockGenerator.class);
        registration.registerBlockComponent(HUDHandlerFluidExtractor.INSTANCE, BlockFluidExtractor.class);

        registration.registerEntityComponent(HUDHandlerCars.INSTANCE, EntityGenericCar.class);
    }

}