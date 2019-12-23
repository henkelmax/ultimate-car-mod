package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public abstract class PartBodyWoodBase extends PartBody {

    protected Vec3d bumperOffset;

    public PartBodyWoodBase(OBJModel model, ResourceLocation texture, Vec3d offset) {
        super(model, texture, offset);
    }

    public Vec3d getBumperOffset() {
        return bumperOffset;
    }
}
