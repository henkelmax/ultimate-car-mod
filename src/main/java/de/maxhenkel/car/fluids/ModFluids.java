package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class ModFluids {

    private static final DeferredRegister<Fluid> FLUID_REGISTER = DeferredRegister.create(ForgeRegistries.FLUIDS, Main.MODID);

    public static final RegistryObject<FluidCanolaOil> CANOLA_OIL = FLUID_REGISTER.register("canola_oil", () -> new FluidCanolaOil());
    public static final RegistryObject<FluidCanolaOilFlowing> CANOLA_OIL_FLOWING = FLUID_REGISTER.register("canola_oil_flowing", () -> new FluidCanolaOilFlowing());
    public static final RegistryObject<FluidMethanol> METHANOL = FLUID_REGISTER.register("methanol", () -> new FluidMethanol());
    public static final RegistryObject<FluidMethanolFlowing> METHANOL_FLOWING = FLUID_REGISTER.register("methanol_flowing", () -> new FluidMethanolFlowing());
    public static final RegistryObject<FluidCanolaMethanolMix> CANOLA_METHANOL_MIX = FLUID_REGISTER.register("canola_methanol_mix", () -> new FluidCanolaMethanolMix());
    public static final RegistryObject<FluidCanolaMethanolMixFlowing> CANOLA_METHANOL_MIX_FLOWING = FLUID_REGISTER.register("canola_methanol_mix_flowing", () -> new FluidCanolaMethanolMixFlowing());
    public static final RegistryObject<FluidGlycerin> GLYCERIN = FLUID_REGISTER.register("glycerin", () -> new FluidGlycerin());
    public static final RegistryObject<FluidGlycerinFlowing> GLYCERIN_FLOWING = FLUID_REGISTER.register("glycerin_flowing", () -> new FluidGlycerinFlowing());
    public static final RegistryObject<FluidBioDiesel> BIO_DIESEL = FLUID_REGISTER.register("bio_diesel", () -> new FluidBioDiesel());
    public static final RegistryObject<FluidBioDieselFlowing> BIO_DIESEL_FLOWING = FLUID_REGISTER.register("bio_diesel_flowing", () -> new FluidBioDieselFlowing());

    private static final DeferredRegister<FluidType> FLUID_TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Main.MODID);

    public static final RegistryObject<FluidType> CANOLA_OIL_TYPE = FLUID_TYPE_REGISTER.register("canola_oil", () ->
            new FluidTypeCar("block.car.canola_oil", new ResourceLocation(Main.MODID, "block/canola_oil_still"), new ResourceLocation(Main.MODID, "block/canola_oil_flowing"))
    );
    public static final RegistryObject<FluidType> METHANOL_TYPE = FLUID_TYPE_REGISTER.register("methanol", () ->
            new FluidTypeCar("block.car.methanol", new ResourceLocation(Main.MODID, "block/methanol_still"), new ResourceLocation(Main.MODID, "block/methanol_flowing"))
    );
    public static final RegistryObject<FluidType> CANOLA_METHANOL_MIX_TYPE = FLUID_TYPE_REGISTER.register("canola_methanol_mix", () ->
            new FluidTypeCar("block.car.canola_methanol_mix", new ResourceLocation(Main.MODID, "block/canola_methanol_mix_still"), new ResourceLocation(Main.MODID, "block/canola_methanol_mix_flowing"))
    );
    public static final RegistryObject<FluidType> GLYCERIN_TYPE = FLUID_TYPE_REGISTER.register("glycerin", () ->
            new FluidTypeCar("block.car.glycerin", new ResourceLocation(Main.MODID, "block/glycerin_still"), new ResourceLocation(Main.MODID, "block/glycerin_flowing"))
    );
    public static final RegistryObject<FluidType> BIO_DIESEL_TYPE = FLUID_TYPE_REGISTER.register("bio_diesel", () ->
            new FluidTypeCar("block.car.bio_diesel", new ResourceLocation(Main.MODID, "block/bio_diesel_still"), new ResourceLocation(Main.MODID, "block/bio_diesel_flowing"))
    );

    public static void init() {
        FLUID_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        FLUID_TYPE_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
