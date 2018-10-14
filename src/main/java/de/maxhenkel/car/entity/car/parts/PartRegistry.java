package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import java.util.HashMap;
import java.util.List;
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

    public static boolean isValid(List<Part> modelParts){
        if(!checkChassis(modelParts)){
            return false;
        }

        if(!checkWheels(modelParts)){
            return false;
        }

        return true;
    }

    private static boolean checkChassis(List<Part> modelParts){
        return checkAmount(modelParts, 1, part -> part instanceof PartChassis);
    }

    private static boolean checkWheels(List<Part> modelParts){
        return checkAmount(modelParts, 4, part -> part instanceof PartWheels);
    }

    private static boolean checkAmount(List<Part> modelParts, int amount, Checker checker){
        int i=0;
        for(Part part:modelParts){
            if(checker.check(part)){
                i++;
            }
        }

        return amount==i;
    }


    private static interface Checker{
        boolean check(Part part);
    }
}
