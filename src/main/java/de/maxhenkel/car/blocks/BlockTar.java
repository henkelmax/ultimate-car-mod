package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.tools.IItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class BlockTar extends BlockBase implements IItemBlock {

	public BlockTar() {
		super(Properties.create(Material.ROCK, MaterialColor.OBSIDIAN).hardnessAndResistance(2.2F, 20F).sound(SoundType.STONE));
		setRegistryName(new ResourceLocation(Main.MODID, "tar"));
	}

	@Override
	public Item toItem() {
		return new BlockItem(this, new Item.Properties().group(ModCreativeTabs.TAB_CAR)).setRegistryName(this.getRegistryName());
	}
}
