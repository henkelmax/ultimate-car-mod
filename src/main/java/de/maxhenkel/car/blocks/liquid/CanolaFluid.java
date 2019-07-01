package de.maxhenkel.car.blocks.liquid;

import de.maxhenkel.car.Main;
import net.minecraft.fluid.IFluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.util.ResourceLocation;

public class CanolaFluid extends WaterFluid {
    public CanolaFluid() {
        setRegistryName(new ResourceLocation(Main.MODID, "canola"));
    }

    @Override
    public boolean isSource(IFluidState state) {
        return state.isSource();
    }

    @Override
    public int getLevel(IFluidState state) {
        return state.getLevel();
    }
}
