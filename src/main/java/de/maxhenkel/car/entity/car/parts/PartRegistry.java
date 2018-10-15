package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import de.maxhenkel.tools.MathTools;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartRegistry {

    private static Map<String, Part> partRegistry = new HashMap<>();

    static {

        partRegistry.put("engine_3_cylinder", new PartEngine(0.5F, 0.2F, 0.032F));

        partRegistry.put("engine_6_cylinder", new PartEngine(0.65F, 0.2F, 0.04F));

        partRegistry.put("oak_chassis", createWoodChassis(new ResourceLocation(Main.MODID, "textures/entity/oak_wood.png")));

        partRegistry.put("black_sport_chassis", createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_black.png")));
        partRegistry.put("blue_sport_chassis", createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_blue.png")));
        partRegistry.put("brown_sport_chassis", createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_brown.png")));
        partRegistry.put("cyan_sport_chassis", createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_cyane.png")));
        partRegistry.put("gray_sport_chassis", createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_gray.png")));
        partRegistry.put("green_sport_chassis", createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_green.png")));
        partRegistry.put("light_blue_sport_chassis", createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_light_blue.png")));
        partRegistry.put("lime_sport_chassis", createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_lime.png")));
        partRegistry.put("magenta_sport_chassis", createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_magenta.png")));
        partRegistry.put("orange_sport_chassis", createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_orange.png")));
        partRegistry.put("pink_sport_chassis", createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_pink.png")));
        partRegistry.put("purple_sport_chassis", createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_purple.png")));
        partRegistry.put("red_sport_chassis", createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_red.png")));
        partRegistry.put("silver_sport_chassis", createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_silver.png")));
        partRegistry.put("white_sport_chassis", createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_white.png")));
        partRegistry.put("yellow_sport_chassis", createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_yellow.png")));


        partRegistry.put("wheel", new PartWheels(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/wheel.obj"),
                new ResourceLocation(Main.MODID, "textures/entity/wheel.png")),
                80F
        ));

        partRegistry.put("oak_number_plate", new PartNumberPlate(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/wood_number_plate.obj"),
                new ResourceLocation(Main.MODID, "textures/entity/oak_wood.png")),
                new Vec3d(0D, 7D / 16D, 14.5D / 16D),
                MathTools.rotate(90F, 0F, 0F, 1F),
                new Vec3d(0F, -0.45F, -0.94F)
        ));

        partRegistry.put("oak_bumper", new PartBumper(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/wood_front.obj"),
                new ResourceLocation(Main.MODID, "textures/entity/oak_wood.png")),
                new Vec3d(0D, 6D / 16D, -14.5D / 16D),
                MathTools.rotate(90F, 0F, 0F, 1F)
        ));
    }

    private static Part createWoodChassis(ResourceLocation texture) {
        return new PartChassis(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/wood_chassis.obj"),
                texture),
                new Vec3d(0D, 8.5D / 16D, 0D),
                new Vec3d[]{
                        new Vec3d(9.5F / 16F, 4F / 16F, 8F / 16F),
                        new Vec3d(9.5F / 16F, 4F / 16F, -8F / 16F),
                        new Vec3d(-9.5F / 16F, 4F / 16F, 8F / 16F),
                        new Vec3d(-9.5F / 16F, 4F / 16F, -8F / 16F)
                }
        );
    }

    private static Part createSportChassis(ResourceLocation texture) {
        return new PartChassis(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/sport_chassis.obj"),
                texture),
                new Vec3d(0D, 4D / 16D, 0D),
                new Vec3d[]{
                        new Vec3d(9.5F / 16F, 4F / 16F, 8F / 16F),
                        new Vec3d(9.5F / 16F, 4F / 16F, -8F / 16F),
                        new Vec3d(-9.5F / 16F, 4F / 16F, 8F / 16F),
                        new Vec3d(-9.5F / 16F, 4F / 16F, -8F / 16F)
                }
        );
    }

    public static Part getPart(String name) {
        return partRegistry.get(name);
    }

    public static boolean isValid(List<Part> modelParts) {
        if (!Part.checkAmount(modelParts, 1, 1, part -> part instanceof PartChassis)) {
            return false;
        }

        List<Part> unmodifiableList= Collections.unmodifiableList(modelParts);

        for(Part part:modelParts){
            if(!part.isValid(unmodifiableList)){
                return false;
            }
        }

        return true;
    }
}
