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

        List<PartWheels> wheels=new ArrayList<>();

        for (Part part : car.getModelParts()) {
            if (part instanceof PartWheels) {
                wheels.add((PartWheels) part);
            }
        }

        for (int i=0; i<wheelOffsets.length&&i<wheels.size(); i++) {
            list.add(new OBJModelInstance(wheels.get(i).model, new OBJModelOptions(wheelOffsets[i], rotationModifier)));
        }

        return list;
    }
}
