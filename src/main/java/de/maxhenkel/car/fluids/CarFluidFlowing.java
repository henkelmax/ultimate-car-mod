package de.maxhenkel.car.fluids;

import net.minecraftforge.fluids.ForgeFlowingFluid;

public abstract class CarFluidFlowing extends ForgeFlowingFluid.Flowing implements IEffectApplyable {

    public CarFluidFlowing(Properties properties) {
        super(properties);
    }
}

