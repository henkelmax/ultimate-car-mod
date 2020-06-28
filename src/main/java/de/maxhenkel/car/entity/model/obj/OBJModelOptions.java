package de.maxhenkel.car.entity.model.obj;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.tools.Rotation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class OBJModelOptions {

    private ResourceLocation texture;
    private Vector3d offset;
    private Rotation rotation;
    private RenderListener onRender;

    public OBJModelOptions(ResourceLocation texture, Vector3d offset, Rotation rotation, RenderListener onRender) {
        this.texture = texture;
        this.offset = offset;
        this.rotation = rotation;
        this.onRender = onRender;
    }

    public OBJModelOptions(ResourceLocation texture, Vector3d offset, Rotation rotation) {
        this(texture, offset, rotation, null);
    }

    public OBJModelOptions(ResourceLocation texture, Vector3d offset) {
        this(texture, offset, null, null);
    }

    public OBJModelOptions(ResourceLocation texture, Vector3d offset, RenderListener onRender) {
        this(texture, offset, null, onRender);
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public void setTexture(ResourceLocation texture) {
        this.texture = texture;
    }

    public Vector3d getOffset() {
        return offset;
    }

    public OBJModelOptions setOffset(Vector3d offset) {
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

    public interface RenderListener {
        void onRender(MatrixStack matrixStack, float partialTicks);
    }
}
