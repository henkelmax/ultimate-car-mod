package de.maxhenkel.car.entity.car.parts;

import com.mojang.math.Axis;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.corelib.client.obj.OBJModel;
import de.maxhenkel.corelib.client.obj.OBJModelInstance;
import de.maxhenkel.corelib.client.obj.OBJModelOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class PartWheelBase extends PartModel {

    protected float rotationModifier;
    protected float stepHeight;

    public PartWheelBase(OBJModel model, ResourceLocation texture, float rotationModifier, float stepHeight) {
        super(model, texture);
        this.rotationModifier = rotationModifier;
        this.stepHeight = stepHeight;
    }

    public float getStepHeight() {
        return stepHeight;
    }

    public float getRotationModifier() {
        return rotationModifier;
    }

    @Override
    public boolean validate(List<Part> parts, List<Component> messages) {
        for (Part part : parts) {
            if (part instanceof PartBody) {
                PartBody body = (PartBody) part;
                if (!body.canFitWheel(this)) {
                    messages.add(Component.translatable("message.parts.wrong_wheel_type"));
                    return false;
                }
            }
        }

        return super.validate(parts, messages);
    }

    @Override
    public List<OBJModelInstance<EntityGenericCar>> getInstances(EntityGenericCar car) {
        List<OBJModelInstance<EntityGenericCar>> list = new ArrayList<>();

        Vector3d[] wheelOffsets = new Vector3d[0];

        for (Part part : car.getModelParts()) {
            if (part instanceof PartBody) {
                wheelOffsets = ((PartBody) part).getWheelOffsets();
            }
        }

        List<PartWheelBase> wheels = new ArrayList<>();

        for (Part part : car.getModelParts()) {
            if (part instanceof PartWheelBase) {
                wheels.add((PartWheelBase) part);
            }
        }

        for (int i = 0; i < wheelOffsets.length && i < wheels.size(); i++) {
            list.add(new OBJModelInstance<>(wheels.get(i).model, new OBJModelOptions<>(wheels.get(i).texture, wheelOffsets[i], null, (c, matrixStack, partialTicks) -> {
                matrixStack.mulPose(Axis.XP.rotationDegrees(-car.getWheelRotation(partialTicks)));
            })));
        }

        return list;
    }

}
