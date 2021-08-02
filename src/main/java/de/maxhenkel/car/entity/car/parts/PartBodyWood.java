package de.maxhenkel.car.entity.car.parts;

import com.mojang.math.Vector3d;
import de.maxhenkel.car.Main;
import de.maxhenkel.corelib.client.obj.OBJModel;
import net.minecraft.resources.ResourceLocation;

public class PartBodyWood extends PartBodyWoodBase {

    public PartBodyWood(ResourceLocation texture, String materialTranslationKey) {
        super(new OBJModel(new ResourceLocation(Main.MODID, "models/entity/wood_body.obj")), texture, new Vector3d(0D, 4D / 16D, 0D), "wood", materialTranslationKey);

        this.bumperOffset = new Vector3d(0D, 6D / 16D, -14.5D / 16D);
        this.wheelOffsets = new Vector3d[]{
                new Vector3d(9.5F / 16F, 4F / 16F, 8F / 16F),
                new Vector3d(9.5F / 16F, 4F / 16F, -8F / 16F),
                new Vector3d(-9.5F / 16F, 4F / 16F, 8F / 16F),
                new Vector3d(-9.5F / 16F, 4F / 16F, -8F / 16F)
        };
        this.playerOffsets = new Vector3d[]{
                new Vector3d(0D, -0.378D, 0D)
        };
        this.numberPlateOffset = new Vector3d(0D, 7D / 16D, 14.5D / 16D);
        this.width = 1.3F;
        this.height = 1.6F;
        this.minRotationSpeed = 2F;
        this.maxRotationSpeed = 5F;
        this.fuelEfficiency = () -> Main.SERVER_CONFIG.bodyWoodFuelEfficiency.get().floatValue();
        this.acceleration = () -> Main.SERVER_CONFIG.bodyWoodAcceleration.get().floatValue();
        this.maxSpeed = () -> Main.SERVER_CONFIG.bodyWoodMaxSpeed.get().floatValue();
    }

}
