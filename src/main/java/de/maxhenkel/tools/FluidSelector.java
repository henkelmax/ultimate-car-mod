package de.maxhenkel.tools;

import javax.annotation.Nullable;

import net.minecraftforge.fluids.Fluid;

public class FluidSelector implements Selector<Fluid> {

    private Fluid fluid;

    public FluidSelector(Fluid fluid) {
        this.fluid = fluid;
    }

    public Fluid getFluid() {
        return fluid;
    }

    @Nullable
    public static FluidSelector fromString(String str) {
        Fluid fluid = FluidStackWrapper.byName(str);
        if (fluid == null) {
            return null;
        }

        return new FluidSelector(fluid);
    }

    public String toString() {
        return FluidStackWrapper.getName(fluid);
    }

    @Override
    public boolean isValid(Fluid f) {
        if (fluid == null || f == null) {
            return false;
        }
        return fluid.equals(f);
    }

}
