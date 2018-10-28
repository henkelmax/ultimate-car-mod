package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.List;

public class PartChassis extends PartModel {

    protected Vec3d[] wheelOffsets;
    protected Vec3d[] playerOffsets;
    protected Vec3d numberPlateOffset;
    protected float width;
    protected float height;
    protected float minRotationSpeed;

    public PartChassis(OBJModel model, Vec3d offset, Vec3d[] wheelOffsets, Vec3d[] playerOffsets, Vec3d numberPlateOffset, float width, float height, float minRotationSpeed) {
        super(model, offset);
        this.wheelOffsets = wheelOffsets;
        this.playerOffsets = playerOffsets;
        this.numberPlateOffset = numberPlateOffset;
        this.width = width;
        this.height = height;
        this.minRotationSpeed = minRotationSpeed;
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

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    protected int getWheelAmount() {
        return 4;
    }

    @Override
    public boolean validate(List<Part> parts, List<ITextComponent> messages) {
        if (getAmount(parts, part -> part instanceof PartWheel) != getWheelAmount()) {
            messages.add(new TextComponentTranslation("message.parts.wheel_amount", getWheelAmount()));
        }

        int engineAmount = getAmount(parts, part -> part instanceof PartEngine);
        if (engineAmount <= 0) {
            messages.add(new TextComponentTranslation("message.parts.no_engine"));
        } else if (engineAmount > 1) {
            messages.add(new TextComponentTranslation("message.parts.too_many_engines"));
        }

        if (getAmount(parts, part -> part instanceof PartNumberPlate) > 1) {
            messages.add(new TextComponentTranslation("message.parts.too_many_number_plates"));
        }

        if (getAmount(parts, part -> part instanceof PartBumper) > 1) {
            messages.add(new TextComponentTranslation("message.parts.too_many_bumpers"));
        }

        return true;
    }
}
