package de.maxhenkel.car.entity.model.obj;

import de.maxhenkel.tools.Rotation;
import net.minecraft.util.math.Vec3d;

public class OBJModelOptions {

    private Vec3d offset;
    private Rotation rotation;
    private float speedRotationFactor;

    public OBJModelOptions(Vec3d offset, Rotation rotation, float speedRotationFactor) {
        this.offset = offset;
        this.rotation = rotation;
        this.speedRotationFactor = speedRotationFactor;
    }

    public OBJModelOptions(Vec3d offset, float speedRotationFactor) {
        this(offset, null, speedRotationFactor);
    }

    public OBJModelOptions(Vec3d offset) {
        this(offset, null, 0F);
    }

    public OBJModelOptions() {
        this(new Vec3d(0D, 0D, 0D), null, 0F);
    }

    public Vec3d getOffset() {
        return offset;
    }

    public OBJModelOptions setOffset(Vec3d offset) {
        this.offset = offset;
        return this;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public OBJModelOptions setRotation(Rotation rotation) {
        this.rotation = rotation;
        return this;
    }

    public float getSpeedRotationFactor() {
        return speedRotationFactor;
    }

    public OBJModelOptions setSpeedRotationFactor(float speedRotationFactor) {
        this.speedRotationFactor = speedRotationFactor;
        return this;
    }
}
