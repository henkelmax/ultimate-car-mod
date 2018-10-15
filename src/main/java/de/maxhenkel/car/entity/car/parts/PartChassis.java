package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.math.Vec3d;
import java.util.List;

public class PartChassis extends PartModel {

    protected Vec3d[] wheelOffsets;
    protected Vec3d[] playerOffsets;
    protected Vec3d numberPlateOffset;
    protected float width;
    protected float height;
    protected float minRotationSpeed;

    public PartChassis(OBJModel model, Vec3d offset, Vec3d[] wheelOffsets, Vec3d[] playerOffsets, Vec3d numberPlateOffset, float width, float height, float minRotationSpeed) {
        super(model, offset);
        this.wheelOffsets = wheelOffsets;
        this.playerOffsets=playerOffsets;
        this.numberPlateOffset=numberPlateOffset;
        this.width = width;
        this.height = height;
        this.minRotationSpeed = minRotationSpeed;
    }

    public Vec3d[] getWheelOffsets() {
        return wheelOffsets;
    }

    public Vec3d[] getPlayerOffsets() {
        return playerOffsets;
    }

    public Vec3d getNumberPlateOffset() {
        return numberPlateOffset;
    }

    public float getMinRotationSpeed() {
        return minRotationSpeed;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    protected int getWheelAmount(){
        return 4;
    }

    @Override
    public boolean isValid(List<Part> parts) {
        if (!checkAmount(parts, getWheelAmount(), getWheelAmount(), part -> part instanceof PartWheels)) {
            return false;
        }

        if (!checkAmount(parts, 1, 1, part -> part instanceof PartEngine)) {
            return false;
        }

        if (!checkAmount(parts, 0, 1, part -> part instanceof PartNumberPlate)) {
            return false;
        }

        if (!checkAmount(parts, 0, 1, part -> part instanceof PartBumper)) {
            return false;
        }

        return true;
    }
}
