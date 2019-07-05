package de.maxhenkel.car.entity.model.obj;

import de.maxhenkel.tools.Rotation;
import net.minecraft.util.math.Vec3d;

public class OBJModelOptions {

    private Vec3d offset;
    private Rotation rotation;
    private RenderListener onRender;

    public OBJModelOptions(Vec3d offset, Rotation rotation, RenderListener onRender) {
        this.offset = offset;
        this.rotation = rotation;
        this.onRender=onRender;
    }

    public OBJModelOptions(Vec3d offset, Rotation rotation) {
        this(offset, rotation, null);
    }

    public OBJModelOptions(Vec3d offset) {
        this(offset, null, null);
    }

    public OBJModelOptions(Vec3d offset, RenderListener onRender) {
        this(offset, null, onRender);
    }

    public OBJModelOptions() {
        this(new Vec3d(0D, 0D, 0D), null, null);
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

    public RenderListener getOnRender() {
        return onRender;
    }

    public static interface RenderListener{
        public void onRender(float partialTicks);
    }
}
