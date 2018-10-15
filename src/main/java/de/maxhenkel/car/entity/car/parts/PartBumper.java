package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.util.vector.Quaternion;

public class PartBumper extends PartModel {

    public PartBumper(OBJModel model, Vec3d offset, Quaternion rotation) {
        super(model, offset, rotation);
    }
}
