package de.maxhenkel.car.blocks;

import de.maxhenkel.car.IDrivable;
import de.maxhenkel.car.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockTar extends Block implements IDrivable{

	public BlockTar() {
		super(Material.ROCK, MapColor.OBSIDIAN);
		setUnlocalizedName("tar");
		setRegistryName("tar");
		setHardness(2.2F);
		setResistance(20.0F);
		setSoundType(SoundType.STONE);
		setCreativeTab(ModCreativeTabs.TAB_CAR);
	}

	@Override
	public float getSpeedModifier() {
		return 1.0F;
	}
	
}
