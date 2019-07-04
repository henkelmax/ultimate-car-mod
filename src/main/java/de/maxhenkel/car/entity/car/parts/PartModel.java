package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import de.maxhenkel.car.entity.model.obj.OBJModelInstance;
import de.maxhenkel.car.entity.model.obj.OBJModelOptions;
import de.maxhenkel.tools.Rotation;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.List;

public class PartModel extends Part{

    protected OBJModel model;
    protected Vec3d offset;
    protected Rotation rotation;

    public PartModel(OBJModel model, Vec3d offset, Rotation rotation) {
        this.model = model;
        this.offset = offset;
        this.rotation=rotation;
    }

    public PartModel(OBJModel model, Vec3d offset) {
        this(model, offset, null);
    }

    public PartModel(OBJModel model) {
        this(model, new Vec3d(0D, 0D, 0D), null);
    }

    public OBJModel getModel() {
        return model;
    }

    public List<OBJModelInstance> getInstances(EntityGenericCar car) {
        List<OBJModelInstance> list=new ArrayList<>();
        list.add(new OBJModelInstance(model, new OBJModelOptions(offset, rotation, 0F)));
        onPartAdd(list);
        return list;
    }

    protected void onPartAdd(List<OBJModelInstance> list){

    }

}
