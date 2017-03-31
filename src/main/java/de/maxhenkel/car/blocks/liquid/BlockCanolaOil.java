package de.maxhenkel.car.blocks.liquid;

import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockCanolaOil extends BlockFluidClassic {

	public BlockCanolaOil() {
		super(ModFluids.CANOLA_OIL, Material.WATER);
		setRegistryName("canola_oil");
		setUnlocalizedName("canola_oil");
	}

}
