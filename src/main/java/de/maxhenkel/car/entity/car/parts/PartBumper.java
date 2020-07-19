package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.corelib.client.obj.OBJModel;
import de.maxhenkel.corelib.client.obj.OBJModelInstance;
import de.maxhenkel.corelib.client.obj.OBJModelOptions;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class PartBumper extends PartModel {

    public PartBumper(ResourceLocation texture) {
        super(new OBJModel(new ResourceLocation(Main.MODID, "models/entity/wood_bumper.obj")), texture);
    }

    @Override
    public List<OBJModelInstance> getInstances(EntityGenericCar car) {
        PartBodyWoodBase chassis = car.getPartByClass(PartBodyWoodBase.class);

        if (chassis == null) {
            return super.getInstances(car);
        }

        List<OBJModelInstance> list = new ArrayList<>();
        list.add(new OBJModelInstance(model, new OBJModelOptions(texture, chassis.getBumperOffset(), rotation)));
        onPartAdd(list);
        return list;
    }

    @Override
    public boolean validate(List<Part> parts, List<ITextComponent> messages) {

        if (Part.getAmount(parts, part -> part instanceof PartBodyWoodBase) != 1) {
            messages.add(new TranslationTextComponent("message.parts.no_body_for_bumper"));
            return false;
        }

        return true;
    }

}
