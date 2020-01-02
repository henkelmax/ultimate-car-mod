package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.entity.model.obj.OBJModel;
import de.maxhenkel.tools.FloatSupplier;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class PartBody extends PartModel {

    protected Vec3d[] wheelOffsets;
    protected Vec3d[] playerOffsets;
    protected Vec3d numberPlateOffset;
    protected float width;
    protected float height;
    protected float minRotationSpeed;
    protected float maxRotationSpeed;
    protected FloatSupplier fuelEfficiency;
    protected FloatSupplier acceleration;
    protected FloatSupplier maxSpeed;

    public PartBody(OBJModel model, ResourceLocation texture, Vec3d offset) {
        super(model, texture, offset);
    }

    public Vec3d[] getWheelOffsets() {
        return wheelOffsets;
    }

    public Vec3d[] getPlayerOffsets() {
        return playerOffsets;
    }

    public Vec3d getNumberPlateOffset() {
        return numberPlateOffset;
    }

    public float getMinRotationSpeed() {
        return minRotationSpeed;
    }

    public float getMaxRotationSpeed() {
        return maxRotationSpeed;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    protected int getWheelAmount() {
        return wheelOffsets.length;
    }

    public float getFuelEfficiency() {
        return fuelEfficiency.getAsFloat();
    }

    public float getAcceleration() {
        return acceleration.getAsFloat();
    }

    public float getMaxSpeed() {
        return maxSpeed.getAsFloat();
    }

    public boolean canFitWheel(PartWheelBase wheel) {
        return wheel instanceof PartWheel;
    }

    @Override
    public boolean validate(List<Part> parts, List<ITextComponent> messages) {
        int wheelAmount = getAmount(parts, part -> part instanceof PartWheelBase);
        if (wheelAmount < getWheelAmount()) {
            messages.add(new TranslationTextComponent("message.parts.too_few_wheels", getWheelAmount()));
        } else if (wheelAmount > getWheelAmount()) {
            messages.add(new TranslationTextComponent("message.parts.too_many_wheels", getWheelAmount()));
        }

        int engineAmount = getAmount(parts, part -> part instanceof PartEngine);
        if (engineAmount <= 0) {
            messages.add(new TranslationTextComponent("message.parts.no_engine"));
        } else if (engineAmount > 1) {
            messages.add(new TranslationTextComponent("message.parts.too_many_engines"));
        }

        if (getAmount(parts, part -> part instanceof PartLicensePlateHolder) > 1) {
            messages.add(new TranslationTextComponent("message.parts.too_many_license_plates"));
        }

        if (getAmount(parts, part -> part instanceof PartBumper) > 1) {
            messages.add(new TranslationTextComponent("message.parts.too_many_bumpers"));
        }

        if (getAmount(parts, part -> part instanceof PartContainer) > 1) {
            messages.add(new TranslationTextComponent("message.parts.too_many_containers"));
        }

        return true;
    }
}
