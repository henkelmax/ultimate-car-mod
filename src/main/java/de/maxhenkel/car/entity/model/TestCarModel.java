package de.maxhenkel.car.entity.model;

import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.model.obj.OBJModelInstance;
import de.maxhenkel.car.entity.model.obj.OBJModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;

import java.util.List;

public class TestCarModel extends OBJModelRenderer {

    public TestCarModel(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public List<OBJModelInstance> getModels(EntityGenericCar entity) {
        return entity.getModels();
    }

    @Override
    public void translateNumberPlate(EntityGenericCar entity) {
        GlStateManager.translate(entity.getNumberPlateOffsetX(), entity.getNumberPlateOffsetY(), entity.getNumberPlateOffsetZ());
    }
}