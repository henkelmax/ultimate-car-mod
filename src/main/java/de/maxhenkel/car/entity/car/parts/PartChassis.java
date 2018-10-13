package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import de.maxhenkel.car.entity.model.obj.OBJModelInstance;
import de.maxhenkel.car.entity.model.obj.OBJModelOptions;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.List;

public class PartChassis extends Part{

    protected Vec3d[] wheelOffsets;
    protected Vec3d offset;

    public PartChassis(OBJModel model, Vec3d offset, Vec3d[] wheelOffsets) {
        super(model);
        this.offset=offset;
        this.wheelOffsets = wheelOffsets;
    }

    public Vec3d[] getWheelOffsets() {
        return wheelOffsets;
    }

    @Override
    public List<OBJModelInstance> getInstances(EntityGenericCar car) {
        List<OBJModelInstance> list=new ArrayList<>();
        list.add(new OBJModelInstance(model, new OBJModelOptions(offset)));
        return list;
    }
}
