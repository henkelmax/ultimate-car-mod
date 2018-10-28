package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import de.maxhenkel.tools.MathTools;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import java.util.*;

public class PartRegistry {

    public static final Part ENGINE_3_CYLINDER = new PartEngine3Cylinder(0.5F, 0.2F, 0.032F);
    public static final Part ENGINE_6_CYLINDER = new PartEngine6Cylinder(0.65F, 0.2F, 0.04F);
    public static final Part NUMBER_PLATE_OAK = new PartNumberPlate(new OBJModel(
            new ResourceLocation(Main.MODID, "models/entity/wood_number_plate.obj"),
            new ResourceLocation(Main.MODID, "textures/entity/oak_wood.png")),
            MathTools.rotate(90F, 0F, 0F, 1F),
            new Vec3d(0D, -0.5D / 16D, -0.5D / 16D - 0.001D)
    );
    public static final Part WHEEL = new PartWheel(new OBJModel(
            new ResourceLocation(Main.MODID, "models/entity/wheel.obj"),
            new ResourceLocation(Main.MODID, "textures/entity/wheel.png")),
            80F
    );
    public static final Part OAK_CHASSIS = createWoodChassis(new ResourceLocation(Main.MODID, "textures/entity/car_wood_oak.png"));
    public static final Part BIG_OAK_CHASSIS = createBigWoodChassis(new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_oak.png"));

    public static final Part BLACK_SPORT_CHASSIS = createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_black.png"));
    public static final Part BLUE_SPORT_CHASSIS = createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_blue.png"));
    public static final Part BROWN_SPORT_CHASSIS = createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_brown.png"));
    public static final Part CYAN_SPORT_CHASSIS = createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_cyane.png"));
    public static final Part GRAY_SPORT_CHASSIS = createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_gray.png"));
    public static final Part GREEN_SPORT_CHASSIS = createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_green.png"));
    public static final Part LIGHT_BLUE_SPORT_CHASSIS = createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_light_blue.png"));
    public static final Part LIME_SPORT_CHASSIS = createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_lime.png"));
    public static final Part MAGENTA_SPORT_CHASSIS = createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_magenta.png"));
    public static final Part ORANGE_SPORT_CHASSIS = createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_orange.png"));
    public static final Part PINK_SPORT_CHASSIS = createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_pink.png"));
    public static final Part PURPLE_SPORT_CHASSIS = createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_purple.png"));
    public static final Part RED_SPORT_CHASSIS = createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_red.png"));
    public static final Part SILVER_SPORT_CHASSIS = createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_silver.png"));
    public static final Part WHITE_SPORT_CHASSIS = createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_white.png"));
    public static final Part YELLOW_SPORT_CHASSIS = createSportChassis(new ResourceLocation(Main.MODID, "textures/entity/car_sport_yellow.png"));

    public static final Part WHITE_TRANSPORTER_CHASSIS = createTransporterChassis(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_white.png"));

    public static final Part OAK_BUMPER = createWoodBumper(new ResourceLocation(Main.MODID, "textures/entity/oak_wood.png"));


    private static Map<String, Part> partRegistry = new HashMap<>();

    static {

        partRegistry.put("engine_3_cylinder", new PartEngine3Cylinder(0.5F, 0.2F, 0.032F));

        partRegistry.put("engine_6_cylinder", new PartEngine6Cylinder(0.65F, 0.2F, 0.04F));

        partRegistry.put("oak_chassis", createWoodChassis(new ResourceLocation(Main.MODID, "textures/entity/car_wood_oak.png")));

        partRegistry.put("big_oak_chassis", createBigWoodChassis(new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_oak.png")));

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

        partRegistry.put("white_transporter_chassis", createTransporterChassis(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_white.png")));

        partRegistry.put("wheel", new PartWheel(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/wheel.obj"),
                new ResourceLocation(Main.MODID, "textures/entity/wheel.png")),
                80F
        ));

        partRegistry.put("oak_number_plate", new PartNumberPlate(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/wood_number_plate.obj"),
                new ResourceLocation(Main.MODID, "textures/entity/oak_wood.png")),
                MathTools.rotate(90F, 0F, 0F, 1F),
                new Vec3d(0D, -0.5D / 16D, -0.5D / 16D - 0.001D)
        ));

        partRegistry.put("oak_bumper", new PartBumper(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/wood_front.obj"),
                new ResourceLocation(Main.MODID, "textures/entity/oak_wood.png")),
                new Vec3d(0D, 6D / 16D, -14.5D / 16D),
                MathTools.rotate(90F, 0F, 0F, 1F)
        ));
    }

    private static Part createWoodBumper(ResourceLocation texture) {
        return new PartBumper(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/wood_front.obj"),
                texture),
                new Vec3d(0D, 6D / 16D, -14.5D / 16D),
                MathTools.rotate(90F, 0F, 0F, 1F)
        );
    }

    private static Part createWoodChassis(ResourceLocation texture) {
        return new PartChassisWood(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/wood_chassis.obj"),
                texture),
                new Vec3d(0D, 4D / 16D, 0D),
                new Vec3d[]{
                        new Vec3d(9.5F / 16F, 4F / 16F, 8F / 16F),
                        new Vec3d(9.5F / 16F, 4F / 16F, -8F / 16F),
                        new Vec3d(-9.5F / 16F, 4F / 16F, 8F / 16F),
                        new Vec3d(-9.5F / 16F, 4F / 16F, -8F / 16F)
                },
                new Vec3d[]{
                        new Vec3d(0D, -0.378D, 0D)
                },
                new Vec3d(0D, 7D / 16D, 14.5D / 16D),
                1.3F,
                1.6F,
                2.0F
        );
    }

    private static Part createBigWoodChassis(ResourceLocation texture) {
        return new PartChassisWood(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/wood_chassis_big.obj"),
                texture),
                new Vec3d(0D, 4D / 16D, 0D),
                new Vec3d[]{
                        new Vec3d(12.5F / 16F, 4F / 16F, 8F / 16F),
                        new Vec3d(12.5F / 16F, 4F / 16F, -8F / 16F),
                        new Vec3d(-12.5F / 16F, 4F / 16F, 8F / 16F),
                        new Vec3d(-12.5F / 16F, 4F / 16F, -8F / 16F)
                },
                new Vec3d[]{
                        new Vec3d(0D, -0.378D, 0D)
                },
                new Vec3d(0D, 7D / 16D, 17D / 16D),
                1.3F,
                1.6F,
                2.0F
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
                },
                new Vec3d[]{
                        new Vec3d(0D, -0.378D, 0D)
                },
                new Vec3d(0D, 7D / 16D, 14.5D / 16D),
                1.4F,
                1.2F,
                1.1F
        );
    }

    private static Part createTransporterChassis(ResourceLocation texture) {
        return new PartChassisTransporter(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/transporter_chassis.obj"),
                texture),
                new Vec3d(0D, 4D / 16D, 0D),
                new Vec3d[]{
                        new Vec3d(14.5F / 16F, 4F / 16F, 12F / 16F),
                        new Vec3d(14.5F / 16F, 4F / 16F, -16F / 16F),
                        new Vec3d(-14.5F / 16F, 4F / 16F, 12F / 16F),
                        new Vec3d(-14.5F / 16F, 4F / 16F, -16F / 16F),
                        new Vec3d(14.5F / 16F, 4F / 16F, 3F / 16F),
                        new Vec3d(-14.5F / 16F, 4F / 16F, 3F / 16F)
                },
                new Vec3d[]{
                        new Vec3d(0.55D, -0.378D, -0.38D),
                        new Vec3d(0.55D, -0.378D, 0.38D)
                },
                new Vec3d(0D, 7D / 16D, 17.5D / 16D),
                2.0F,
                1.51F,
                2.0F
        );
    }

    @Deprecated
    public static Part getPart(String name) {
        return partRegistry.get(name);
    }

    public static boolean isValid(EntityGenericCar car, List<ITextComponent> messages) {
        return isValid(car.getModelParts(), messages);
    }

    public static boolean isValid(List<Part> modelParts, List<ITextComponent> messages) {
        int chassisAmount = Part.getAmount(modelParts, part -> part instanceof PartChassis);
        if (chassisAmount <= 0) {
            messages.add(new TextComponentTranslation("message.parts.no_chassis"));
            return false;
        } else if (chassisAmount > 1) {
            messages.add(new TextComponentTranslation("message.parts.too_many_chassis"));
            return false;
        }

        List<Part> unmodifiableList = Collections.unmodifiableList(modelParts);

        boolean flag = true;

        for (Part part : modelParts) {
            if (!part.validate(unmodifiableList, messages)) {
                flag = false;
            }
        }

        return flag;
    }
}
