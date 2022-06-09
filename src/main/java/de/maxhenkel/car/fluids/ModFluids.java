package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {

    private static final DeferredRegister<Fluid> FLUID_REGISTER = DeferredRegister.create(ForgeRegistries.FLUIDS, Main.MODID);

    public static RegistryObject<FluidCanolaOil> CANOLA_OIL = FLUID_REGISTER.register("canola_oil", () -> new FluidCanolaOil());
    public static RegistryObject<FluidCanolaOilFlowing> CANOLA_OIL_FLOWING = FLUID_REGISTER.register("canola_oil_flowing", () -> new FluidCanolaOilFlowing());
    public static RegistryObject<FluidMethanol> METHANOL = FLUID_REGISTER.register("methanol", () -> new FluidMethanol());
    public static RegistryObject<FluidMethanolFlowing> METHANOL_FLOWING = FLUID_REGISTER.register("methanol_flowing", () -> new FluidMethanolFlowing());
    public static RegistryObject<FluidCanolaMethanolMix> CANOLA_METHANOL_MIX = FLUID_REGISTER.register("canola_methanol_mix", () -> new FluidCanolaMethanolMix());
    public static RegistryObject<FluidCanolaMethanolMixFlowing> CANOLA_METHANOL_MIX_FLOWING = FLUID_REGISTER.register("canola_methanol_mix_flowing", () -> new FluidCanolaMethanolMixFlowing());
    public static RegistryObject<FluidGlycerin> GLYCERIN = FLUID_REGISTER.register("glycerin", () -> new FluidGlycerin());
    public static RegistryObject<FluidGlycerinFlowing> GLYCERIN_FLOWING = FLUID_REGISTER.register("glycerin_flowing", () -> new FluidGlycerinFlowing());
    public static RegistryObject<FluidBioDiesel> BIO_DIESEL = FLUID_REGISTER.register("bio_diesel", () -> new FluidBioDiesel());
    public static RegistryObject<FluidBioDieselFlowing> BIO_DIESEL_FLOWING = FLUID_REGISTER.register("bio_diesel_flowing", () -> new FluidBioDieselFlowing());

    public static void init() {
        FLUID_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
