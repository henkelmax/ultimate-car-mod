package de.maxhenkel.car.entity.model;

import de.maxhenkel.car.Main;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class TestCarModel extends EntityOBJModel{

    public TestCarModel(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected ResourceLocation[] getEntityModels() {
        return new ResourceLocation[]{new ResourceLocation(Main.MODID, "entity/watch.obj")};
    }

    @Override
    protected boolean preRender(Entity entity, int model, BufferBuilder buffer, double x, double y, double z, float entityYaw, float partialTicks) {
        return true;
    }
}