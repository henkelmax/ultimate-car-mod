package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class PartBodySUV extends PartBody {

    public PartBodySUV(ResourceLocation texture) {
        super(new OBJModel(new ResourceLocation(Main.MODID, "models/entity/suv_body.obj")), texture,
                new Vector3d(0D, 4D / 16D, 0D));
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
        this.fuelEfficiency = () -> Config.bodySUVFuelEfficiency.get().floatValue();
        this.acceleration = () -> Config.bodySUVAcceleration.get().floatValue();
        this.maxSpeed = () -> Config.bodySUVMaxSpeed.get().floatValue();
    }

    @Override
    public boolean canFitWheel(PartWheelBase wheel) {
        return wheel instanceof PartWheelBig;
    }
}
