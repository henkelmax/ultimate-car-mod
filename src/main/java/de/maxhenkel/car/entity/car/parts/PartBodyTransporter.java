package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class PartBodyTransporter extends PartBody {

    public PartBodyTransporter(ResourceLocation texture, String materialTranslationKey) {
        super(new OBJModel(new ResourceLocation(Main.MODID, "models/entity/transporter_body.obj")), texture, new Vector3d(0D, 4D / 16D, 0D), "transporter", materialTranslationKey);
        this.wheelOffsets = new Vector3d[]{
                new Vector3d(14.5F / 16F, 4F / 16F, 12F / 16F),
                new Vector3d(14.5F / 16F, 4F / 16F, -16F / 16F),
                new Vector3d(-14.5F / 16F, 4F / 16F, 12F / 16F),
                new Vector3d(-14.5F / 16F, 4F / 16F, -16F / 16F),
                new Vector3d(14.5F / 16F, 4F / 16F, 3F / 16F),
                new Vector3d(-14.5F / 16F, 4F / 16F, 3F / 16F)
        };
        this.playerOffsets = new Vector3d[]{
                new Vector3d(0.55D, -0.378D, -0.38D),
                new Vector3d(0.55D, -0.378D, 0.38D)
        };
        this.numberPlateOffset = new Vector3d(0D, 7D / 16D, 17.5D / 16D);
        this.width = 2F;
        this.height = 1.51F;
        this.minRotationSpeed = 2.0F;
        this.maxRotationSpeed = 5F;
        this.fuelEfficiency = () -> Config.bodyTransporterFuelEfficiency.get().floatValue();
        this.acceleration = () -> Config.bodyTransporterAcceleration.get().floatValue();
        this.maxSpeed = () -> Config.bodyTransporterMaxSpeed.get().floatValue();
    }

    @Override
    public boolean validate(List<Part> parts, List<ITextComponent> messages) {
        if (getAmount(parts, part -> part instanceof PartTransporterBack) > 1) {
            messages.add(new TranslationTextComponent("message.parts.too_many_containers"));
            return false;
        }

        return super.validate(parts, messages);
    }

}
