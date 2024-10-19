package de.maxhenkel.car.entity.car.parts;

import com.mojang.math.Axis;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.model.CarRenderState;
import de.maxhenkel.corelib.client.obj.OBJModel;
import de.maxhenkel.corelib.client.obj.OBJModelInstance;
import de.maxhenkel.corelib.client.obj.OBJModelOptions;
import de.maxhenkel.corelib.math.Rotation;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class PartModel extends Part {

    protected OBJModel model;
    protected ResourceLocation texture;
    protected Vector3d offset;
    protected Rotation rotation;

    public PartModel(OBJModel model, ResourceLocation texture, Vector3d offset, Rotation rotation) {
        this.model = model;
        this.texture = texture;
        this.offset = offset;
        this.rotation = new Rotation(-90F, Axis.XP).add(rotation);
    }

    public PartModel(OBJModel model, ResourceLocation texture, Vector3d offset) {
        this(model, texture, offset, null);
    }

    public PartModel(OBJModel model, ResourceLocation texture) {
        this(model, texture, new Vector3d(0D, 0D, 0D), null);
    }

    public OBJModel getModel() {
        return model;
    }

    public List<OBJModelInstance<CarRenderState>> getInstances(EntityGenericCar car) {
        List<OBJModelInstance<CarRenderState>> list = new ArrayList<>();
        list.add(new OBJModelInstance<>(model, new OBJModelOptions<>(texture, offset, rotation)));
        onPartAdd(list);
        return list;
    }

    protected void onPartAdd(List<OBJModelInstance<CarRenderState>> list) {

    }

}
