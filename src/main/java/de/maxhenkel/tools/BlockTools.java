package de.maxhenkel.tools;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class BlockTools {

    public static VoxelShape combine(VoxelShape... shapes) {
        if (shapes.length <= 0) {
            return VoxelShapes.empty();
        }
        VoxelShape combined = shapes[0];

        for (int i = 1; i < shapes.length; i++) {
            combined = VoxelShapes.or(combined, shapes[i]);
        }

        return combined;
    }

}
