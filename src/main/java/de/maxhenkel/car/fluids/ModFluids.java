package de.maxhenkel.car.fluids;

import net.minecraft.world.level.material.FlowingFluid;

public class ModFluids {

    public static FluidCanolaOil CANOLA_OIL = new FluidCanolaOil();
    public static FluidCanolaOilFlowing CANOLA_OIL_FLOWING = new FluidCanolaOilFlowing();
    public static FluidMethanol METHANOL = new FluidMethanol();
    public static FluidMethanolFlowing METHANOL_FLOWING = new FluidMethanolFlowing();
    public static FluidCanolaMethanolMix CANOLA_METHANOL_MIX = new FluidCanolaMethanolMix();
    public static FluidCanolaMethanolMixFlowing CANOLA_METHANOL_MIX_FLOWING = new FluidCanolaMethanolMixFlowing();
    public static FluidGlycerin GLYCERIN = new FluidGlycerin();
    public static FluidGlycerinFlowing GLYCERIN_FLOWING = new FluidGlycerinFlowing();
    public static FluidBioDiesel BIO_DIESEL = new FluidBioDiesel();
    public static FluidBioDieselFlowing BIO_DIESEL_FLOWING = new FluidBioDieselFlowing();

    public static FlowingFluid[] STILL_FLUIDS = new FlowingFluid[]{
            CANOLA_OIL,
            METHANOL,
            CANOLA_METHANOL_MIX,
            GLYCERIN,
            BIO_DIESEL
    };
}
