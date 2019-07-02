package de.maxhenkel.car.entity.model.obj;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.obj.OBJLoader;

public class OBJModel {

    private net.minecraftforge.client.model.obj.OBJModel objModel;
    private ResourceLocation model;
    private ResourceLocation texture;
    private boolean culling;

    public OBJModel(ResourceLocation model, ResourceLocation texture, boolean culling) {
        this.model = model;
        this.texture = texture;
        this.culling = culling;
    }

    public OBJModel(ResourceLocation model, ResourceLocation texture) {
        this(model, texture, true);
    }

    public net.minecraftforge.client.model.obj.OBJModel getModel() {
        if (objModel == null) {
            try {
                //TODO This is just a hotfix
                OBJLoader.INSTANCE.onResourceManagerReload(Minecraft.getInstance().getResourceManager());
                this.objModel = (net.minecraftforge.client.model.obj.OBJModel) OBJLoader.INSTANCE.loadModel(model);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
            //this.objModel = (OBJModel) ModelLoaderRegistry.getModelOrLogError(model, "Error loading Model");
        }
        return this.objModel;
    }

    public boolean hasCulling() {
        return culling;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

}
