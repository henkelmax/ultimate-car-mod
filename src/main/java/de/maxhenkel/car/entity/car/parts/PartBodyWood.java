package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class PartBodyWood extends PartBodyWoodBase {

    public PartBodyWood(ResourceLocation texture) {
        super(new OBJModel(new ResourceLocation(Main.MODID, "models/entity/wood_body.obj"), texture),
                new Vec3d(0D, 4D / 16D, 0D));

        this.bumperOffset = new Vec3d(0D, 6D / 16D, -14.5D / 16D);
        this.wheelOffsets = new Vec3d[]{
                new Vec3d(9.5F / 16F, 4F / 16F, 8F / 16F),
                new Vec3d(9.5F / 16F, 4F / 16F, -8F / 16F),
                new Vec3d(-9.5F / 16F, 4F / 16F, 8F / 16F),
                new Vec3d(-9.5F / 16F, 4F / 16F, -8F / 16F)
        };
        this.playerOffsets = new Vec3d[]{
                new Vec3d(0D, -0.378D, 0D)
        };
        this.numberPlateOffset = new Vec3d(0D, 7D / 16D, 14.5D / 16D);
        this.width = 1.3F;
        this.height = 1.6F;
        this.minRotationSpeed = 2F;
        this.maxRotationSpeed = 5F;
        this.fuelEfficiency = Config.bodyWoodFuelEfficiency;
        this.acceleration = Config.bodyWoodAcceleration;
        this.maxSpeed = Config.bodyWoodMaxSpeed;
    }
}
