package de.maxhenkel.car.blocks.liquid;

import de.maxhenkel.car.fluids.ModFluids;
import net.minecraftforge.fluids.Fluid;

public class BlockGlycerin extends FluidBlockBase {

	public BlockGlycerin() {
		super("glycerin");
	}

	@Override
	public Fluid getFluid() {
		return ModFluids.BIO_DIESEL;
	}

}
