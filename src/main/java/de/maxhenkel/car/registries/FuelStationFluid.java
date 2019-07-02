package de.maxhenkel.car.registries;

import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.tools.FluidSelector;
import net.minecraftforge.fluids.Fluid;

public class FuelStationFluid {

    public static final StringRegistry<FuelStationFluid> REGISTRY = new StringRegistry<>();

    //TODO remove registries
    static {
        REGISTRY.register("bio_diesel", new FuelStationFluid(ModFluids.BIO_DIESEL));
    }

    private FluidSelector input;

    public FuelStationFluid(Fluid input) {
        this.input = new FluidSelector(input);
    }

    public FuelStationFluid(FluidSelector input) {
        this.input = input;
    }

    public FluidSelector getInput() {
        return input;
    }

}
