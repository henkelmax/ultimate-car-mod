package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.corelib.client.obj.OBJModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class PartRegistry {

    public static final Part ENGINE_3_CYLINDER = new PartEngine3Cylinder();
    public static final Part ENGINE_6_CYLINDER = new PartEngine6Cylinder();
    public static final Part ENGINE_TRUCK = new PartEngineTruck();

    public static final Part OAK_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(ResourceLocation.withDefaultNamespace("textures/block/oak_planks.png"));
    public static final Part ACACIA_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(ResourceLocation.withDefaultNamespace("textures/block/acacia_planks.png"));
    public static final Part DARK_OAK_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(ResourceLocation.withDefaultNamespace("textures/block/dark_oak_planks.png"));
    public static final Part BIRCH_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(ResourceLocation.withDefaultNamespace("textures/block/birch_planks.png"));
    public static final Part JUNGLE_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(ResourceLocation.withDefaultNamespace("textures/block/jungle_planks.png"));
    public static final Part SPRUCE_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(ResourceLocation.withDefaultNamespace("textures/block/spruce_planks.png"));
    public static final Part CRIMSON_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(ResourceLocation.withDefaultNamespace("textures/block/crimson_planks.png"));
    public static final Part WARPED_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(ResourceLocation.withDefaultNamespace("textures/block/warped_planks.png"));

    public static final Part IRON_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(ResourceLocation.withDefaultNamespace("textures/block/iron_block.png"));
    public static final Part DIAMOND_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(ResourceLocation.withDefaultNamespace("textures/block/diamond_block.png"));
    public static final Part GOLD_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(ResourceLocation.withDefaultNamespace("textures/block/gold_block.png"));
    public static final Part EMERALD_LICENSE_PLATE_HOLDER = new PartLicensePlateHolder(ResourceLocation.withDefaultNamespace("textures/block/emerald_block.png"));

    public static final Part OAK_BODY = new PartBodyWood(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_wood_oak.png"), "oak");
    public static final Part ACACIA_BODY = new PartBodyWood(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_wood_acacia.png"), "acacia");
    public static final Part DARK_OAK_BODY = new PartBodyWood(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_wood_dark_oak.png"), "dark_oak");
    public static final Part BIRCH_BODY = new PartBodyWood(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_wood_birch.png"), "birch");
    public static final Part JUNGLE_BODY = new PartBodyWood(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_wood_jungle.png"), "jungle");
    public static final Part SPRUCE_BODY = new PartBodyWood(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_wood_spruce.png"), "spruce");
    public static final Part CRIMSON_BODY = new PartBodyWood(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_wood_crimson.png"), "crimson");
    public static final Part WARPED_BODY = new PartBodyWood(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_wood_warped.png"), "warped");

    public static final Part BIG_OAK_BODY = new PartBodyBigWood(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_big_wood_oak.png"), "oak");
    public static final Part BIG_ACACIA_BODY = new PartBodyBigWood(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_big_wood_acacia.png"), "acacia");
    public static final Part BIG_DARK_OAK_BODY = new PartBodyBigWood(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_big_wood_dark_oak.png"), "dark_oak");
    public static final Part BIG_BIRCH_BODY = new PartBodyBigWood(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_big_wood_birch.png"), "birch");
    public static final Part BIG_JUNGLE_BODY = new PartBodyBigWood(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_big_wood_jungle.png"), "jungle");
    public static final Part BIG_SPRUCE_BODY = new PartBodyBigWood(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_big_wood_spruce.png"), "spruce");
    public static final Part BIG_CRIMSON_BODY = new PartBodyBigWood(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_big_wood_crimson.png"), "crimson");
    public static final Part BIG_WARPED_BODY = new PartBodyBigWood(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_big_wood_warped.png"), "warped");

    public static final Part BLACK_SPORT_BODY = new PartBodySport(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_sport_black.png"), "black");
    public static final Part BLUE_SPORT_BODY = new PartBodySport(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_sport_blue.png"), "blue");
    public static final Part BROWN_SPORT_BODY = new PartBodySport(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_sport_brown.png"), "brown");
    public static final Part CYAN_SPORT_BODY = new PartBodySport(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_sport_cyan.png"), "cyan");
    public static final Part GRAY_SPORT_BODY = new PartBodySport(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_sport_gray.png"), "gray");
    public static final Part GREEN_SPORT_BODY = new PartBodySport(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_sport_green.png"), "green");
    public static final Part LIGHT_BLUE_SPORT_BODY = new PartBodySport(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_sport_light_blue.png"), "light_blue");
    public static final Part LIME_SPORT_BODY = new PartBodySport(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_sport_lime.png"), "lime");
    public static final Part MAGENTA_SPORT_BODY = new PartBodySport(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_sport_magenta.png"), "magenta");
    public static final Part ORANGE_SPORT_BODY = new PartBodySport(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_sport_orange.png"), "orange");
    public static final Part PINK_SPORT_BODY = new PartBodySport(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_sport_pink.png"), "pink");
    public static final Part PURPLE_SPORT_BODY = new PartBodySport(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_sport_purple.png"), "purple");
    public static final Part RED_SPORT_BODY = new PartBodySport(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_sport_red.png"), "red");
    public static final Part LIGHT_GRAY_SPORT_BODY = new PartBodySport(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_sport_light_gray.png"), "light_gray");
    public static final Part WHITE_SPORT_BODY = new PartBodySport(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_sport_white.png"), "white");
    public static final Part YELLOW_SPORT_BODY = new PartBodySport(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_sport_yellow.png"), "yellow");

    public static final Part BLACK_TRANSPORTER_BODY = new PartBodyTransporter(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_transporter_black.png"), "black");
    public static final Part BLUE_TRANSPORTER_BODY = new PartBodyTransporter(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_transporter_blue.png"), "blue");
    public static final Part BROWN_TRANSPORTER_BODY = new PartBodyTransporter(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_transporter_brown.png"), "brown");
    public static final Part CYAN_TRANSPORTER_BODY = new PartBodyTransporter(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_transporter_cyan.png"), "cyan");
    public static final Part GRAY_TRANSPORTER_BODY = new PartBodyTransporter(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_transporter_gray.png"), "gray");
    public static final Part GREEN_TRANSPORTER_BODY = new PartBodyTransporter(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_transporter_green.png"), "green");
    public static final Part LIGHT_BLUE_TRANSPORTER_BODY = new PartBodyTransporter(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_transporter_light_blue.png"), "light_blue");
    public static final Part LIME_TRANSPORTER_BODY = new PartBodyTransporter(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_transporter_lime.png"), "lime");
    public static final Part MAGENTA_TRANSPORTER_BODY = new PartBodyTransporter(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_transporter_magenta.png"), "magenta");
    public static final Part ORANGE_TRANSPORTER_BODY = new PartBodyTransporter(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_transporter_orange.png"), "orange");
    public static final Part PINK_TRANSPORTER_BODY = new PartBodyTransporter(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_transporter_pink.png"), "pink");
    public static final Part PURPLE_TRANSPORTER_BODY = new PartBodyTransporter(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_transporter_purple.png"), "purple");
    public static final Part RED_TRANSPORTER_BODY = new PartBodyTransporter(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_transporter_red.png"), "red");
    public static final Part LIGHT_GRAY_TRANSPORTER_BODY = new PartBodyTransporter(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_transporter_light_gray.png"), "light_gray");
    public static final Part WHITE_TRANSPORTER_BODY = new PartBodyTransporter(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_transporter_white.png"), "white");
    public static final Part YELLOW_TRANSPORTER_BODY = new PartBodyTransporter(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_transporter_yellow.png"), "yellow");

    public static final Part BLACK_SUV_BODY = new PartBodySUV(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_suv_black.png"), "black");
    public static final Part BLUE_SUV_BODY = new PartBodySUV(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_suv_blue.png"), "blue");
    public static final Part BROWN_SUV_BODY = new PartBodySUV(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_suv_brown.png"), "brown");
    public static final Part CYAN_SUV_BODY = new PartBodySUV(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_suv_cyan.png"), "cyan");
    public static final Part GRAY_SUV_BODY = new PartBodySUV(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_suv_gray.png"), "gray");
    public static final Part GREEN_SUV_BODY = new PartBodySUV(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_suv_green.png"), "green");
    public static final Part LIGHT_BLUE_SUV_BODY = new PartBodySUV(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_suv_light_blue.png"), "light_blue");
    public static final Part LIME_SUV_BODY = new PartBodySUV(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_suv_lime.png"), "lime");
    public static final Part MAGENTA_SUV_BODY = new PartBodySUV(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_suv_magenta.png"), "magenta");
    public static final Part ORANGE_SUV_BODY = new PartBodySUV(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_suv_orange.png"), "orange");
    public static final Part PINK_SUV_BODY = new PartBodySUV(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_suv_pink.png"), "pink");
    public static final Part PURPLE_SUV_BODY = new PartBodySUV(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_suv_purple.png"), "purple");
    public static final Part RED_SUV_BODY = new PartBodySUV(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_suv_red.png"), "red");
    public static final Part LIGHT_GRAY_SUV_BODY = new PartBodySUV(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_suv_light_gray.png"), "light_gray");
    public static final Part WHITE_SUV_BODY = new PartBodySUV(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_suv_white.png"), "white");
    public static final Part YELLOW_SUV_BODY = new PartBodySUV(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/car_suv_yellow.png"), "yellow");

    public static final Part BLACK_CONTAINER = new PartContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/container_black.png"));
    public static final Part BLUE_CONTAINER = new PartContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/container_blue.png"));
    public static final Part BROWN_CONTAINER = new PartContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/container_brown.png"));
    public static final Part CYAN_CONTAINER = new PartContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/container_cyan.png"));
    public static final Part GRAY_CONTAINER = new PartContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/container_gray.png"));
    public static final Part GREEN_CONTAINER = new PartContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/container_green.png"));
    public static final Part LIGHT_BLUE_CONTAINER = new PartContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/container_light_blue.png"));
    public static final Part LIME_CONTAINER = new PartContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/container_lime.png"));
    public static final Part MAGENTA_CONTAINER = new PartContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/container_magenta.png"));
    public static final Part ORANGE_CONTAINER = new PartContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/container_orange.png"));
    public static final Part PINK_CONTAINER = new PartContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/container_pink.png"));
    public static final Part PURPLE_CONTAINER = new PartContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/container_purple.png"));
    public static final Part RED_CONTAINER = new PartContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/container_red.png"));
    public static final Part LIGHT_GRAY_CONTAINER = new PartContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/container_light_gray.png"));
    public static final Part WHITE_CONTAINER = new PartContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/container_white.png"));
    public static final Part YELLOW_CONTAINER = new PartContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/container_yellow.png"));

    public static final Part BLACK_TANK_CONTAINER = new PartTankContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/tank_container_black.png"));
    public static final Part BLUE_TANK_CONTAINER = new PartTankContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/tank_container_blue.png"));
    public static final Part BROWN_TANK_CONTAINER = new PartTankContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/tank_container_brown.png"));
    public static final Part CYAN_TANK_CONTAINER = new PartTankContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/tank_container_cyan.png"));
    public static final Part GRAY_TANK_CONTAINER = new PartTankContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/tank_container_gray.png"));
    public static final Part GREEN_TANK_CONTAINER = new PartTankContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/tank_container_green.png"));
    public static final Part LIGHT_BLUE_TANK_CONTAINER = new PartTankContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/tank_container_light_blue.png"));
    public static final Part LIME_TANK_CONTAINER = new PartTankContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/tank_container_lime.png"));
    public static final Part MAGENTA_TANK_CONTAINER = new PartTankContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/tank_container_magenta.png"));
    public static final Part ORANGE_TANK_CONTAINER = new PartTankContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/tank_container_orange.png"));
    public static final Part PINK_TANK_CONTAINER = new PartTankContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/tank_container_pink.png"));
    public static final Part PURPLE_TANK_CONTAINER = new PartTankContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/tank_container_purple.png"));
    public static final Part RED_TANK_CONTAINER = new PartTankContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/tank_container_red.png"));
    public static final Part LIGHT_GRAY_TANK_CONTAINER = new PartTankContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/tank_container_light_gray.png"));
    public static final Part WHITE_TANK_CONTAINER = new PartTankContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/tank_container_white.png"));
    public static final Part YELLOW_TANK_CONTAINER = new PartTankContainer(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/tank_container_yellow.png"));

    public static final Part OAK_BUMPER = new PartBumper(ResourceLocation.withDefaultNamespace("textures/block/oak_planks.png"));
    public static final Part ACACIA_BUMPER = new PartBumper(ResourceLocation.withDefaultNamespace("textures/block/acacia_planks.png"));
    public static final Part DARK_OAK_BUMPER = new PartBumper(ResourceLocation.withDefaultNamespace("textures/block/dark_oak_planks.png"));
    public static final Part BIRCH_BUMPER = new PartBumper(ResourceLocation.withDefaultNamespace("textures/block/birch_planks.png"));
    public static final Part JUNGLE_BUMPER = new PartBumper(ResourceLocation.withDefaultNamespace("textures/block/jungle_planks.png"));
    public static final Part SPRUCE_BUMPER = new PartBumper(ResourceLocation.withDefaultNamespace("textures/block/spruce_planks.png"));
    public static final Part CRIMSON_BUMPER = new PartBumper(ResourceLocation.withDefaultNamespace("textures/block/crimson_planks.png"));
    public static final Part WARPED_BUMPER = new PartBumper(ResourceLocation.withDefaultNamespace("textures/block/warped_planks.png"));

    public static final Part SMALL_TANK = new PartTank(() -> CarMod.SERVER_CONFIG.tankSmallMaxFuel.get());
    public static final Part MEDIUM_TANK = new PartTank(() -> CarMod.SERVER_CONFIG.tankMediumMaxFuel.get());
    public static final Part LARGE_TANK = new PartTank(() -> CarMod.SERVER_CONFIG.tankLargeMaxFuel.get());

    public static final Part WHEEL = new PartWheel(
            new OBJModel(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "models/entity/wheel.obj")),
            ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/wheel.png"),
            120F,
            0.5F
    );

    public static final Part BIG_WHEEL = new PartWheelBig(
            new OBJModel(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "models/entity/big_wheel.obj")),
            ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/entity/big_wheel.png"),
            105F,
            1F
    );

    public static boolean isValid(EntityGenericCar car, List<Component> messages) {
        return isValid(car.getModelParts(), messages);
    }

    public static boolean isValid(List<Part> modelParts, List<Component> messages) {
        int bodyAmount = Part.getAmount(modelParts, part -> part instanceof PartBody);
        if (bodyAmount <= 0) {
            messages.add(Component.translatable("message.parts.no_body"));
            return false;
        } else if (bodyAmount > 1) {
            messages.add(Component.translatable("message.parts.too_many_bodies"));
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
