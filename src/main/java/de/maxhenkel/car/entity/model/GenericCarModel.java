package de.maxhenkel.car.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.model.obj.OBJModelInstance;
import de.maxhenkel.car.entity.model.obj.OBJModelRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class GenericCarModel extends OBJModelRenderer {

    public GenericCarModel(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public List<OBJModelInstance> getModels(EntityGenericCar entity) {
        return entity.getModels();
    }

    @Override
    public void translateLicensePlate(EntityGenericCar entity, MatrixStack matrixStack) {
        Vector3d offset = entity.getLicensePlateOffset();
        matrixStack.translate(offset.x, offset.y, offset.z);
    }
}