package de.maxhenkel.car.fluids;

import net.neoforged.neoforge.fluids.BaseFlowingFluid;

public abstract class CarFluidSource extends BaseFlowingFluid.Source implements IEffectApplyable {

    public CarFluidSource(Properties properties) {
        super(properties);
    }
}

