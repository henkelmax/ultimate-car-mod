package de.maxhenkel.car.entity.car.parts;

import org.joml.Vector3d;
import de.maxhenkel.car.CarMod;
import de.maxhenkel.corelib.client.obj.OBJModel;
import net.minecraft.resources.Identifier;

public class PartBodyWood extends PartBodyWoodBase {

    public PartBodyWood(Identifier texture, String materialTranslationKey) {
        super(new OBJModel(Identifier.fromNamespaceAndPath(CarMod.MODID, "models/entity/wood_body.obj")), texture, new Vector3d(0D, 4D / 16D, 0D), "wood", materialTranslationKey);

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
        this.fuelEfficiency = () -> CarMod.SERVER_CONFIG.bodyWoodFuelEfficiency.get().floatValue();
        this.acceleration = () -> CarMod.SERVER_CONFIG.bodyWoodAcceleration.get().floatValue();
        this.maxSpeed = () -> CarMod.SERVER_CONFIG.bodyWoodMaxSpeed.get().floatValue();
    }

}
