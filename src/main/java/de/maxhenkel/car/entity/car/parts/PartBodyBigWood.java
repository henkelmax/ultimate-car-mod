package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class PartBodyBigWood extends PartBodyWoodBase {

    public PartBodyBigWood(ResourceLocation texture) {
        super(new OBJModel(new ResourceLocation(Main.MODID, "models/entity/wood_body_big.obj"), texture),
                new Vec3d(0D, 4D / 16D, 0D));

        this.bumperOffset = new Vec3d(0D, 6D / 16D, -19D / 16D);
        this.wheelOffsets = new Vec3d[]{
                new Vec3d(12.5F / 16F, 4F / 16F, 11F / 16F),
                new Vec3d(12.5F / 16F, 4F / 16F, -13F / 16F),
                new Vec3d(-12.5F / 16F, 4F / 16F, 11F / 16F),
                new Vec3d(-12.5F / 16F, 4F / 16F, -13F / 16F)
        };
        this.playerOffsets = new Vec3d[]{
                new Vec3d(0D, -0.378D, 0D)
        };
        this.numberPlateOffset = new Vec3d(0D, 7D / 16D, 17D / 16D);
        this.width = 1.5F;
        this.height = 1.6F;
        this.minRotationSpeed = 2F;
        this.maxRotationSpeed = 5F;
        this.fuelEfficiency = 0.7F;
        this.acceleration = 0.95F;
        this.maxSpeed = 0.85F;
    }
}
