package de.maxhenkel.car.config;

import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.corelib.config.DynamicConfig;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FuelConfig extends DynamicConfig {

    private Map<Fluid, Fuel> fuels;

    public FuelConfig() {
        fuels = new HashMap<>();
    }

    @Override
    protected void setDefaults() {
        super.setDefaults();
        addFuel(ModFluids.BIO_DIESEL.get(), 100);
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        fuels = getFuelsInternal();
    }

    private void addFuel(Fluid fluid, int efficiency) {
        String name = ForgeRegistries.FLUIDS.getKey(fluid).toString();
        setObject(name, new Fuel(name, efficiency));
    }

    private Map<Fluid, Fuel> getFuelsInternal() {
        return getSubValues().stream().map(s -> getObject(s, () -> new Fuel(s))).filter(fuel -> fuel.getFluid() != Fluids.EMPTY).collect(Collectors.toMap(Fuel::getFluid, fuel -> fuel));
    }

    public Map<Fluid, Fuel> getFuels() {
        return fuels;
    }

}
