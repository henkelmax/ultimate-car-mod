package de.maxhenkel.car.entity.car.parts;

import com.mojang.math.Vector3d;
import de.maxhenkel.car.Main;
import de.maxhenkel.corelib.client.obj.OBJModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class PartTankContainer extends PartTransporterBack {

    private int fluidAmount;

    public PartTankContainer(ResourceLocation texture) {
        super(new OBJModel(new ResourceLocation(Main.MODID, "models/entity/tank_container.obj")), texture, new Vector3d(0D / 16D, 17D / 16D, 5.5D / 16D));
        this.fluidAmount = 64000;
    }

    public int getFluidAmount() {
        return fluidAmount;
    }

    @Override
    public boolean validate(List<Part> parts, List<Component> messages) {
        if (Part.getAmount(parts, part -> part instanceof PartBodyTransporter) != 1) {
            messages.add(new TranslatableComponent("message.parts.no_body_for_tank_container"));
            return false;
        }
        return true;
    }

}
