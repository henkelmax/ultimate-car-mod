package de.maxhenkel.car.entity.model;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.model.obj.OBJModelPart;
import de.maxhenkel.car.entity.model.obj.OBJModelRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class TestCarModel extends OBJModelRenderer {

    public TestCarModel(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public OBJModelPart[] getModels() {
        return new OBJModelPart[]{
                new OBJModelPart(
                        new ResourceLocation(Main.MODID, "entity/wheel.obj"),
                        new ResourceLocation(Main.MODID, "textures/entity/wheel.png"),
                        new Vec3d(9.5F/16F,4F/16F,8F/16F)).setSpeedRotationFactor(80F),
                new OBJModelPart(
                        new ResourceLocation(Main.MODID, "entity/wheel.obj"),
                        new ResourceLocation(Main.MODID, "textures/entity/wheel.png"),
                        new Vec3d(9.5F/16F,4F/16F,-8F/16F)).setSpeedRotationFactor(80F),
                new OBJModelPart(
                        new ResourceLocation(Main.MODID, "entity/wheel.obj"),
                        new ResourceLocation(Main.MODID, "textures/entity/wheel.png"),
                        new Vec3d(-9.5F/16F,4F/16F,8F/16F)).setSpeedRotationFactor(80F),
                new OBJModelPart(
                        new ResourceLocation(Main.MODID, "entity/wheel.obj"),
                        new ResourceLocation(Main.MODID, "textures/entity/wheel.png"),
                        new Vec3d(-9.5F/16F,4F/16F,-8F/16F)).setSpeedRotationFactor(80F),
                new OBJModelPart(
                        new ResourceLocation(Main.MODID, "entity/woodcar.obj"),
                        new ResourceLocation(Main.MODID, "textures/entity/oak_wood.png"),
                        new Vec3d(0,8.5F/16F,0))
        };
    }

    @Override
    public void translateNumberPlate() {

    }

    @Override
    public float getHeightOffset() {
        return 0;
    }
}