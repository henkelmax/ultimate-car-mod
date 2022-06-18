package de.maxhenkel.car.fluids;

import net.minecraftforge.fluids.ForgeFlowingFluid;

public abstract class CarFluidSource extends ForgeFlowingFluid.Source implements IEffectApplyable {

    public CarFluidSource(Properties properties) {
        super(properties);
    }
}

