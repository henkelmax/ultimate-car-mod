package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.*;

public class PartRegistry {

    public static final Part ENGINE_3_CYLINDER = new PartEngine3Cylinder();
    public static final Part ENGINE_6_CYLINDER = new PartEngine6Cylinder();
    public static final Part ENGINE_TRUCK = new PartEngineTruck();

    public static final Part OAK_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/block/oak_planks.png"));
    public static final Part ACACIA_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/block/acacia_planks.png"));
    public static final Part DARK_OAK_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/block/dark_oak_planks.png"));
    public static final Part BIRCH_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/block/birch_planks.png"));
    public static final Part JUNGLE_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/block/jungle_planks_.png"));
    public static final Part SPRUCE_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/block/spruce_planks.png"));

    public static final Part IRON_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/block/iron_block.png"));
    public static final Part DIAMOND_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/block/diamond_block.png"));
    public static final Part GOLD_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/block/gold_block.png"));
    public static final Part EMERALD_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(new ResourceLocation("textures/block/emerald_block.png"));

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
    public static final Part LIGHT_GRAY_SPORT_BODY = new PartBodySport(new ResourceLocation(Main.MODID, "textures/entity/car_sport_light_gray.png"));
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
    public static final Part LIGHT_GRAY_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_light_gray.png"));
    public static final Part WHITE_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_white.png"));
    public static final Part YELLOW_TRANSPORTER_BODY = new PartBodyTransporter(new ResourceLocation(Main.MODID, "textures/entity/car_transporter_yellow.png"));

    public static final Part BLACK_SUV_BODY = new PartBodySUV(new ResourceLocation(Main.MODID, "textures/entity/car_suv_black.png"));
    public static final Part BLUE_SUV_BODY = new PartBodySUV(new ResourceLocation(Main.MODID, "textures/entity/car_suv_blue.png"));
    public static final Part BROWN_SUV_BODY = new PartBodySUV(new ResourceLocation(Main.MODID, "textures/entity/car_suv_brown.png"));
    public static final Part CYAN_SUV_BODY = new PartBodySUV(new ResourceLocation(Main.MODID, "textures/entity/car_suv_cyan.png"));
    public static final Part GRAY_SUV_BODY = new PartBodySUV(new ResourceLocation(Main.MODID, "textures/entity/car_suv_gray.png"));
    public static final Part GREEN_SUV_BODY = new PartBodySUV(new ResourceLocation(Main.MODID, "textures/entity/car_suv_green.png"));
    public static final Part LIGHT_BLUE_SUV_BODY = new PartBodySUV(new ResourceLocation(Main.MODID, "textures/entity/car_suv_light_blue.png"));
    public static final Part LIME_SUV_BODY = new PartBodySUV(new ResourceLocation(Main.MODID, "textures/entity/car_suv_lime.png"));
    public static final Part MAGENTA_SUV_BODY = new PartBodySUV(new ResourceLocation(Main.MODID, "textures/entity/car_suv_magenta.png"));
    public static final Part ORANGE_SUV_BODY = new PartBodySUV(new ResourceLocation(Main.MODID, "textures/entity/car_suv_orange.png"));
    public static final Part PINK_SUV_BODY = new PartBodySUV(new ResourceLocation(Main.MODID, "textures/entity/car_suv_pink.png"));
    public static final Part PURPLE_SUV_BODY = new PartBodySUV(new ResourceLocation(Main.MODID, "textures/entity/car_suv_purple.png"));
    public static final Part RED_SUV_BODY = new PartBodySUV(new ResourceLocation(Main.MODID, "textures/entity/car_suv_red.png"));
    public static final Part LIGHT_GRAY_SUV_BODY = new PartBodySUV(new ResourceLocation(Main.MODID, "textures/entity/car_suv_light_gray.png"));
    public static final Part WHITE_SUV_BODY = new PartBodySUV(new ResourceLocation(Main.MODID, "textures/entity/car_suv_white.png"));
    public static final Part YELLOW_SUV_BODY = new PartBodySUV(new ResourceLocation(Main.MODID, "textures/entity/car_suv_yellow.png"));


    public static final Part BLACK_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/container_black.png"));
    public static final Part BLUE_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/container_blue.png"));
    public static final Part BROWN_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/container_brown.png"));
    public static final Part CYAN_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/container_cyan.png"));
    public static final Part GRAY_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/container_gray.png"));
    public static final Part GREEN_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/container_green.png"));
    public static final Part LIGHT_BLUE_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/container_light_blue.png"));
    public static final Part LIME_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/container_lime.png"));
    public static final Part MAGENTA_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/container_magenta.png"));
    public static final Part ORANGE_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/container_orange.png"));
    public static final Part PINK_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/container_pink.png"));
    public static final Part PURPLE_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/container_purple.png"));
    public static final Part RED_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/container_red.png"));
    public static final Part LIGHT_GRAY_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/container_light_gray.png"));
    public static final Part WHITE_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/container_white.png"));
    public static final Part YELLOW_CONTAINER = new PartContainer(new ResourceLocation(Main.MODID, "textures/entity/container_yellow.png"));

    public static final Part BLACK_TANK_CONTAINER = new PartTankContainer(new ResourceLocation(Main.MODID, "textures/entity/tank_container_black.png"));
    public static final Part BLUE_TANK_CONTAINER = new PartTankContainer(new ResourceLocation(Main.MODID, "textures/entity/tank_container_blue.png"));
    public static final Part BROWN_TANK_CONTAINER = new PartTankContainer(new ResourceLocation(Main.MODID, "textures/entity/tank_container_brown.png"));
    public static final Part CYAN_TANK_CONTAINER = new PartTankContainer(new ResourceLocation(Main.MODID, "textures/entity/tank_container_cyan.png"));
    public static final Part GRAY_TANK_CONTAINER = new PartTankContainer(new ResourceLocation(Main.MODID, "textures/entity/tank_container_gray.png"));
    public static final Part GREEN_TANK_CONTAINER = new PartTankContainer(new ResourceLocation(Main.MODID, "textures/entity/tank_container_green.png"));
    public static final Part LIGHT_BLUE_TANK_CONTAINER = new PartTankContainer(new ResourceLocation(Main.MODID, "textures/entity/tank_container_light_blue.png"));
    public static final Part LIME_TANK_CONTAINER = new PartTankContainer(new ResourceLocation(Main.MODID, "textures/entity/tank_container.png_lime"));
    public static final Part MAGENTA_TANK_CONTAINER = new PartTankContainer(new ResourceLocation(Main.MODID, "textures/entity/tank_container_magenta.png"));
    public static final Part ORANGE_TANK_CONTAINER = new PartTankContainer(new ResourceLocation(Main.MODID, "textures/entity/tank_container_orange.png"));
    public static final Part PINK_TANK_CONTAINER = new PartTankContainer(new ResourceLocation(Main.MODID, "textures/entity/tank_container_pink.png"));
    public static final Part PURPLE_TANK_CONTAINER = new PartTankContainer(new ResourceLocation(Main.MODID, "textures/entity/tank_container_purple.png"));
    public static final Part RED_TANK_CONTAINER = new PartTankContainer(new ResourceLocation(Main.MODID, "textures/entity/tank_container_red.png"));
    public static final Part LIGHT_GRAY_TANK_CONTAINER = new PartTankContainer(new ResourceLocation(Main.MODID, "textures/entity/tank_container_light_gray.png"));
    public static final Part WHITE_TANK_CONTAINER = new PartTankContainer(new ResourceLocation(Main.MODID, "textures/entity/tank_container_white.png"));
    public static final Part YELLOW_TANK_CONTAINER = new PartTankContainer(new ResourceLocation(Main.MODID, "textures/entity/tank_container_yellow.png"));

    public static final Part OAK_BUMPER = new PartBumper(new ResourceLocation("textures/block/oak_planks.png"));
    public static final Part ACACIA_BUMPER = new PartBumper(new ResourceLocation("textures/block/acacia_planks.png"));
    public static final Part DARK_OAK_BUMPER = new PartBumper(new ResourceLocation("textures/block/dark_oak_planks.png"));
    public static final Part BIRCH_BUMPER = new PartBumper(new ResourceLocation("textures/block/birch_planks.png"));
    public static final Part JUNGLE_BUMPER = new PartBumper(new ResourceLocation("textures/block/jungle_planks.png"));
    public static final Part SPRUCE_BUMPER = new PartBumper(new ResourceLocation("textures/block/spruce_planks.png"));

    public static final Part SMALL_TANK = new PartTank(500);
    public static final Part MEDIUM_TANK = new PartTank(1000);
    public static final Part LARGE_TANK = new PartTank(1500);

    public static final Part WHEEL = new PartWheel(new OBJModel(
            new ResourceLocation(Main.MODID, "models/entity/wheel.obj"),
            new ResourceLocation(Main.MODID, "textures/entity/wheel.png")),
            120F,
            0.5F
    );

    public static final Part BIG_WHEEL = new PartWheelBig(new OBJModel(
            new ResourceLocation(Main.MODID, "models/entity/big_wheel.obj"),
            new ResourceLocation(Main.MODID, "textures/entity/big_wheel.png")),
            105F,
            1F
    );

    public static boolean isValid(EntityGenericCar car, List<ITextComponent> messages) {
        return isValid(car.getModelParts(), messages);
    }

    public static boolean isValid(List<Part> modelParts, List<ITextComponent> messages) {
        int bodyAmount = Part.getAmount(modelParts, part -> part instanceof PartBody);
        if (bodyAmount <= 0) {
            messages.add(new TranslationTextComponent("message.parts.no_body"));
            return false;
        } else if (bodyAmount > 1) {
            messages.add(new TranslationTextComponent("message.parts.too_many_bodies"));
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
