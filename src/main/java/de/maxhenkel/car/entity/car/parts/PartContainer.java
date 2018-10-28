package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import java.util.List;

public class PartContainer extends PartModel {

    public PartContainer(OBJModel model, Vec3d offset) {
        super(model, offset);
    }

    @Override
    public boolean validate(List<Part> parts, List<ITextComponent> messages) {

        if (Part.getAmount(parts, part -> part instanceof PartBodyTransporter) != 1) {
            messages.add(new TextComponentTranslation("message.parts.no_chassis_for_container"));
            return false;
        }

        return true;
    }
}
