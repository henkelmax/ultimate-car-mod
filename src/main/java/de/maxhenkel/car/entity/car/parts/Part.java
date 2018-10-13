package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import de.maxhenkel.car.entity.model.obj.OBJModelInstance;

import java.util.List;

public abstract class Part {

    protected OBJModel model;

    public Part(OBJModel model) {
        this.model = model;
    }

    public OBJModel getModel() {
        return model;
    }

    public abstract List<OBJModelInstance> getInstances(EntityGenericCar car);
}
