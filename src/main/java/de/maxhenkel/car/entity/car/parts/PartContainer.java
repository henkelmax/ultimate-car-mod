package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class PartContainer extends PartTransporterBack {

    public PartContainer(ResourceLocation texture) {
        super(new OBJModel(new ResourceLocation(Main.MODID, "models/entity/container.obj")),
                texture, new Vec3d(0D / 16D, 17D / 16D, 5.5D / 16D));
    }

    @Override
    public boolean validate(List<Part> parts, List<ITextComponent> messages) {
        if (Part.getAmount(parts, part -> part instanceof PartBodyTransporter) != 1) {
            messages.add(new TranslationTextComponent("message.parts.no_body_for_container"));
            return false;
        }
        return true;
    }
}
