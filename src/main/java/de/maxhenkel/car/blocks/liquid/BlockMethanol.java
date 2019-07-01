package de.maxhenkel.car.blocks.liquid;

import de.maxhenkel.car.fluids.ModFluids;
import net.minecraftforge.fluids.Fluid;

public class BlockMethanol extends FluidBlockBase {

	public BlockMethanol() {
		super("methanol");
	}

	@Override
	public Fluid getFluid() {
		return ModFluids.BIO_DIESEL;
	}

}
