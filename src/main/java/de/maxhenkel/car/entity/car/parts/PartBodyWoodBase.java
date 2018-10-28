package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.math.Vec3d;

public abstract class PartBodyWoodBase extends PartBody {

    protected Vec3d bumperOffset;

    public PartBodyWoodBase(OBJModel model, Vec3d offset) {
        super(model, offset);
    }

    public Vec3d getBumperOffset() {
        return bumperOffset;
    }
}
