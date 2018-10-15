package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import de.maxhenkel.car.entity.model.obj.OBJModelInstance;
import de.maxhenkel.car.entity.model.obj.OBJModelOptions;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class PartWheels extends PartModel {

    protected float rotationModifier;

    public PartWheels(OBJModel model, float rotationModifier) {
        super(model);
        this.rotationModifier = rotationModifier;
    }

    @Override
    public List<OBJModelInstance> getInstances(EntityGenericCar car) {
        List<OBJModelInstance> list = new ArrayList<>();

        Vec3d[] wheelOffsets = new Vec3d[0];

        for (Part part : car.getModelParts()) {
            if (part instanceof PartChassis) {
                wheelOffsets = ((PartChassis) part).getWheelOffsets();
            }
        }

        for (Vec3d vec3d : wheelOffsets) {
            list.add(new OBJModelInstance(model, new OBJModelOptions(vec3d, rotationModifier)));
        }

        return list;
    }
}
