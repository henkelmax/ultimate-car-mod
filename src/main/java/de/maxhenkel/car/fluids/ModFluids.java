package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluids {

    private static final DeferredRegister<Fluid> FLUID_REGISTER = DeferredRegister.create(BuiltInRegistries.FLUID, Main.MODID);

    public static final DeferredHolder<Fluid, FluidCanolaOil> CANOLA_OIL = FLUID_REGISTER.register("canola_oil", () -> new FluidCanolaOil());
    public static final DeferredHolder<Fluid, FluidCanolaOilFlowing> CANOLA_OIL_FLOWING = FLUID_REGISTER.register("canola_oil_flowing", () -> new FluidCanolaOilFlowing());
    public static final DeferredHolder<Fluid, FluidMethanol> METHANOL = FLUID_REGISTER.register("methanol", () -> new FluidMethanol());
    public static final DeferredHolder<Fluid, FluidMethanolFlowing> METHANOL_FLOWING = FLUID_REGISTER.register("methanol_flowing", () -> new FluidMethanolFlowing());
    public static final DeferredHolder<Fluid, FluidCanolaMethanolMix> CANOLA_METHANOL_MIX = FLUID_REGISTER.register("canola_methanol_mix", () -> new FluidCanolaMethanolMix());
    public static final DeferredHolder<Fluid, FluidCanolaMethanolMixFlowing> CANOLA_METHANOL_MIX_FLOWING = FLUID_REGISTER.register("canola_methanol_mix_flowing", () -> new FluidCanolaMethanolMixFlowing());
    public static final DeferredHolder<Fluid, FluidGlycerin> GLYCERIN = FLUID_REGISTER.register("glycerin", () -> new FluidGlycerin());
    public static final DeferredHolder<Fluid, FluidGlycerinFlowing> GLYCERIN_FLOWING = FLUID_REGISTER.register("glycerin_flowing", () -> new FluidGlycerinFlowing());
    public static final DeferredHolder<Fluid, FluidBioDiesel> BIO_DIESEL = FLUID_REGISTER.register("bio_diesel", () -> new FluidBioDiesel());
    public static final DeferredHolder<Fluid, FluidBioDieselFlowing> BIO_DIESEL_FLOWING = FLUID_REGISTER.register("bio_diesel_flowing", () -> new FluidBioDieselFlowing());

    private static final DeferredRegister<FluidType> FLUID_TYPE_REGISTER = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, Main.MODID);

    public static final DeferredHolder<FluidType, FluidType> CANOLA_OIL_TYPE = FLUID_TYPE_REGISTER.register("canola_oil", () ->
            new FluidTypeCar("block.car.canola_oil", new ResourceLocation(Main.MODID, "block/canola_oil_still"), new ResourceLocation(Main.MODID, "block/canola_oil_flowing"))
    );
    public static final DeferredHolder<FluidType, FluidType> METHANOL_TYPE = FLUID_TYPE_REGISTER.register("methanol", () ->
            new FluidTypeCar("block.car.methanol", new ResourceLocation(Main.MODID, "block/methanol_still"), new ResourceLocation(Main.MODID, "block/methanol_flowing"))
    );
    public static final DeferredHolder<FluidType, FluidType> CANOLA_METHANOL_MIX_TYPE = FLUID_TYPE_REGISTER.register("canola_methanol_mix", () ->
            new FluidTypeCar("block.car.canola_methanol_mix", new ResourceLocation(Main.MODID, "block/canola_methanol_mix_still"), new ResourceLocation(Main.MODID, "block/canola_methanol_mix_flowing"))
    );
    public static final DeferredHolder<FluidType, FluidType> GLYCERIN_TYPE = FLUID_TYPE_REGISTER.register("glycerin", () ->
            new FluidTypeCar("block.car.glycerin", new ResourceLocation(Main.MODID, "block/glycerin_still"), new ResourceLocation(Main.MODID, "block/glycerin_flowing"))
    );
    public static final DeferredHolder<FluidType, FluidType> BIO_DIESEL_TYPE = FLUID_TYPE_REGISTER.register("bio_diesel", () ->
            new FluidTypeCar("block.car.bio_diesel", new ResourceLocation(Main.MODID, "block/bio_diesel_still"), new ResourceLocation(Main.MODID, "block/bio_diesel_flowing"))
    );

    public static void init(IEventBus eventBus) {
        FLUID_REGISTER.register(eventBus);
        FLUID_TYPE_REGISTER.register(eventBus);
    }

}
