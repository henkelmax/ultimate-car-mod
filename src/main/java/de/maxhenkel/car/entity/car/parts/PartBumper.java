package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.model.CarRenderState;
import de.maxhenkel.corelib.client.obj.OBJModel;
import de.maxhenkel.corelib.client.obj.OBJModelInstance;
import de.maxhenkel.corelib.client.obj.OBJModelOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class PartBumper extends PartModel {

    public PartBumper(ResourceLocation texture) {
        super(new OBJModel(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "models/entity/wood_bumper.obj")), texture);
    }

    @Override
    public List<OBJModelInstance<CarRenderState>> getInstances(EntityGenericCar car) {
        PartBodyWoodBase chassis = car.getPartByClass(PartBodyWoodBase.class);

        if (chassis == null) {
            return super.getInstances(car);
        }

        List<OBJModelInstance<CarRenderState>> list = new ArrayList<>();
        list.add(new OBJModelInstance<>(model, new OBJModelOptions<>(texture, chassis.getBumperOffset(), rotation)));
        onPartAdd(list);
        return list;
    }

    @Override
    public boolean validate(List<Part> parts, List<Component> messages) {

        if (Part.getAmount(parts, part -> part instanceof PartBodyWoodBase) != 1) {
            messages.add(Component.translatable("message.parts.no_body_for_bumper"));
            return false;
        }

        return true;
    }

}
