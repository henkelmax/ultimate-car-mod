package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.corelib.client.obj.OBJModel;
import net.minecraft.util.ResourceLocation;

public class PartWheel extends PartWheelBase {

    public PartWheel(OBJModel model, ResourceLocation texture, float rotationModifier, float stepHeight) {
        super(model, texture, rotationModifier, stepHeight);
    }

}
