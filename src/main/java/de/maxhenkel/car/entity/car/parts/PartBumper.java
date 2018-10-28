package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.util.vector.Quaternion;

import java.util.List;

public class PartBumper extends PartModel {

    public PartBumper(OBJModel model, Vec3d offset, Quaternion rotation) {
        super(model, offset, rotation);
    }


    @Override
    public boolean validate(List<Part> parts, List<ITextComponent> messages) {

        if (Part.getAmount(parts, part -> part instanceof PartChassisWood) != 1) {
            messages.add(new TextComponentTranslation("message.parts.no_chassis_for_bumper"));
            return false;
        }

        return true;
    }
}
