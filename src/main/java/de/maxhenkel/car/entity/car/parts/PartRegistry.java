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

    public static final Part OAK_LICENSE_PLATE_HOLDER = createLicensePlateHolder(new ResourceLocation("textures/blocks/planks_oak.png"));
    public static final Part ACACIA_LICENSE_PLATE_HOLDER = createLicensePlateHolder(new ResourceLocation("textures/blocks/planks_acacia.png"));
    public static final Part DARK_OAK_LICENSE_PLATE_HOLDER = createLicensePlateHolder(new ResourceLocation("textures/blocks/planks_big_oak.png"));
    public static final Part BIRCH_LICENSE_PLATE_HOLDER = createLicensePlateHolder(new ResourceLocation("textures/blocks/planks_birch.png"));
    public static final Part JUNGLE_LICENSE_PLATE_HOLDER = createLicensePlateHolder(new ResourceLocation("textures/blocks/planks_jungle.png"));
    public static final Part SPRUCE_LICENSE_PLATE_HOLDER = createLicensePlateHolder(new ResourceLocation("textures/blocks/planks_spruce.png"));

    public static final Part IRON_LICENSE_PLATE_HOLDER = createLicensePlateHolder(new ResourceLocation("textures/blocks/iron_block.png"));
    public static final Part DIAMOND_LICENSE_PLATE_HOLDER = createLicensePlateHolder(new ResourceLocation("textures/blocks/diamond_block.png"));
    public static final Part GOLD_LICENSE_PLATE_HOLDER = createLicensePlateHolder(new ResourceLocation("textures/blocks/gold_block.png"));
    public static final Part EMERALD_LICENSE_PLATE_HOLDER = createLicensePlateHolder(new ResourceLocation("textures/blocks/emerald_block.png"));

    public static final Part WHEEL = new PartWheel(new OBJModel(
            new ResourceLocation(Main.MODID, "models/entity/wheel.obj"),
            new ResourceLocation(Main.MODID, "textures/entity/wheel.png")),
            80F
    );

    public static final Part OAK_BODY = createWoodBody(new ResourceLocation(Main.MODID, "textures/entity/car_wood_oak.png"));
    public static final Part ACACIA_BODY = createWoodBody(new ResourceLocation(Main.MODID, "textures/entity/car_wood_acacia.png"));
    public static final Part DARK_OAK_BODY = createWoodBody(new ResourceLocation(Main.MODID, "textures/entity/car_wood_dark_oak.png"));
    public static final Part BIRCH_BODY = createWoodBody(new ResourceLocation(Main.MODID, "textures/entity/car_wood_birch.png"));
    public static final Part JUNGLE_BODY = createWoodBody(new ResourceLocation(Main.MODID, "textures/entity/car_wood_jungle.png"));
    public static final Part SPRUCE_BODY = createWoodBody(new ResourceLocation(Main.MODID, "textures/entity/car_wood_spruce.png"));

    public static final Part BIG_OAK_BODY = createBigWoodBody(new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_oak.png"));
    public static final Part BIG_ACACIA_BODY = createBigWoodBody(new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_acacia.png"));
    public static final Part BIG_DARK_OAK_BODY = createBigWoodBody(new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_dark_oak.png"));
    public static final Part BIG_BIRCH_BODY = createBigWoodBody(new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_birch.png"));
    public static final Part BIG_JUNGLE_BODY = createBigWoodBody(new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_jungle.png"));
    public static final Part BIG_SPRUCE_BODY = createBigWoodBody(new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_spruce.png"));

    public static final Part BLACK_SPORT_BODY = createSportBody(new ResourceLocation(Main.MODID, "textures/entity/car_sport_black.png"));
    public static final Part BLUE_SPORT_BODY = createSportBody(new ResourceLocation(Main.MODID, "textures/entity/car_sport_blue.png"));
    public static final Part BROWN_SPORT_BODY = createSportBody(new ResourceLocation(Main.MODID, "textures/entity/car_sport_brown.png"));
    public static final Part CYAN_SPORT_BODY = createSportBody(new ResourceLocation(Main.MODID, "textures/entity/car_sport_cyan.png"));
    public static final Part GRAY_SPORT_BODY = createSportBody(new ResourceLocation(Main.MODID, "textures/entity/car_sport_gray.png"));
    public static final Part GREEN_SPORT_BODY = createSportBody(new ResourceLocation(Main.MODID, "textures/entity/car_sport_green.png"));
    public static final Part LIGHT_BLUE_SPORT_BODY = createSportBody(new ResourceLocation(Main.MODID, "textures/entity/car_sport_light_blue.png"));
    public static final Part LIME_SPORT_BODY = createSportBody(new ResourceLocation(Main.MODID, "textures/entity/car_sport_lime.png"));
    public static final Part MAGENTA_SPORT_BODY = createSportBody(new ResourceLocation(Main.MODID, "textures/entity/car_sport_magenta.png"));
    public static final Part ORANGE_SPORT_BODY = createSportBody(new ResourceLocation(Main.MODID, "textures/entity/car_sport_orange.png"));
    public static final Part PINK_SPORT_BODY = createSportBody(new ResourceLocation(Main.MODID, "textures/entity/car_sport_pink.png"));
    public static final Part PURPLE_SPORT_BODY = createSportBody(new ResourceLocation(Main.MODID, "textures/entity/car_sport_purple.png"));
    public static final Part RED_SPORT_BODY = createSportBody(new ResourceLocation(Main.MODID, "textures/entity/car_sport_red.png"));
    public static final Part SILVER_SPORT_BODY = createSportBody(new ResourceLocation(Main.MODID, "textures/entity/car_sport_silver.png"));
    public static final Part WHITE_SPORT_BODY = createSportBody(new ResourceLocation(Main.MODID, "textures/entity/car_sport_white.png"));
    public static final Part YELLOW_SPORT_BODY = createSportBody(new ResourceLocation(Main.MODID, "textures/entity/car_sport_yellow.png"));

    public static final Part BLACK_TRANSPORTER_BODY = createTransporterBody(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_black.png"));
    public static final Part BLUE_TRANSPORTER_BODY = createTransporterBody(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_blue.png"));
    public static final Part BROWN_TRANSPORTER_BODY = createTransporterBody(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_brown.png"));
    public static final Part CYAN_TRANSPORTER_BODY = createTransporterBody(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_cyan.png"));
    public static final Part GRAY_TRANSPORTER_BODY = createTransporterBody(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_gray.png"));
    public static final Part GREEN_TRANSPORTER_BODY = createTransporterBody(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_green.png"));
    public static final Part LIGHT_BLUE_TRANSPORTER_BODY = createTransporterBody(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_light_blue.png"));
    public static final Part LIME_TRANSPORTER_BODY = createTransporterBody(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_lime.png"));
    public static final Part MAGENTA_TRANSPORTER_BODY = createTransporterBody(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_magenta.png"));
    public static final Part ORANGE_TRANSPORTER_BODY = createTransporterBody(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_orange.png"));
    public static final Part PINK_TRANSPORTER_BODY = createTransporterBody(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_pink.png"));
    public static final Part PURPLE_TRANSPORTER_BODY = createTransporterBody(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_purple.png"));
    public static final Part RED_TRANSPORTER_BODY = createTransporterBody(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_red.png"));
    public static final Part SILVER_TRANSPORTER_BODY = createTransporterBody(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_silver.png"));
    public static final Part WHITE_TRANSPORTER_BODY = createTransporterBody(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_white.png"));
    public static final Part YELLOW_TRANSPORTER_BODY = createTransporterBody(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_yellow.png"));

    public static final Part BLACK_CONTAINER = createContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_black.png"));
    public static final Part BLUE_CONTAINER = createContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_blue.png"));
    public static final Part BROWN_CONTAINER = createContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_brown.png"));
    public static final Part CYAN_CONTAINER = createContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_cyan.png"));
    public static final Part GRAY_CONTAINER = createContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_gray.png"));
    public static final Part GREEN_CONTAINER = createContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_green.png"));
    public static final Part LIGHT_BLUE_CONTAINER = createContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_light_blue.png"));
    public static final Part LIME_CONTAINER = createContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_lime.png"));
    public static final Part MAGENTA_CONTAINER = createContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_magenta.png"));
    public static final Part ORANGE_CONTAINER = createContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_orange.png"));
    public static final Part PINK_CONTAINER = createContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_pink.png"));
    public static final Part PURPLE_CONTAINER = createContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_purple.png"));
    public static final Part RED_CONTAINER = createContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_red.png"));
    public static final Part SILVER_CONTAINER = createContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_silver.png"));
    public static final Part WHITE_CONTAINER = createContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_white.png"));
    public static final Part YELLOW_CONTAINER = createContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_yellow.png"));

    public static final Part OAK_BUMPER = createWoodBumper(new ResourceLocation(Main.MODID, "textures/entity/oak_wood.png"));
    public static final Part ACACIA_BUMPER = createWoodBumper(new ResourceLocation(Main.MODID, "textures/entity/acacia_wood.png"));
    public static final Part DARK_OAK_BUMPER = createWoodBumper(new ResourceLocation(Main.MODID, "textures/entity/birch_wood.png"));
    public static final Part BIRCH_BUMPER = createWoodBumper(new ResourceLocation(Main.MODID, "textures/entity/dark_oak_wood.png"));
    public static final Part JUNGLE_BUMPER = createWoodBumper(new ResourceLocation(Main.MODID, "textures/entity/jungle_wood.png"));
    public static final Part SPRUCE_BUMPER = createWoodBumper(new ResourceLocation(Main.MODID, "textures/entity/spruce_wood.png"));

    private static Part createContainer(ResourceLocation texture) {
        return new PartContainer(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/container.obj"),
                texture),
                new Vec3d(0D / 16D, 17D / 16D, 5.5D / 16D)
        );
    }

    private static Part createLicensePlateHolder(ResourceLocation texture) {
        return new PartLicensePlateHolder(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/license_plate.obj"),
                texture),
                MathTools.rotate(90F, 0F, 0F, 1F),
                new Vec3d(0D, -0.5D / 16D, -0.5D / 16D - 0.001D)
        );
    }

    private static Part createWoodBumper(ResourceLocation texture) {
        return new PartBumper(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/wood_front.obj"),
                texture),
                new Vec3d(0D, 6D / 16D, -14.5D / 16D),
                MathTools.rotate(90F, 0F, 0F, 1F)
        );
    }

    private static Part createWoodBody(ResourceLocation texture) {
        return new PartBodyWood(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/wood_body.obj"),
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

    private static Part createBigWoodBody(ResourceLocation texture) {
        return new PartBodyWood(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/wood_body_big.obj"),
                texture),
                new Vec3d(0D, 4D / 16D, 0D),
                new Vec3d[]{
                        new Vec3d(12.5F / 16F, 4F / 16F, 11F / 16F),
                        new Vec3d(12.5F / 16F, 4F / 16F, -13F / 16F),
                        new Vec3d(-12.5F / 16F, 4F / 16F, 11F / 16F),
                        new Vec3d(-12.5F / 16F, 4F / 16F, -13F / 16F)
                },
                new Vec3d[]{
                        new Vec3d(0D, -0.378D, 0D)
                },
                new Vec3d(0D, 7D / 16D, 17D / 16D),
                1.5F,
                1.6F,
                2.0F
        );
    }

    private static Part createSportBody(ResourceLocation texture) {
        return new PartBody(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/sport_body.obj"),
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

    private static Part createTransporterBody(ResourceLocation texture) {
        return new PartBodyTransporter(new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/transporter_body.obj"),
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

    public static boolean isValid(EntityGenericCar car, List<ITextComponent> messages) {
        return isValid(car.getModelParts(), messages);
    }

    public static boolean isValid(List<Part> modelParts, List<ITextComponent> messages) {
        int bodyAmount = Part.getAmount(modelParts, part -> part instanceof PartBody);
        if (bodyAmount <= 0) {
            messages.add(new TextComponentTranslation("message.parts.no_body"));
            return false;
        } else if (bodyAmount > 1) {
            messages.add(new TextComponentTranslation("message.parts.too_many_body"));
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
