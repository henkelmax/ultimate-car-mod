package de.maxhenkel.car.fluids;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.function.Supplier;

public abstract class CarFluidSource extends ForgeFlowingFluid.Source implements IEffectApplyable {

    protected CarFluidSource(FluidAttributes.Builder builder, Supplier<LiquidBlock> block, Supplier<Fluid> still, Supplier<Fluid> flowing, Supplier<? extends Item> bucket) {
        super(new Properties(still, flowing, builder).block(block).bucket(bucket));
    }
}

