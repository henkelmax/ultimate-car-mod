package de.maxhenkel.car.integration.wawla;

import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;

@WailaPlugin
public class PluginCar implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        registrar.registerComponentProvider(HUDHandlerTank.INSTANCE, TooltipPosition.BODY, TileEntityTank.class);
        registrar.registerBlockDataProvider(HUDHandlerTank.INSTANCE, TileEntityTank.class);

        registrar.registerComponentProvider(HUDHandlerGenerator.INSTANCE, TooltipPosition.BODY, TileEntityGenerator.class);
        registrar.registerBlockDataProvider(HUDHandlerGenerator.INSTANCE, TileEntityGenerator.class);

        registrar.registerComponentProvider(HUDHandlerFluidExtractor.INSTANCE, TooltipPosition.BODY, TileEntityFluidExtractor.class);
        registrar.registerBlockDataProvider(HUDHandlerFluidExtractor.INSTANCE, TileEntityFluidExtractor.class);

        registrar.registerComponentProvider(HUDHandlerCars.INSTANCE, TooltipPosition.HEAD, EntityGenericCar.class);
        registrar.registerComponentProvider(HUDHandlerCars.INSTANCE, TooltipPosition.BODY, EntityGenericCar.class);
        registrar.registerComponentProvider(HUDHandlerCars.INSTANCE, TooltipPosition.TAIL, EntityGenericCar.class);
    }

}
