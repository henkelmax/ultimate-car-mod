package de.maxhenkel.car.fluids;

import net.minecraft.fluid.FlowingFluid;

public class ModFluids {

    public static FlowingFluid CANOLA_OIL = new FluidCanolaOil();
    public static FlowingFluid CANOLA_OIL_FLOWING = new FluidCanolaOilFlowing();
    public static FlowingFluid METHANOL = new FluidMethanol();
    public static FlowingFluid METHANOL_FLOWING = new FluidMethanolFlowing();
    public static FlowingFluid CANOLA_METHANOL_MIX = new FluidCanolaMethanolMix();
    public static FlowingFluid CANOLA_METHANOL_MIX_FLOWING = new FluidCanolaMethanolMixFlowing();
    public static FlowingFluid GLYCERIN = new FluidGlycerin();
    public static FlowingFluid GLYCERIN_FLOWING = new FluidGlycerinFlowing();
    public static FlowingFluid BIO_DIESEL = new FluidBioDiesel();
    public static FlowingFluid BIO_DIESEL_FLOWING = new FluidBioDieselFlowing();

    public static FlowingFluid[] STILL_FLUIDS = new FlowingFluid[]{
            CANOLA_OIL,
            METHANOL,
            CANOLA_METHANOL_MIX,
            GLYCERIN,
            BIO_DIESEL
    };
}
