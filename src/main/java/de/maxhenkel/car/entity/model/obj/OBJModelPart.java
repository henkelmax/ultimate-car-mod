package de.maxhenkel.car.entity.model.obj;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;
import org.lwjgl.util.vector.Quaternion;

public class OBJModelPart {

    private OBJModel objModel;
    private ResourceLocation model;
    private ResourceLocation texture;
    private Vec3d offset;
    private Quaternion rotation;
    private float speedRotationFactor;
    private boolean culling;

    public OBJModelPart(ResourceLocation model, ResourceLocation texture, Vec3d offset, Quaternion rotation) {
        this.model = model;
        this.texture = texture;
        this.offset = offset;
        this.rotation = rotation;
        this.speedRotationFactor = 0F;
        this.culling=true;
    }

    public OBJModelPart(ResourceLocation model, ResourceLocation texture, Vec3d offset) {
        this(model, texture, offset, null);
    }

    public OBJModel getModel() {
        if (objModel == null) {
            try {
                this.objModel = (OBJModel) OBJLoader.INSTANCE.loadModel(model);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
            //this.objModel = (OBJModel) ModelLoaderRegistry.getModelOrLogError(model, "Error loading Model");
        }
        return this.objModel;
    }

    public float getSpeedRotationFactor() {
        return speedRotationFactor;
    }

    public OBJModelPart setSpeedRotationFactor(float speedRotationFactor) {
        this.speedRotationFactor = speedRotationFactor;
        return this;
    }

    public boolean hasCulling() {
        return culling;
    }

    public OBJModelPart setCulling(boolean culling) {
        this.culling = culling;
        return this;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public Vec3d getOffset() {
        return offset;
    }

    public Quaternion getRotation() {
        return rotation;
    }
}
