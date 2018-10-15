package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.math.Vec3d;

public class PartChassisTransporter extends PartChassis {

    public PartChassisTransporter(OBJModel model, Vec3d offset, Vec3d[] wheelOffsets, Vec3d[] playerOffsets, Vec3d numberPlateOffset, float width, float height, float minRotationSpeed) {
        super(model, offset, wheelOffsets, playerOffsets, numberPlateOffset, width, height, minRotationSpeed);
    }

    @Override
    protected int getWheelAmount() {
        return 6;
    }
}
