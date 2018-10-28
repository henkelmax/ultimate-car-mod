package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import de.maxhenkel.car.entity.model.obj.OBJModelInstance;
import de.maxhenkel.car.entity.model.obj.OBJModelOptions;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.util.vector.Quaternion;
import java.util.ArrayList;
import java.util.List;

public class PartLicensePlateHolder extends PartModel {

    protected Vec3d textOffset;

    public PartLicensePlateHolder(OBJModel model, Quaternion rotation, Vec3d textOffset) {
        super(model, new Vec3d(0D, 0D, 0D), rotation);
        this.textOffset=textOffset;
    }

    public Vec3d getTextOffset() {
        return textOffset;
    }

    @Override
    public List<OBJModelInstance> getInstances(EntityGenericCar car) {
        PartBody chassis = car.getPartByClass(PartBody.class);

        if (chassis == null) {
            return super.getInstances(car);
        }

        List<OBJModelInstance> list = new ArrayList<>();
        list.add(new OBJModelInstance(model, new OBJModelOptions(chassis.getNumberPlateOffset(), rotation, 0F)));
        onPartAdd(list);
        return list;
    }
}
