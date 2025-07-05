package de.maxhenkel.car.entity.car.parts;

import org.joml.Vector3d;
import de.maxhenkel.car.CarMod;
import de.maxhenkel.corelib.client.obj.OBJModel;
import net.minecraft.resources.ResourceLocation;

public class PartBodyBigWood extends PartBodyWoodBase {

    public PartBodyBigWood(ResourceLocation texture, String materialTranslationKey) {
        super(new OBJModel(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "models/entity/wood_body_big.obj")), texture, new Vector3d(0D, 4D / 16D, 0D), "big_wood", materialTranslationKey);

        this.bumperOffset = new Vector3d(0D, 6D / 16D, -19.5D / 16D);
        this.wheelOffsets = new Vector3d[]{
                new Vector3d(12.5F / 16F, 4F / 16F, 10F / 16F),
                new Vector3d(12.5F / 16F, 4F / 16F, -13F / 16F),
                new Vector3d(-12.5F / 16F, 4F / 16F, 10F / 16F),
                new Vector3d(-12.5F / 16F, 4F / 16F, -13F / 16F)
        };
        this.playerOffsets = new Vector3d[]{
                new Vector3d(0.2D, -0.378D, 0D),
                new Vector3d(-0.5D, -0.378D, 0D)
        };
        this.numberPlateOffset = new Vector3d(0D, 7D / 16D, 16.5D / 16D);
        this.width = 1.625F;
        this.height = 1.6F;
        this.minRotationSpeed = 2F;
        this.maxRotationSpeed = 5F;
        this.fuelEfficiency = () -> CarMod.SERVER_CONFIG.bodyBigWoodFuelEfficiency.get().floatValue();
        this.acceleration = () -> CarMod.SERVER_CONFIG.bodyBigWoodAcceleration.get().floatValue();
        this.maxSpeed = () -> CarMod.SERVER_CONFIG.bodyBigWoodMaxSpeed.get().floatValue();
    }

}
