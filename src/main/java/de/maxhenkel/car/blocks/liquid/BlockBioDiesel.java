package de.maxhenkel.car.blocks.liquid;

import de.maxhenkel.car.fluids.ModFluids;
import net.minecraftforge.fluids.Fluid;

public class BlockBioDiesel extends FluidBlockBase{

	public BlockBioDiesel() {
		super("bio_diesel");
	}

	@Override
	public Fluid getFluid() {
		return ModFluids.BIO_DIESEL;
	}
}
