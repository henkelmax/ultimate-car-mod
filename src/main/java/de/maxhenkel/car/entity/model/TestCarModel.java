package de.maxhenkel.car.entity.model;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import de.maxhenkel.car.entity.model.obj.OBJModelInstance;
import de.maxhenkel.car.entity.model.obj.OBJModelOptions;
import de.maxhenkel.car.entity.model.obj.OBJModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class TestCarModel extends OBJModelRenderer {

    public TestCarModel(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public OBJModelInstance[] getModels(EntityGenericCar entity) {
        return entity.getModels();
    }

    @Override
    public void translateNumberPlate(EntityGenericCar entity) {
        GlStateManager.translate(entity.getNumberPlateOffsetX(), entity.getNumberPlateOffsetY(), entity.getNumberPlateOffsetZ());
    }
}