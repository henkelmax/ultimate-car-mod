package de.maxhenkel.car.integration;

//import de.maxhenkel.car.integration.theoneprobe.TheOneProbeModule;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public class IMC {

    @SubscribeEvent
    public static void enqueueIMC(InterModEnqueueEvent event) {
        if (ModList.get().isLoaded("theoneprobe")) {
            // TODO
            // InterModComms.sendTo("theoneprobe", "getTheOneProbe", TheOneProbeModule::new);
        }
    }

}
