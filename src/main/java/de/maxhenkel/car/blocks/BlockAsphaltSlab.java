package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.corelib.block.IItemBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class BlockAsphaltSlab extends SlabBlock implements IItemBlock {

    public BlockAsphaltSlab() {
        super(Properties.create(Material.ROCK, MaterialColor.OBSIDIAN).hardnessAndResistance(2.2F, 20F).sound(SoundType.STONE));
        setRegistryName(new ResourceLocation(Main.MODID, "asphalt_slab"));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ModItemGroups.TAB_CAR)).setRegistryName(this.getRegistryName());
    }
}