package de.maxhenkel.car.fluids;

import de.maxhenkel.car.CarMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluids {

    private static final DeferredRegister<Fluid> FLUID_REGISTER = DeferredRegister.create(BuiltInRegistries.FLUID, CarMod.MODID);

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

    private static final DeferredRegister<FluidType> FLUID_TYPE_REGISTER = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, CarMod.MODID);

    public static final DeferredHolder<FluidType, FluidTypeCar> CANOLA_OIL_TYPE = FLUID_TYPE_REGISTER.register("canola_oil", () ->
            new FluidTypeCar("block.car.canola_oil", Identifier.fromNamespaceAndPath(CarMod.MODID, "block/canola_oil_still"), Identifier.fromNamespaceAndPath(CarMod.MODID, "block/canola_oil_flowing"))
    );
    public static final DeferredHolder<FluidType, FluidTypeCar> METHANOL_TYPE = FLUID_TYPE_REGISTER.register("methanol", () ->
            new FluidTypeCar("block.car.methanol", Identifier.fromNamespaceAndPath(CarMod.MODID, "block/methanol_still"), Identifier.fromNamespaceAndPath(CarMod.MODID, "block/methanol_flowing"))
    );
    public static final DeferredHolder<FluidType, FluidTypeCar> CANOLA_METHANOL_MIX_TYPE = FLUID_TYPE_REGISTER.register("canola_methanol_mix", () ->
            new FluidTypeCar("block.car.canola_methanol_mix", Identifier.fromNamespaceAndPath(CarMod.MODID, "block/canola_methanol_mix_still"), Identifier.fromNamespaceAndPath(CarMod.MODID, "block/canola_methanol_mix_flowing"))
    );
    public static final DeferredHolder<FluidType, FluidTypeCar> GLYCERIN_TYPE = FLUID_TYPE_REGISTER.register("glycerin", () ->
            new FluidTypeCar("block.car.glycerin", Identifier.fromNamespaceAndPath(CarMod.MODID, "block/glycerin_still"), Identifier.fromNamespaceAndPath(CarMod.MODID, "block/glycerin_flowing"))
    );
    public static final DeferredHolder<FluidType, FluidTypeCar> BIO_DIESEL_TYPE = FLUID_TYPE_REGISTER.register("bio_diesel", () ->
            new FluidTypeCar("block.car.bio_diesel", Identifier.fromNamespaceAndPath(CarMod.MODID, "block/bio_diesel_still"), Identifier.fromNamespaceAndPath(CarMod.MODID, "block/bio_diesel_flowing"))
    );

    public static void init(IEventBus eventBus) {
        FLUID_REGISTER.register(eventBus);
        FLUID_TYPE_REGISTER.register(eventBus);
    }

}
