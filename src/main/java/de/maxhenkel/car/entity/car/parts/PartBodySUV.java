package de.maxhenkel.car.entity.car.parts;

import org.joml.Vector3d;
import de.maxhenkel.car.Main;
import de.maxhenkel.corelib.client.obj.OBJModel;
import net.minecraft.resources.ResourceLocation;

public class PartBodySUV extends PartBody {

    public PartBodySUV(ResourceLocation texture, String materialTranslationKey) {
        super(new OBJModel(new ResourceLocation(Main.MODID, "models/entity/suv_body.obj")), texture, new Vector3d(0D, 4D / 16D, 0D), "suv", materialTranslationKey);
        this.wheelOffsets = new Vector3d[]{
                new Vector3d(11F / 16F, 5F / 16F, 10F / 16F),
                new Vector3d(11F / 16F, 5F / 16F, -10F / 16F),
                new Vector3d(-11F / 16F, 5F / 16F, 10F / 16F),
                new Vector3d(-11F / 16F, 5F / 16F, -10F / 16F)
        };
        this.playerOffsets = new Vector3d[]{
                new Vector3d(-5D / 16D, -0.378D, 0D)
        };
        this.numberPlateOffset = new Vector3d(0D, 6D / 16D, 16.5D / 16D);
        this.width = 1.5F;
        this.height = 1.4F;
        this.minRotationSpeed = 1.1F;
        this.maxRotationSpeed = 5F;
        this.fuelEfficiency = () -> Main.SERVER_CONFIG.bodySUVFuelEfficiency.get().floatValue();
        this.acceleration = () -> Main.SERVER_CONFIG.bodySUVAcceleration.get().floatValue();
        this.maxSpeed = () -> Main.SERVER_CONFIG.bodySUVMaxSpeed.get().floatValue();
    }

    @Override
    public boolean canFitWheel(PartWheelBase wheel) {
        return wheel instanceof PartWheelBig;
    }

}
