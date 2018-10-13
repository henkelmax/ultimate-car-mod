package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import java.util.HashMap;
import java.util.Map;

public class PartRegistry {

    private static Map<String, Part> partRegistry=new HashMap<>();

    static{
        partRegistry.put("oak_chassis", new PartChassis(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/woodcar.obj"),
                new ResourceLocation(Main.MODID, "textures/entity/oak_wood.png")),
                new Vec3d(0, 8.5F / 16F, 0),
                new Vec3d[]{
                        new Vec3d(9.5F / 16F, 4F / 16F, 8F / 16F),
                        new Vec3d(9.5F / 16F, 4F / 16F, -8F / 16F),
                        new Vec3d(-9.5F / 16F, 4F / 16F, 8F / 16F),
                        new Vec3d(-9.5F / 16F, 4F / 16F, -8F / 16F)
                    }
                ));
        partRegistry.put("wheel", new PartWheels(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/wheel.obj"),
                new ResourceLocation(Main.MODID, "textures/entity/wheel.png")),
                80F
        ));
    }

    public static Part getPart(String name){
        return partRegistry.get(name);
    }

}
