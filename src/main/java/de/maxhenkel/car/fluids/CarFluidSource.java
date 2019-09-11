package de.maxhenkel.car.fluids;

import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.function.Supplier;

public abstract class CarFluidSource extends ForgeFlowingFluid.Source implements IEffectApplyable {

    protected CarFluidSource(FluidAttributes.Builder builder, Supplier<FlowingFluidBlock> block, Supplier<Fluid> still, Supplier<Fluid> flowing, Supplier<? extends Item> bucket) {
        super(new Properties(still, flowing, builder).block(block).bucket(bucket));
    }
}

