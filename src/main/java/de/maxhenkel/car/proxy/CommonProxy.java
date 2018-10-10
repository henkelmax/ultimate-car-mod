package de.maxhenkel.car.proxy;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;
import de.maxhenkel.car.Config;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.events.ConfigEvents;
import de.maxhenkel.car.gui.GuiHandler;
import de.maxhenkel.car.net.*;
import minecrafttransportsimulator.systems.ConfigSystem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

    public static SimpleNetworkWrapper simpleNetworkWrapper;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    public void preinit(FMLPreInitializationEvent event) {
        CommonProxy.simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Main.MODID);
        CommonProxy.simpleNetworkWrapper.registerMessage(MessageControlCar.class, MessageControlCar.class, 0, Side.SERVER);
        CommonProxy.simpleNetworkWrapper.registerMessage(MessageCarGui.class, MessageCarGui.class, 1, Side.SERVER);
        CommonProxy.simpleNetworkWrapper.registerMessage(MessageStarting.class, MessageStarting.class, 2, Side.SERVER);
        CommonProxy.simpleNetworkWrapper.registerMessage(MessageCrash.class, MessageCrash.class, 3, Side.SERVER);
        CommonProxy.simpleNetworkWrapper.registerMessage(MessageStartFuel.class, MessageStartFuel.class, 4, Side.SERVER);
        CommonProxy.simpleNetworkWrapper.registerMessage(MessagePlaySoundLoop.class, MessagePlaySoundLoop.class, 5, Side.CLIENT);
        CommonProxy.simpleNetworkWrapper.registerMessage(MessageSyncTileEntity.class, MessageSyncTileEntity.class, 6, Side.CLIENT);
        CommonProxy.simpleNetworkWrapper.registerMessage(MessageSpawnCar.class, MessageSpawnCar.class, 7, Side.SERVER);
        CommonProxy.simpleNetworkWrapper.registerMessage(MessageOpenGui.class, MessageOpenGui.class, 8, Side.SERVER);
        CommonProxy.simpleNetworkWrapper.registerMessage(MessageRepairCar.class, MessageRepairCar.class, 9, Side.SERVER);
        CommonProxy.simpleNetworkWrapper.registerMessage(MessageCarHorn.class, MessageCarHorn.class, 10, Side.SERVER);
        CommonProxy.simpleNetworkWrapper.registerMessage(MessageSyncConfig.class, MessageSyncConfig.class, 11, Side.CLIENT);
        CommonProxy.simpleNetworkWrapper.registerMessage(MessageEditSign.class, MessageEditSign.class, 12, Side.SERVER);
        CommonProxy.simpleNetworkWrapper.registerMessage(MessageFuelStationAdminAmount.class, MessageFuelStationAdminAmount.class, 13, Side.SERVER);
        CommonProxy.simpleNetworkWrapper.registerMessage(MessageCenterCar.class, MessageCenterCar.class, 14, Side.SERVER);
        CommonProxy.simpleNetworkWrapper.registerMessage(MessageCenterCar.class, MessageCenterCar.class, 15, Side.CLIENT);
        CommonProxy.simpleNetworkWrapper.registerMessage(MessageEditNumberPlate.class, MessageEditNumberPlate.class, 16, Side.SERVER);

        try {
            File configFolder = new File(event.getModConfigurationDirectory(), Main.MODID);
            configFolder.mkdirs();
            Config.init(configFolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(FMLInitializationEvent event) {
        inject();

        EntityRegistry.registerModEntity(new ResourceLocation(Main.MODID, "car"), EntityGenericCar.class,
                "car", 3727, Main.instance(), 64, 1, true);

        NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance(), new GuiHandler());

        MinecraftForge.EVENT_BUS.register(new ConfigEvents());
    }

    public void postinit(FMLPostInitializationEvent event) {
        Config.postInit();
    }

    public static void inject() {
        if(!Loader.isModLoaded("mts")){
            return;
        }
        try {
            ConfigSystem.config.load();
            double value = ConfigSystem.config.get("fuels", "bio_diesel", 0.0D).getDouble();
            ConfigSystem.config.save();
            Field field = ConfigSystem.class.getDeclaredField("fluidValues");
            field.setAccessible(true);
            Map<String, Double> map = (Map<String, Double>) field.get(null);
            map.put("bio_diesel", value);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
