package de.maxhenkel.car.entity.car.parts;

import org.joml.Vector3d;
import de.maxhenkel.car.CarMod;
import de.maxhenkel.corelib.client.obj.OBJModel;
import net.minecraft.resources.ResourceLocation;

public class PartBodySport extends PartBody {

    public PartBodySport(ResourceLocation texture, String materialTranslationKey) {
        super(new OBJModel(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "models/entity/sport_body.obj")), texture, new Vector3d(0D, 4D / 16D, 0D), "sport", materialTranslationKey);
        this.wheelOffsets = new Vector3d[]{
                new Vector3d(9.5F / 16F, 4F / 16F, 8F / 16F),
                new Vector3d(9.5F / 16F, 4F / 16F, -9F / 16F),
                new Vector3d(-9.5F / 16F, 4F / 16F, 8F / 16F),
                new Vector3d(-9.5F / 16F, 4F / 16F, -9F / 16F)
        };
        this.playerOffsets = new Vector3d[]{
                new Vector3d(0D, -0.378D, 0D)
        };
        this.numberPlateOffset = new Vector3d(0D, 7D / 16D, 14.5D / 16D);
        this.width = 1.4F;
        this.height = 1.2F;
        this.minRotationSpeed = 1.1F;
        this.maxRotationSpeed = 5F;
        this.fuelEfficiency = () -> CarMod.SERVER_CONFIG.bodySportFuelEfficiency.get().floatValue();
        this.acceleration = () -> CarMod.SERVER_CONFIG.bodySportAcceleration.get().floatValue();
        this.maxSpeed = () -> CarMod.SERVER_CONFIG.bodySportMaxSpeed.get().floatValue();
    }

}
