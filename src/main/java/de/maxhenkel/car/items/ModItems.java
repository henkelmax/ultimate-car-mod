package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.parts.PartRegistry;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.tools.NoRegister;
import de.maxhenkel.tools.ReflectionTools;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static final ItemPainter PAINTER = new ItemPainter(false);
    public static final ItemPainter PAINTER_YELLOW = new ItemPainter(true);
    public static final ItemCanolaSeed CANOLA_SEEDS = new ItemCanolaSeed();
    public static final ItemCanola CANOLA = new ItemCanola();
    public static final ItemCanolaCake CANOLA_CAKE = new ItemCanolaCake();
    public static final ItemCraftingComponent IRON_STICK = new ItemCraftingComponent("iron_stick");
    public static final ItemCraftingComponent ENGINE_PISTON = new ItemCraftingComponent("engine_piston");
    public static final ItemCanister CANISTER = new ItemCanister();
    public static final ItemRepairKit REPAIR_KIT = new ItemRepairKit();
    public static final ItemRepairTool WRENCH = new ItemRepairTool("wrench");
    public static final ItemRepairTool SCREW_DRIVER = new ItemRepairTool("screw_driver");
    public static final ItemRepairTool HAMMER = new ItemRepairTool("hammer");
    public static final ItemCraftingComponent CABLE_INSULATOR = new ItemCraftingComponent("cable_insulator");
    public static final ItemKey KEY = new ItemKey();
    public static final ItemBattery BATTERY = new ItemBattery();

    public static final ItemLicensePlate LICENSE_PLATE = new ItemLicensePlate("license_plate");
    /**
     * ---- CAR PARTS ----
     **/

    public static final ItemCarPart ENGINE_3_CYLINDER = new ItemCarPart("engine_3_cylinder", PartRegistry.ENGINE_3_CYLINDER);
    public static final ItemCarPart ENGINE_6_CYLINDER = new ItemCarPart("engine_6_cylinder", PartRegistry.ENGINE_6_CYLINDER);
    public static final ItemCarPart ENGINE_TRUCK = new ItemCarPart("engine_truck", PartRegistry.ENGINE_TRUCK);

    public static final ItemCarPart WHEEL = new ItemCarPart("wheel", PartRegistry.WHEEL);
    public static final ItemCarPart BIG_WHEEL = new ItemCarPart("big_wheel", PartRegistry.BIG_WHEEL);

    public static final ItemCarPart OAK_BODY = new ItemCarPart("oak_body", PartRegistry.OAK_BODY);
    public static final ItemCarPart ACACIA_BODY = new ItemCarPart("acacia_body", PartRegistry.ACACIA_BODY);
    public static final ItemCarPart DARK_OAK_BODY = new ItemCarPart("dark_oak_body", PartRegistry.DARK_OAK_BODY);
    public static final ItemCarPart BIRCH_BODY = new ItemCarPart("birch_body", PartRegistry.BIRCH_BODY);
    public static final ItemCarPart JUNGLE_BODY = new ItemCarPart("jungle_body", PartRegistry.JUNGLE_BODY);
    public static final ItemCarPart SPRUCE_BODY = new ItemCarPart("spruce_body", PartRegistry.SPRUCE_BODY);

    public static final ItemCarPart[] WOOD_BODIES = new ItemCarPart[]{
            OAK_BODY, SPRUCE_BODY, BIRCH_BODY, JUNGLE_BODY, ACACIA_BODY, DARK_OAK_BODY
    };

    public static final ItemCarPart BIG_OAK_BODY = new ItemCarPart("big_oak_body", PartRegistry.BIG_OAK_BODY);
    public static final ItemCarPart BIG_ACACIA_BODY = new ItemCarPart("big_acacia_body", PartRegistry.BIG_ACACIA_BODY);
    public static final ItemCarPart BIG_DARK_OAK_BODY = new ItemCarPart("big_dark_oak_body", PartRegistry.BIG_DARK_OAK_BODY);
    public static final ItemCarPart BIG_BIRCH_BODY = new ItemCarPart("big_birch_body", PartRegistry.BIG_BIRCH_BODY);
    public static final ItemCarPart BIG_JUNGLE_BODY = new ItemCarPart("big_jungle_body", PartRegistry.BIG_JUNGLE_BODY);
    public static final ItemCarPart BIG_SPRUCE_BODY = new ItemCarPart("big_spruce_body", PartRegistry.BIG_SPRUCE_BODY);

    public static final ItemCarPart[] BIG_WOOD_BODIES = new ItemCarPart[]{
            BIG_OAK_BODY, BIG_SPRUCE_BODY, BIG_BIRCH_BODY, BIG_JUNGLE_BODY, BIG_ACACIA_BODY, BIG_DARK_OAK_BODY
    };

    public static final ItemCarPart BLACK_TRANSPORTER_BODY = new ItemCarPart("black_transporter_body", PartRegistry.BLACK_TRANSPORTER_BODY);
    public static final ItemCarPart BLUE_TRANSPORTER_BODY = new ItemCarPart("blue_transporter_body", PartRegistry.BLUE_TRANSPORTER_BODY);
    public static final ItemCarPart BROWN_TRANSPORTER_BODY = new ItemCarPart("brown_transporter_body", PartRegistry.BROWN_TRANSPORTER_BODY);
    public static final ItemCarPart CYAN_TRANSPORTER_BODY = new ItemCarPart("cyan_transporter_body", PartRegistry.CYAN_TRANSPORTER_BODY);
    public static final ItemCarPart GRAY_TRANSPORTER_BODY = new ItemCarPart("gray_transporter_body", PartRegistry.GRAY_TRANSPORTER_BODY);
    public static final ItemCarPart GREEN_TRANSPORTER_BODY = new ItemCarPart("green_transporter_body", PartRegistry.GREEN_TRANSPORTER_BODY);
    public static final ItemCarPart LIGHT_BLUE_TRANSPORTER_BODY = new ItemCarPart("light_blue_transporter_body", PartRegistry.LIGHT_BLUE_TRANSPORTER_BODY);
    public static final ItemCarPart LIME_TRANSPORTER_BODY = new ItemCarPart("lime_transporter_body", PartRegistry.LIME_TRANSPORTER_BODY);
    public static final ItemCarPart MAGENTA_TRANSPORTER_BODY = new ItemCarPart("magenta_transporter_body", PartRegistry.MAGENTA_TRANSPORTER_BODY);
    public static final ItemCarPart ORANGE_TRANSPORTER_BODY = new ItemCarPart("orange_transporter_body", PartRegistry.ORANGE_TRANSPORTER_BODY);
    public static final ItemCarPart PINK_TRANSPORTER_BODY = new ItemCarPart("pink_transporter_body", PartRegistry.PINK_TRANSPORTER_BODY);
    public static final ItemCarPart PURPLE_TRANSPORTER_BODY = new ItemCarPart("purple_transporter_body", PartRegistry.PURPLE_TRANSPORTER_BODY);
    public static final ItemCarPart RED_TRANSPORTER_BODY = new ItemCarPart("red_transporter_body", PartRegistry.RED_TRANSPORTER_BODY);
    public static final ItemCarPart LIGHT_GRAY_TRANSPORTER_BODY = new ItemCarPart("light_gray_transporter_body", PartRegistry.LIGHT_GRAY_TRANSPORTER_BODY);
    public static final ItemCarPart WHITE_TRANSPORTER_BODY = new ItemCarPart("white_transporter_body", PartRegistry.WHITE_TRANSPORTER_BODY);
    public static final ItemCarPart YELLOW_TRANSPORTER_BODY = new ItemCarPart("yellow_transporter_body", PartRegistry.YELLOW_TRANSPORTER_BODY);

    public static final ItemCarPart[] TRANSPORTER_BODIES = new ItemCarPart[]{
            WHITE_TRANSPORTER_BODY, ORANGE_TRANSPORTER_BODY, MAGENTA_TRANSPORTER_BODY, LIGHT_BLUE_TRANSPORTER_BODY, YELLOW_TRANSPORTER_BODY,
            LIME_TRANSPORTER_BODY, PINK_TRANSPORTER_BODY, GRAY_TRANSPORTER_BODY, LIGHT_GRAY_TRANSPORTER_BODY, CYAN_TRANSPORTER_BODY,
            PURPLE_TRANSPORTER_BODY, BLUE_TRANSPORTER_BODY, BROWN_TRANSPORTER_BODY, GREEN_TRANSPORTER_BODY, RED_TRANSPORTER_BODY, BLACK_TRANSPORTER_BODY
    };

    public static final ItemCarPart BLACK_SUV_BODY = new ItemCarPart("black_suv_body", PartRegistry.BLACK_SUV_BODY);
    public static final ItemCarPart BLUE_SUV_BODY = new ItemCarPart("blue_suv_body", PartRegistry.BLUE_SUV_BODY);
    public static final ItemCarPart BROWN_SUV_BODY = new ItemCarPart("brown_suv_body", PartRegistry.BROWN_SUV_BODY);
    public static final ItemCarPart CYAN_SUV_BODY = new ItemCarPart("cyan_suv_body", PartRegistry.CYAN_SUV_BODY);
    public static final ItemCarPart GRAY_SUV_BODY = new ItemCarPart("gray_suv_body", PartRegistry.GRAY_SUV_BODY);
    public static final ItemCarPart GREEN_SUV_BODY = new ItemCarPart("green_suv_body", PartRegistry.GREEN_SUV_BODY);
    public static final ItemCarPart LIGHT_BLUE_SUV_BODY = new ItemCarPart("light_blue_suv_body", PartRegistry.LIGHT_BLUE_SUV_BODY);
    public static final ItemCarPart LIME_SUV_BODY = new ItemCarPart("lime_suv_body", PartRegistry.LIME_SUV_BODY);
    public static final ItemCarPart MAGENTA_SUV_BODY = new ItemCarPart("magenta_suv_body", PartRegistry.MAGENTA_SUV_BODY);
    public static final ItemCarPart ORANGE_SUV_BODY = new ItemCarPart("orange_suv_body", PartRegistry.ORANGE_SUV_BODY);
    public static final ItemCarPart PINK_SUV_BODY = new ItemCarPart("pink_suv_body", PartRegistry.PINK_SUV_BODY);
    public static final ItemCarPart PURPLE_SUV_BODY = new ItemCarPart("purple_suv_body", PartRegistry.PURPLE_SUV_BODY);
    public static final ItemCarPart RED_SUV_BODY = new ItemCarPart("red_suv_body", PartRegistry.RED_SUV_BODY);
    public static final ItemCarPart LIGHT_GRAY_SUV_BODY = new ItemCarPart("light_gray_suv_body", PartRegistry.LIGHT_GRAY_SUV_BODY);
    public static final ItemCarPart WHITE_SUV_BODY = new ItemCarPart("white_suv_body", PartRegistry.WHITE_SUV_BODY);
    public static final ItemCarPart YELLOW_SUV_BODY = new ItemCarPart("yellow_suv_body", PartRegistry.YELLOW_SUV_BODY);

    public static final ItemCarPart[] SUV_BODIES = new ItemCarPart[]{
            WHITE_SUV_BODY, ORANGE_SUV_BODY, MAGENTA_SUV_BODY, LIGHT_BLUE_SUV_BODY, YELLOW_SUV_BODY,
            LIME_SUV_BODY, PINK_SUV_BODY, GRAY_SUV_BODY, LIGHT_GRAY_SUV_BODY, CYAN_SUV_BODY,
            PURPLE_SUV_BODY, BLUE_SUV_BODY, BROWN_SUV_BODY, GREEN_SUV_BODY, RED_SUV_BODY, BLACK_SUV_BODY
    };

    public static final ItemCarPart BLACK_SPORT_BODY = new ItemCarPart("black_sport_body", PartRegistry.BLACK_SPORT_BODY);
    public static final ItemCarPart BLUE_SPORT_BODY = new ItemCarPart("blue_sport_body", PartRegistry.BLUE_SPORT_BODY);
    public static final ItemCarPart BROWN_SPORT_BODY = new ItemCarPart("brown_sport_body", PartRegistry.BROWN_SPORT_BODY);
    public static final ItemCarPart CYAN_SPORT_BODY = new ItemCarPart("cyan_sport_body", PartRegistry.CYAN_SPORT_BODY);
    public static final ItemCarPart GRAY_SPORT_BODY = new ItemCarPart("gray_sport_body", PartRegistry.GRAY_SPORT_BODY);
    public static final ItemCarPart GREEN_SPORT_BODY = new ItemCarPart("green_sport_body", PartRegistry.GREEN_SPORT_BODY);
    public static final ItemCarPart LIGHT_BLUE_SPORT_BODY = new ItemCarPart("light_blue_sport_body", PartRegistry.LIGHT_BLUE_SPORT_BODY);
    public static final ItemCarPart LIME_SPORT_BODY = new ItemCarPart("lime_sport_body", PartRegistry.LIME_SPORT_BODY);
    public static final ItemCarPart MAGENTA_SPORT_BODY = new ItemCarPart("magenta_sport_body", PartRegistry.MAGENTA_SPORT_BODY);
    public static final ItemCarPart ORANGE_SPORT_BODY = new ItemCarPart("orange_sport_body", PartRegistry.ORANGE_SPORT_BODY);
    public static final ItemCarPart PINK_SPORT_BODY = new ItemCarPart("pink_sport_body", PartRegistry.PINK_SPORT_BODY);
    public static final ItemCarPart PURPLE_SPORT_BODY = new ItemCarPart("purple_sport_body", PartRegistry.PURPLE_SPORT_BODY);
    public static final ItemCarPart RED_SPORT_BODY = new ItemCarPart("red_sport_body", PartRegistry.RED_SPORT_BODY);
    public static final ItemCarPart LIGHT_GRAY_SPORT_BODY = new ItemCarPart("light_gray_sport_body", PartRegistry.LIGHT_GRAY_SPORT_BODY);
    public static final ItemCarPart WHITE_SPORT_BODY = new ItemCarPart("white_sport_body", PartRegistry.WHITE_SPORT_BODY);
    public static final ItemCarPart YELLOW_SPORT_BODY = new ItemCarPart("yellow_sport_body", PartRegistry.YELLOW_SPORT_BODY);

    public static final ItemCarPart[] SPORT_BODIES = new ItemCarPart[]{
            WHITE_SPORT_BODY, ORANGE_SPORT_BODY, MAGENTA_SPORT_BODY, LIGHT_BLUE_SPORT_BODY, YELLOW_SPORT_BODY,
            LIME_SPORT_BODY, PINK_SPORT_BODY, GRAY_SPORT_BODY, LIGHT_GRAY_SPORT_BODY, CYAN_SPORT_BODY,
            PURPLE_SPORT_BODY, BLUE_SPORT_BODY, BROWN_SPORT_BODY, GREEN_SPORT_BODY, RED_SPORT_BODY, BLACK_SPORT_BODY
    };

    public static final ItemCarPart BLACK_CONTAINER = new ItemCarPart("black_container", PartRegistry.BLACK_CONTAINER);
    public static final ItemCarPart BLUE_CONTAINER = new ItemCarPart("blue_container", PartRegistry.BLUE_CONTAINER);
    public static final ItemCarPart BROWN_CONTAINER = new ItemCarPart("brown_container", PartRegistry.BROWN_CONTAINER);
    public static final ItemCarPart CYAN_CONTAINER = new ItemCarPart("cyan_container", PartRegistry.CYAN_CONTAINER);
    public static final ItemCarPart GRAY_CONTAINER = new ItemCarPart("gray_container", PartRegistry.GRAY_CONTAINER);
    public static final ItemCarPart GREEN_CONTAINER = new ItemCarPart("green_container", PartRegistry.GREEN_CONTAINER);
    public static final ItemCarPart LIGHT_BLUE_CONTAINER = new ItemCarPart("light_blue_container", PartRegistry.LIGHT_BLUE_CONTAINER);
    public static final ItemCarPart LIME_CONTAINER = new ItemCarPart("lime_container", PartRegistry.LIME_CONTAINER);
    public static final ItemCarPart MAGENTA_CONTAINER = new ItemCarPart("magenta_container", PartRegistry.MAGENTA_CONTAINER);
    public static final ItemCarPart ORANGE_CONTAINER = new ItemCarPart("orange_container", PartRegistry.ORANGE_CONTAINER);
    public static final ItemCarPart PINK_CONTAINER = new ItemCarPart("pink_container", PartRegistry.PINK_CONTAINER);
    public static final ItemCarPart PURPLE_CONTAINER = new ItemCarPart("purple_container", PartRegistry.PURPLE_CONTAINER);
    public static final ItemCarPart RED_CONTAINER = new ItemCarPart("red_container", PartRegistry.RED_CONTAINER);
    public static final ItemCarPart LIGHT_GRAY_CONTAINER = new ItemCarPart("light_gray_container", PartRegistry.LIGHT_GRAY_CONTAINER);
    public static final ItemCarPart WHITE_CONTAINER = new ItemCarPart("white_container", PartRegistry.WHITE_CONTAINER);
    public static final ItemCarPart YELLOW_CONTAINER = new ItemCarPart("yellow_container", PartRegistry.YELLOW_CONTAINER);

    public static final ItemCarPart[] CONTAINERS = new ItemCarPart[]{
            WHITE_CONTAINER, ORANGE_CONTAINER, MAGENTA_CONTAINER, LIGHT_BLUE_CONTAINER, YELLOW_CONTAINER,
            LIME_CONTAINER, PINK_CONTAINER, GRAY_CONTAINER, LIGHT_GRAY_CONTAINER, CYAN_CONTAINER,
            PURPLE_CONTAINER, BLUE_CONTAINER, BROWN_CONTAINER, GREEN_CONTAINER, RED_CONTAINER, BLACK_CONTAINER
    };

    public static final ItemCarPart BLACK_TANK_CONTAINER = new ItemCarPart("black_tank_container", PartRegistry.BLACK_TANK_CONTAINER);
    public static final ItemCarPart BLUE_TANK_CONTAINER = new ItemCarPart("blue_tank_container", PartRegistry.BLUE_TANK_CONTAINER);
    public static final ItemCarPart BROWN_TANK_CONTAINER = new ItemCarPart("brown_tank_container", PartRegistry.BROWN_TANK_CONTAINER);
    public static final ItemCarPart CYAN_TANK_CONTAINER = new ItemCarPart("cyan_tank_container", PartRegistry.CYAN_TANK_CONTAINER);
    public static final ItemCarPart GRAY_TANK_CONTAINER = new ItemCarPart("gray_tank_container", PartRegistry.GRAY_TANK_CONTAINER);
    public static final ItemCarPart GREEN_TANK_CONTAINER = new ItemCarPart("green_tank_container", PartRegistry.GREEN_TANK_CONTAINER);
    public static final ItemCarPart LIGHT_BLUE_TANK_CONTAINER = new ItemCarPart("light_blue_tank_container", PartRegistry.LIGHT_BLUE_TANK_CONTAINER);
    public static final ItemCarPart LIME_TANK_CONTAINER = new ItemCarPart("lime_tank_container", PartRegistry.LIME_TANK_CONTAINER);
    public static final ItemCarPart MAGENTA_TANK_CONTAINER = new ItemCarPart("magenta_tank_container", PartRegistry.MAGENTA_TANK_CONTAINER);
    public static final ItemCarPart ORANGE_TANK_CONTAINER = new ItemCarPart("orange_tank_container", PartRegistry.ORANGE_TANK_CONTAINER);
    public static final ItemCarPart PINK_TANK_CONTAINER = new ItemCarPart("pink_tank_container", PartRegistry.PINK_TANK_CONTAINER);
    public static final ItemCarPart PURPLE_TANK_CONTAINER = new ItemCarPart("purple_tank_container", PartRegistry.PURPLE_TANK_CONTAINER);
    public static final ItemCarPart RED_TANK_CONTAINER = new ItemCarPart("red_tank_container", PartRegistry.RED_TANK_CONTAINER);
    public static final ItemCarPart LIGHT_GRAY_TANK_CONTAINER = new ItemCarPart("light_gray_tank_container", PartRegistry.LIGHT_GRAY_TANK_CONTAINER);
    public static final ItemCarPart WHITE_TANK_CONTAINER = new ItemCarPart("white_tank_container", PartRegistry.WHITE_TANK_CONTAINER);
    public static final ItemCarPart YELLOW_TANK_CONTAINER = new ItemCarPart("yellow_tank_container", PartRegistry.YELLOW_TANK_CONTAINER);

    public static final ItemCarPart[] TANK_CONTAINERS = new ItemCarPart[]{
            WHITE_TANK_CONTAINER, ORANGE_TANK_CONTAINER, MAGENTA_TANK_CONTAINER, LIGHT_BLUE_TANK_CONTAINER, YELLOW_TANK_CONTAINER,
            LIME_TANK_CONTAINER, PINK_TANK_CONTAINER, GRAY_TANK_CONTAINER, LIGHT_GRAY_TANK_CONTAINER, CYAN_TANK_CONTAINER,
            PURPLE_TANK_CONTAINER, BLUE_TANK_CONTAINER, BROWN_TANK_CONTAINER, GREEN_TANK_CONTAINER, RED_TANK_CONTAINER, BLACK_TANK_CONTAINER
    };

    public static final ItemCarPart OAK_BUMPER = new ItemCarPart("oak_bumper", PartRegistry.OAK_BUMPER);
    public static final ItemCarPart ACACIA_BUMPER = new ItemCarPart("acacia_bumper", PartRegistry.ACACIA_BUMPER);
    public static final ItemCarPart DARK_OAK_BUMPER = new ItemCarPart("dark_oak_bumper", PartRegistry.DARK_OAK_BUMPER);
    public static final ItemCarPart BIRCH_BUMPER = new ItemCarPart("birch_bumper", PartRegistry.BIRCH_BUMPER);
    public static final ItemCarPart JUNGLE_BUMPER = new ItemCarPart("jungle_bumper", PartRegistry.JUNGLE_BUMPER);
    public static final ItemCarPart SPRUCE_BUMPER = new ItemCarPart("spruce_bumper", PartRegistry.SPRUCE_BUMPER);

    public static final ItemCarPart[] BUMPERS = new ItemCarPart[]{
            OAK_BUMPER, SPRUCE_BUMPER, BIRCH_BUMPER, JUNGLE_BUMPER, ACACIA_BUMPER, DARK_OAK_BUMPER
    };

    public static final ItemCarPart OAK_LICENSE_PLATE_HOLDER = new ItemCarPart("oak_license_plate_holder", PartRegistry.OAK_LICENSE_PLATE_HOLDER);
    public static final ItemCarPart ACACIA_LICENSE_PLATE_HOLDER = new ItemCarPart("acacia_license_plate_holder", PartRegistry.ACACIA_LICENSE_PLATE_HOLDER);
    public static final ItemCarPart DARK_OAK_LICENSE_PLATE_HOLDER = new ItemCarPart("dark_oak_license_plate_holder", PartRegistry.DARK_OAK_LICENSE_PLATE_HOLDER);
    public static final ItemCarPart BIRCH_LICENSE_PLATE_HOLDER = new ItemCarPart("birch_license_plate_holder", PartRegistry.BIRCH_LICENSE_PLATE_HOLDER);
    public static final ItemCarPart JUNGLE_LICENSE_PLATE_HOLDER = new ItemCarPart("jungle_license_plate_holder", PartRegistry.JUNGLE_LICENSE_PLATE_HOLDER);
    public static final ItemCarPart SPRUCE_LICENSE_PLATE_HOLDER = new ItemCarPart("spruce_license_plate_holder", PartRegistry.SPRUCE_LICENSE_PLATE_HOLDER);

    public static final ItemCarPart[] WOODEN_LICENSE_PLATE_HOLDERS = new ItemCarPart[]{
            OAK_LICENSE_PLATE_HOLDER, SPRUCE_LICENSE_PLATE_HOLDER, BIRCH_LICENSE_PLATE_HOLDER, JUNGLE_LICENSE_PLATE_HOLDER,
            ACACIA_LICENSE_PLATE_HOLDER, DARK_OAK_LICENSE_PLATE_HOLDER
    };

    public static final ItemCarPart IRON_LICENSE_PLATE_HOLDER = new ItemCarPart("iron_license_plate_holder", PartRegistry.IRON_LICENSE_PLATE_HOLDER);
    public static final ItemCarPart DIAMOND_LICENSE_PLATE_HOLDER = new ItemCarPart("diamond_license_plate_holder", PartRegistry.DIAMOND_LICENSE_PLATE_HOLDER);
    public static final ItemCarPart GOLD_LICENSE_PLATE_HOLDER = new ItemCarPart("gold_license_plate_holder", PartRegistry.GOLD_LICENSE_PLATE_HOLDER);
    public static final ItemCarPart EMERALD_LICENSE_PLATE_HOLDER = new ItemCarPart("emerald_license_plate_holder", PartRegistry.EMERALD_LICENSE_PLATE_HOLDER);

    public static final ItemCarPart SMALL_TANK = new ItemCarPart("small_tank", PartRegistry.SMALL_TANK);
    public static final ItemCarPart MEDIUM_TANK = new ItemCarPart("medium_tank", PartRegistry.MEDIUM_TANK);
    public static final ItemCarPart LARGE_TANK = new ItemCarPart("large_tank", PartRegistry.LARGE_TANK);


    public static final CarBucketItem CANOLA_OIL_BUCKET = new CarBucketItem(ModFluids.CANOLA_OIL, new ResourceLocation(Main.MODID, "canola_oil_bucket"));
    public static final CarBucketItem METHANOL_BUCKET = new CarBucketItem(ModFluids.METHANOL, new ResourceLocation(Main.MODID, "methanol_bucket"));
    public static final CarBucketItem CANOLA_METHANOL_MIX_BUCKET = new CarBucketItem(ModFluids.CANOLA_METHANOL_MIX, new ResourceLocation(Main.MODID, "canola_methanol_mix_bucket"));
    public static final CarBucketItem GLYCERIN_BUCKET = new CarBucketItem(ModFluids.GLYCERIN, new ResourceLocation(Main.MODID, "glycerin_bucket"));
    public static final CarBucketItem BIO_DIESEL_BUCKET = new CarBucketItem(ModFluids.BIO_DIESEL, new ResourceLocation(Main.MODID, "bio_diesel_bucket"));

    public static List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        for (Field field : ModItems.class.getFields()) {
            if (ReflectionTools.hasAnnotation(field, NoRegister.class)) {
                continue;
            }
            try {
                Object obj = field.get(null);
                if (obj != null && obj instanceof Item) {
                    items.add((Item) obj);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return items;
    }
}
