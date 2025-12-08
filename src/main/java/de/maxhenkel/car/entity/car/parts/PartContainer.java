package de.maxhenkel.car.entity.car.parts;

import org.joml.Vector3d;
import de.maxhenkel.car.CarMod;
import de.maxhenkel.corelib.client.obj.OBJModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.List;

public class PartContainer extends PartTransporterBack {

    public PartContainer(Identifier texture) {
        super(new OBJModel(Identifier.fromNamespaceAndPath(CarMod.MODID, "models/entity/container.obj")),
                texture, new Vector3d(0D / 16D, 17D / 16D, 5.5D / 16D));
    }

    @Override
    public boolean validate(List<Part> parts, List<Component> messages) {
        if (Part.getAmount(parts, part -> part instanceof PartBodyTransporter) != 1) {
            messages.add(Component.translatable("message.parts.no_body_for_container"));
            return false;
        }
        return true;
    }

}
