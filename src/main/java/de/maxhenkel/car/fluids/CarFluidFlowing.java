package de.maxhenkel.car.fluids;

import net.neoforged.neoforge.fluids.BaseFlowingFluid;

public abstract class CarFluidFlowing extends BaseFlowingFluid.Flowing implements IEffectApplyable {

    public CarFluidFlowing(Properties properties) {
        super(properties);
    }
}

