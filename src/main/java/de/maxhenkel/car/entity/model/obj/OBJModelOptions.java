package de.maxhenkel.car.entity.model.obj;

import net.minecraft.util.math.Vec3d;
import org.lwjgl.util.vector.Quaternion;

public class OBJModelOptions {

    private Vec3d offset;
    private Quaternion rotation;
    private float speedRotationFactor;

    public OBJModelOptions(Vec3d offset, Quaternion rotation, float speedRotationFactor) {
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

    public Quaternion getRotation() {
        return rotation;
    }

    public OBJModelOptions setRotation(Quaternion rotation) {
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
