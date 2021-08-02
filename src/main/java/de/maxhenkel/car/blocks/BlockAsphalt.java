package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.corelib.block.IItemBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class BlockAsphalt extends BlockBase implements IItemBlock {

    public BlockAsphalt() {
        super(Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(2.2F, 20F).sound(SoundType.STONE));
        setRegistryName(new ResourceLocation(Main.MODID, "asphalt"));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().tab(ModItemGroups.TAB_CAR)).setRegistryName(getRegistryName());
    }
}
