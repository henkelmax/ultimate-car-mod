package de.maxhenkel.car.config;

import com.electronwill.nightconfig.core.conversion.Path;
import com.electronwill.nightconfig.core.conversion.SpecIntInRange;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class Fuel {

    private transient Fluid fluid;

    @Path("efficiency")
    @SpecIntInRange(min = 0, max = Short.MAX_VALUE)
    private int efficiency;

    public Fuel(String fluid, int efficiency) {
        this(fluid);
        this.efficiency = efficiency;
    }

    public Fuel(Fluid fluid, int efficiency) {
        this.fluid = fluid;
        this.efficiency = efficiency;
    }

    public Fuel(String fluid) {
        this.fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluid));
    }

    public Fluid getFluid() {
        return fluid;
    }

    public int getEfficiency() {
        return efficiency;
    }
}
