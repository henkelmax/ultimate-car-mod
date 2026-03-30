package de.maxhenkel.car.fluids;

import de.maxhenkel.car.CarMod;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.fluid.FluidTintSources;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluids {

    private static final DeferredRegister<Fluid> FLUID_REGISTER = DeferredRegister.create(BuiltInRegistries.FLUID, CarMod.MODID);

    public static final DeferredHolder<Fluid, FluidCanolaOil> CANOLA_OIL = FLUID_REGISTER.register("canola_oil", FluidCanolaOil::new);
    public static final DeferredHolder<Fluid, FluidCanolaOilFlowing> CANOLA_OIL_FLOWING = FLUID_REGISTER.register("canola_oil_flowing", FluidCanolaOilFlowing::new);
    public static final DeferredHolder<Fluid, FluidMethanol> METHANOL = FLUID_REGISTER.register("methanol", FluidMethanol::new);
    public static final DeferredHolder<Fluid, FluidMethanolFlowing> METHANOL_FLOWING = FLUID_REGISTER.register("methanol_flowing", FluidMethanolFlowing::new);
    public static final DeferredHolder<Fluid, FluidCanolaMethanolMix> CANOLA_METHANOL_MIX = FLUID_REGISTER.register("canola_methanol_mix", FluidCanolaMethanolMix::new);
    public static final DeferredHolder<Fluid, FluidCanolaMethanolMixFlowing> CANOLA_METHANOL_MIX_FLOWING = FLUID_REGISTER.register("canola_methanol_mix_flowing", FluidCanolaMethanolMixFlowing::new);
    public static final DeferredHolder<Fluid, FluidGlycerin> GLYCERIN = FLUID_REGISTER.register("glycerin", FluidGlycerin::new);
    public static final DeferredHolder<Fluid, FluidGlycerinFlowing> GLYCERIN_FLOWING = FLUID_REGISTER.register("glycerin_flowing", FluidGlycerinFlowing::new);
    public static final DeferredHolder<Fluid, FluidBioDiesel> BIO_DIESEL = FLUID_REGISTER.register("bio_diesel", FluidBioDiesel::new);
    public static final DeferredHolder<Fluid, FluidBioDieselFlowing> BIO_DIESEL_FLOWING = FLUID_REGISTER.register("bio_diesel_flowing", FluidBioDieselFlowing::new);

    private static final DeferredRegister<FluidType> FLUID_TYPE_REGISTER = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, CarMod.MODID);

    public static final DeferredHolder<FluidType, FluidTypeCar> CANOLA_OIL_TYPE = FLUID_TYPE_REGISTER.register("canola_oil", () ->
            new FluidTypeCar("block.car.canola_oil")
    );
    public static final DeferredHolder<FluidType, FluidTypeCar> METHANOL_TYPE = FLUID_TYPE_REGISTER.register("methanol", () ->
            new FluidTypeCar("block.car.methanol")
    );
    public static final DeferredHolder<FluidType, FluidTypeCar> CANOLA_METHANOL_MIX_TYPE = FLUID_TYPE_REGISTER.register("canola_methanol_mix", () ->
            new FluidTypeCar("block.car.canola_methanol_mix")
    );
    public static final DeferredHolder<FluidType, FluidTypeCar> GLYCERIN_TYPE = FLUID_TYPE_REGISTER.register("glycerin", () ->
            new FluidTypeCar("block.car.glycerin")
    );
    public static final DeferredHolder<FluidType, FluidTypeCar> BIO_DIESEL_TYPE = FLUID_TYPE_REGISTER.register("bio_diesel", () ->
            new FluidTypeCar("block.car.bio_diesel")
    );

    public static void init(IEventBus eventBus) {
        FLUID_REGISTER.register(eventBus);
        FLUID_TYPE_REGISTER.register(eventBus);
    }

}
