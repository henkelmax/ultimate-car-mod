package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class PartBodySport extends PartBody {

    public PartBodySport(ResourceLocation texture) {
        super(new OBJModel(new ResourceLocation(Main.MODID, "models/entity/sport_body.obj")), texture,
                new Vector3d(0D, 4D / 16D, 0D));
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
        this.fuelEfficiency = () -> Config.bodySportFuelEfficiency.get().floatValue();
        this.acceleration = () -> Config.bodySportAcceleration.get().floatValue();
        this.maxSpeed = () -> Config.bodySportMaxSpeed.get().floatValue();
    }
}
