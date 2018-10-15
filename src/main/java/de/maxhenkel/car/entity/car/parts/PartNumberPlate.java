package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.util.vector.Quaternion;

public class PartNumberPlate extends PartModel {

    protected Vec3d numberPlateTextOffset;

    public PartNumberPlate(OBJModel model, Vec3d offset, Quaternion rotation, Vec3d numberPlateTextOffset) {
        super(model, offset, rotation);
        this.numberPlateTextOffset = numberPlateTextOffset;
    }

    public Vec3d getNumberPlateTextOffset() {
        return numberPlateTextOffset;
    }
}
