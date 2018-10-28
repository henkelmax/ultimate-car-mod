package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import java.util.*;

public class PartRegistry {

    public static final Part ENGINE_3_CYLINDER = new PartEngine3Cylinder();
    public static final Part ENGINE_6_CYLINDER = new PartEngine6Cylinder();

    public static final Part OAK_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/blocks/planks_oak.png"));
    public static final Part ACACIA_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/blocks/planks_acacia.png"));
    public static final Part DARK_OAK_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/blocks/planks_big_oak.png"));
    public static final Part BIRCH_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/blocks/planks_birch.png"));
    public static final Part JUNGLE_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/blocks/planks_jungle.png"));
    public static final Part SPRUCE_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/blocks/planks_spruce.png"));

    public static final Part IRON_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/blocks/iron_block.png"));
    public static final Part DIAMOND_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/blocks/diamond_block.png"));
    public static final Part GOLD_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/blocks/gold_block.png"));
    public static final Part EMERALD_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/blocks/emerald_block.png"));

    public static final Part OAK_BODY = new PartBodyWood(new ResourceLocation(Main.MODID, "textures/entity/car_wood_oak.png"));
    public static final Part ACACIA_BODY = new PartBodyWood(new ResourceLocation(Main.MODID, "textures/entity/car_wood_acacia.png"));
    public static final Part DARK_OAK_BODY = new PartBodyWood(new ResourceLocation(Main.MODID, "textures/entity/car_wood_dark_oak.png"));
    public static final Part BIRCH_BODY = new PartBodyWood(new ResourceLocation(Main.MODID, "textures/entity/car_wood_birch.png"));
    public static final Part JUNGLE_BODY = new PartBodyWood(new ResourceLocation(Main.MODID, "textures/entity/car_wood_jungle.png"));
    public static final Part SPRUCE_BODY = new PartBodyWood(new ResourceLocation(Main.MODID, "textures/entity/car_wood_spruce.png"));

    public static final Part BIG_OAK_BODY = new PartBodyBigWood(new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_oak.png"));
    public static final Part BIG_ACACIA_BODY = new PartBodyBigWood(new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_acacia.png"));
    public static final Part BIG_DARK_OAK_BODY = new PartBodyBigWood(new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_dark_oak.png"));
    public static final Part BIG_BIRCH_BODY = new PartBodyBigWood(new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_birch.png"));
    public static final Part BIG_JUNGLE_BODY = new PartBodyBigWood(new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_jungle.png"));
    public static final Part BIG_SPRUCE_BODY = new PartBodyBigWood(new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_spruce.png"));

    public static final Part BLACK_SPORT_BODY = new PartBodySport(new ResourceLocation(Main.MODID, "textures/entity/car_sport_black.png"));
    public static final Part BLUE_SPORT_BODY = new PartBodySport(new ResourceLocation(Main.MODID, "textures/entity/car_sport_blue.png"));
    public static final Part BROWN_SPORT_BODY = new PartBodySport(new ResourceLocation(Main.MODID, "textures/entity/car_sport_brown.png"));
    public static final Part CYAN_SPORT_BODY = new PartBodySport(new ResourceLocation(Main.MODID, "textures/entity/car_sport_cyan.png"));
    public static final Part GRAY_SPORT_BODY = new PartBodySport(new ResourceLocation(Main.MODID, "textures/entity/car_sport_gray.png"));
    public static final Part GREEN_SPORT_BODY = new PartBodySport(new ResourceLocation(Main.MODID, "textures/entity/car_sport_green.png"));
    public static final Part LIGHT_BLUE_SPORT_BODY = new PartBodySport(new ResourceLocation(Main.MODID, "textures/entity/car_sport_light_blue.png"));
    public static final Part LIME_SPORT_BODY = new PartBodySport(new ResourceLocation(Main.MODID, "textures/entity/car_sport_lime.png"));
    public static final Part MAGENTA_SPORT_BODY = new PartBodySport(new ResourceLocation(Main.MODID, "textures/entity/car_sport_magenta.png"));
    public static final Part ORANGE_SPORT_BODY = new PartBodySport(new ResourceLocation(Main.MODID, "textures/entity/car_sport_orange.png"));
    public static final Part PINK_SPORT_BODY = new PartBodySport(new ResourceLocation(Main.MODID, "textures/entity/car_sport_pink.png"));
    public static final Part PURPLE_SPORT_BODY = new PartBodySport(new ResourceLocation(Main.MODID, "textures/entity/car_sport_purple.png"));
    public static final Part RED_SPORT_BODY = new PartBodySport(new ResourceLocation(Main.MODID, "textures/entity/car_sport_red.png"));
    public static final Part SILVER_SPORT_BODY = new PartBodySport(new ResourceLocation(Main.MODID, "textures/entity/car_sport_silver.png"));
    public static final Part WHITE_SPORT_BODY = new PartBodySport(new ResourceLocation(Main.MODID, "textures/entity/car_sport_white.png"));
    public static final Part YELLOW_SPORT_BODY = new PartBodySport(new ResourceLocation(Main.MODID, "textures/entity/car_sport_yellow.png"));

    public static final Part BLACK_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_black.png"));
    public static final Part BLUE_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_blue.png"));
    public static final Part BROWN_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_brown.png"));
    public static final Part CYAN_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_cyan.png"));
    public static final Part GRAY_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_gray.png"));
    public static final Part GREEN_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_green.png"));
    public static final Part LIGHT_BLUE_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_light_blue.png"));
    public static final Part LIME_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_lime.png"));
    public static final Part MAGENTA_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_magenta.png"));
    public static final Part ORANGE_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_orange.png"));
    public static final Part PINK_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_pink.png"));
    public static final Part PURPLE_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_purple.png"));
    public static final Part RED_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_red.png"));
    public static final Part SILVER_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_silver.png"));
    public static final Part WHITE_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_white.png"));
    public static final Part YELLOW_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_yellow.png"));

    public static final Part BLACK_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_black.png"));
    public static final Part BLUE_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_blue.png"));
    public static final Part BROWN_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_brown.png"));
    public static final Part CYAN_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_cyan.png"));
    public static final Part GRAY_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_gray.png"));
    public static final Part GREEN_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_green.png"));
    public static final Part LIGHT_BLUE_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_light_blue.png"));
    public static final Part LIME_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_lime.png"));
    public static final Part MAGENTA_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_magenta.png"));
    public static final Part ORANGE_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_orange.png"));
    public static final Part PINK_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_pink.png"));
    public static final Part PURPLE_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_purple.png"));
    public static final Part RED_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_red.png"));
    public static final Part SILVER_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_silver.png"));
    public static final Part WHITE_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_white.png"));
    public static final Part YELLOW_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_yellow.png"));

    public static final Part OAK_BUMPER = new PartBumper(new ResourceLocation("textures/blocks/planks_oak.png"));
    public static final Part ACACIA_BUMPER = new PartBumper(new ResourceLocation("textures/blocks/planks_acacia.png"));
    public static final Part DARK_OAK_BUMPER = new PartBumper(new ResourceLocation("textures/blocks/planks_big_oak.png"));
    public static final Part BIRCH_BUMPER = new PartBumper(new ResourceLocation("textures/blocks/planks_birch.png"));
    public static final Part JUNGLE_BUMPER = new PartBumper(new ResourceLocation("textures/blocks/planks_jungle.png"));
    public static final Part SPRUCE_BUMPER = new PartBumper(new ResourceLocation("textures/blocks/planks_spruce.png"));

    public static final Part SMALL_TANK = new PartTank(500);
    public static final Part MEDIUM_TANK = new PartTank(1000);
    public static final Part LARGE_TANK = new PartTank(1500);

    public static final Part WHEEL = new PartWheel(new OBJModel(
            new ResourceLocation(Main.MODID, "models/entity/wheel.obj"),
            new ResourceLocation(Main.MODID, "textures/entity/wheel.png")),
            80F
    );

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
