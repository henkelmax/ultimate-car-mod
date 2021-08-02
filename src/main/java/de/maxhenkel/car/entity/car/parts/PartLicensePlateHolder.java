package de.maxhenkel.car.entity.car.parts;

import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.corelib.client.obj.OBJModel;
import de.maxhenkel.corelib.client.obj.OBJModelInstance;
import de.maxhenkel.corelib.client.obj.OBJModelOptions;
import de.maxhenkel.corelib.math.Rotation;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class PartLicensePlateHolder extends PartModel {

    protected Vector3d textOffset;

    public PartLicensePlateHolder(ResourceLocation texture) {
        super(new OBJModel(new ResourceLocation(Main.MODID, "models/entity/license_plate.obj")), texture, new Vector3d(0D, 0D, 0D), new Rotation(90F, Vector3f.ZP));
        this.textOffset = new Vector3d(0D, -0.5D / 16D, -0.5D / 16D - 0.001D);

    }

    public Vector3d getTextOffset() {
        return textOffset;
    }

    @Override
    public List<OBJModelInstance<EntityGenericCar>> getInstances(EntityGenericCar car) {
        PartBody chassis = car.getPartByClass(PartBody.class);

        if (chassis == null) {
            return super.getInstances(car);
        }

        List<OBJModelInstance<EntityGenericCar>> list = new ArrayList<>();
        list.add(new OBJModelInstance<>(model, new OBJModelOptions<>(texture, chassis.getNumberPlateOffset(), rotation)));
        onPartAdd(list);
        return list;
    }

}
