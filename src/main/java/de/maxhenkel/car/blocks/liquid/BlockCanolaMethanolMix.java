package de.maxhenkel.car.blocks.liquid;

import de.maxhenkel.car.fluids.ModFluids;
import net.minecraftforge.fluids.Fluid;

public class BlockCanolaMethanolMix extends FluidBlockBase {

    public BlockCanolaMethanolMix() {
        super("canola_methanol_mix");
    }

    @Override
    public Fluid getFluid() {
        return ModFluids.BIO_DIESEL;
    }

}
