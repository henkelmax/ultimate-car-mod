package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.math.Vec3d;
import java.util.List;

public class PartChassis extends PartModel{

    protected Vec3d[] wheelOffsets;


    public PartChassis(OBJModel model, Vec3d offset, Vec3d[] wheelOffsets) {
        super(model, offset);
        this.wheelOffsets = wheelOffsets;
    }

    public Vec3d[] getWheelOffsets() {
        return wheelOffsets;
    }

    @Override
    public boolean isValid(List<Part> parts) {
        if(!checkAmount(parts, 4, 4, part -> part instanceof PartWheels)){
            return false;
        }

        if(!checkAmount(parts, 1, 1, part -> part instanceof PartEngine)){
            return false;
        }

        if(!checkAmount(parts, 0, 1, part -> part instanceof PartNumberPlate)){
            return false;
        }

        if(!checkAmount(parts, 0, 1, part -> part instanceof PartBumper)){
            return false;
        }

        return true;
    }
}
