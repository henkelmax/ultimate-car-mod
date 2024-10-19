package de.maxhenkel.car.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class BlockAsphalt extends BlockBase {

    public BlockAsphalt(Properties properties) {
        super(properties.mapColor(MapColor.COLOR_BLACK).strength(2.2F, 20F).sound(SoundType.STONE));
    }

}
