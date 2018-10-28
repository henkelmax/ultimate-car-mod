package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import de.maxhenkel.tools.MathTools;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import java.util.List;

public class PartBumper extends PartModel {

    public PartBumper(ResourceLocation texture) {
        super(new OBJModel(new ResourceLocation(Main.MODID, "models/entity/wood_front.obj"), texture),
                new Vec3d(0D, 6D / 16D, -14.5D / 16D),
                MathTools.rotate(90F, 0F, 0F, 1F)
        );
    }


    @Override
    public boolean validate(List<Part> parts, List<ITextComponent> messages) {

        if (Part.getAmount(parts, part -> part instanceof PartBodyWood) != 1) {
            messages.add(new TextComponentTranslation("message.parts.no_chassis_for_bumper"));
            return false;
        }

        return true;
    }
}
