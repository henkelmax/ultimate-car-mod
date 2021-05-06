package de.maxhenkel.car.config;

import de.maxhenkel.corelib.config.ConfigBase;
import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig extends ConfigBase {

    public final ForgeConfigSpec.DoubleValue carVolume;
    public final ForgeConfigSpec.BooleanValue thirdPersonEnter;
    public final ForgeConfigSpec.BooleanValue tempInFarenheit;
    public final ForgeConfigSpec.DoubleValue carZoom;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        super(builder);
        thirdPersonEnter = builder.define("car.third_person_when_enter_car", true);
        tempInFarenheit = builder.comment("True if the car temperature should be displayed in farenheit").define("car.temp_farenheit", false);
        carVolume = builder.defineInRange("car.car_volume", 0.25D, 0D, 1D);
        carZoom = builder.defineInRange("car.third_person_zoom", 6D, 1D, 20D);
    }
}
