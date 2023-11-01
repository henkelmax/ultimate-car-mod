package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.BlockPaint;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.entity.car.parts.PartRegistry;
import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.world.item.Item;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class ModItems {

    private static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    public static final RegistryObject<ItemPainter> PAINTER = ITEM_REGISTER.register("painter", () -> new ItemPainter(false));
    public static final RegistryObject<ItemPainter> PAINTER_YELLOW = ITEM_REGISTER.register("painter_yellow", () -> new ItemPainter(true));
    public static final RegistryObject<ItemCanolaSeed> CANOLA_SEEDS = ITEM_REGISTER.register("canola_seeds", () -> new ItemCanolaSeed());
    public static final RegistryObject<ItemCanola> CANOLA = ITEM_REGISTER.register("canola", () -> new ItemCanola());
    public static final RegistryObject<ItemCanolaCake> CANOLA_CAKE = ITEM_REGISTER.register("canola_cake", () -> new ItemCanolaCake());
    public static final RegistryObject<ItemCraftingComponent> IRON_STICK = ITEM_REGISTER.register("iron_stick", () -> new ItemCraftingComponent());
    public static final RegistryObject<ItemCraftingComponent> ENGINE_PISTON = ITEM_REGISTER.register("engine_piston", () -> new ItemCraftingComponent());
    public static final RegistryObject<ItemCanister> CANISTER = ITEM_REGISTER.register("canister", () -> new ItemCanister());
    public static final RegistryObject<ItemRepairKit> REPAIR_KIT = ITEM_REGISTER.register("repair_kit", () -> new ItemRepairKit());
    public static final RegistryObject<ItemRepairTool> WRENCH = ITEM_REGISTER.register("wrench", () -> new ItemRepairTool());
    public static final RegistryObject<ItemRepairTool> SCREW_DRIVER = ITEM_REGISTER.register("screw_driver", () -> new ItemRepairTool());
    public static final RegistryObject<ItemRepairTool> HAMMER = ITEM_REGISTER.register("hammer", () -> new ItemRepairTool());
    public static final RegistryObject<ItemCraftingComponent> CABLE_INSULATOR = ITEM_REGISTER.register("cable_insulator", () -> new ItemCraftingComponent());
    public static final RegistryObject<ItemKey> KEY = ITEM_REGISTER.register("key", () -> new ItemKey());
    public static final RegistryObject<ItemBattery> BATTERY = ITEM_REGISTER.register("battery", () -> new ItemBattery());
    public static final RegistryObject<ItemGuardRail> GUARD_RAIL = ITEM_REGISTER.register("guard_rail", () -> new ItemGuardRail());

    public static final RegistryObject<ItemLicensePlate> LICENSE_PLATE = ITEM_REGISTER.register("license_plate", () -> new ItemLicensePlate());

    public static final RegistryObject<ItemCarPart> ENGINE_3_CYLINDER = ITEM_REGISTER.register("engine_3_cylinder", () -> new ItemCarPart(PartRegistry.ENGINE_3_CYLINDER));
    public static final RegistryObject<ItemCarPart> ENGINE_6_CYLINDER = ITEM_REGISTER.register("engine_6_cylinder", () -> new ItemCarPart(PartRegistry.ENGINE_6_CYLINDER));
    public static final RegistryObject<ItemCarPart> ENGINE_TRUCK = ITEM_REGISTER.register("engine_truck", () -> new ItemCarPart(PartRegistry.ENGINE_TRUCK));

    public static final RegistryObject<ItemCarPart> WHEEL = ITEM_REGISTER.register("wheel", () -> new ItemCarPart(PartRegistry.WHEEL));
    public static final RegistryObject<ItemCarPart> BIG_WHEEL = ITEM_REGISTER.register("big_wheel", () -> new ItemCarPart(PartRegistry.BIG_WHEEL));

    public static final RegistryObject<ItemCarPart> OAK_BODY = ITEM_REGISTER.register("oak_body", () -> new ItemCarPart(PartRegistry.OAK_BODY));
    public static final RegistryObject<ItemCarPart> ACACIA_BODY = ITEM_REGISTER.register("acacia_body", () -> new ItemCarPart(PartRegistry.ACACIA_BODY));
    public static final RegistryObject<ItemCarPart> DARK_OAK_BODY = ITEM_REGISTER.register("dark_oak_body", () -> new ItemCarPart(PartRegistry.DARK_OAK_BODY));
    public static final RegistryObject<ItemCarPart> BIRCH_BODY = ITEM_REGISTER.register("birch_body", () -> new ItemCarPart(PartRegistry.BIRCH_BODY));
    public static final RegistryObject<ItemCarPart> JUNGLE_BODY = ITEM_REGISTER.register("jungle_body", () -> new ItemCarPart(PartRegistry.JUNGLE_BODY));
    public static final RegistryObject<ItemCarPart> SPRUCE_BODY = ITEM_REGISTER.register("spruce_body", () -> new ItemCarPart(PartRegistry.SPRUCE_BODY));
    public static final RegistryObject<ItemCarPart> CRIMSON_BODY = ITEM_REGISTER.register("crimson_body", () -> new ItemCarPart(PartRegistry.CRIMSON_BODY));
    public static final RegistryObject<ItemCarPart> WARPED_BODY = ITEM_REGISTER.register("warped_body", () -> new ItemCarPart(PartRegistry.WARPED_BODY));

    public static final RegistryObject<ItemCarPart>[] WOOD_BODIES = new RegistryObject[]{
            OAK_BODY, SPRUCE_BODY, BIRCH_BODY, JUNGLE_BODY, ACACIA_BODY, DARK_OAK_BODY, CRIMSON_BODY, WARPED_BODY
    };

    public static final RegistryObject<ItemCarPart> BIG_OAK_BODY = ITEM_REGISTER.register("big_oak_body", () -> new ItemCarPart(PartRegistry.BIG_OAK_BODY));
    public static final RegistryObject<ItemCarPart> BIG_ACACIA_BODY = ITEM_REGISTER.register("big_acacia_body", () -> new ItemCarPart(PartRegistry.BIG_ACACIA_BODY));
    public static final RegistryObject<ItemCarPart> BIG_DARK_OAK_BODY = ITEM_REGISTER.register("big_dark_oak_body", () -> new ItemCarPart(PartRegistry.BIG_DARK_OAK_BODY));
    public static final RegistryObject<ItemCarPart> BIG_BIRCH_BODY = ITEM_REGISTER.register("big_birch_body", () -> new ItemCarPart(PartRegistry.BIG_BIRCH_BODY));
    public static final RegistryObject<ItemCarPart> BIG_JUNGLE_BODY = ITEM_REGISTER.register("big_jungle_body", () -> new ItemCarPart(PartRegistry.BIG_JUNGLE_BODY));
    public static final RegistryObject<ItemCarPart> BIG_SPRUCE_BODY = ITEM_REGISTER.register("big_spruce_body", () -> new ItemCarPart(PartRegistry.BIG_SPRUCE_BODY));
    public static final RegistryObject<ItemCarPart> BIG_CRIMSON_BODY = ITEM_REGISTER.register("big_crimson_body", () -> new ItemCarPart(PartRegistry.BIG_CRIMSON_BODY));
    public static final RegistryObject<ItemCarPart> BIG_WARPED_BODY = ITEM_REGISTER.register("big_warped_body", () -> new ItemCarPart(PartRegistry.BIG_WARPED_BODY));

    public static final RegistryObject<ItemCarPart>[] BIG_WOOD_BODIES = new RegistryObject[]{
            BIG_OAK_BODY, BIG_SPRUCE_BODY, BIG_BIRCH_BODY, BIG_JUNGLE_BODY, BIG_ACACIA_BODY, BIG_DARK_OAK_BODY, BIG_CRIMSON_BODY, BIG_WARPED_BODY
    };

    public static final RegistryObject<ItemCarPart> BLACK_TRANSPORTER_BODY = ITEM_REGISTER.register("black_transporter_body", () -> new ItemCarPart(PartRegistry.BLACK_TRANSPORTER_BODY));
    public static final RegistryObject<ItemCarPart> BLUE_TRANSPORTER_BODY = ITEM_REGISTER.register("blue_transporter_body", () -> new ItemCarPart(PartRegistry.BLUE_TRANSPORTER_BODY));
    public static final RegistryObject<ItemCarPart> BROWN_TRANSPORTER_BODY = ITEM_REGISTER.register("brown_transporter_body", () -> new ItemCarPart(PartRegistry.BROWN_TRANSPORTER_BODY));
    public static final RegistryObject<ItemCarPart> CYAN_TRANSPORTER_BODY = ITEM_REGISTER.register("cyan_transporter_body", () -> new ItemCarPart(PartRegistry.CYAN_TRANSPORTER_BODY));
    public static final RegistryObject<ItemCarPart> GRAY_TRANSPORTER_BODY = ITEM_REGISTER.register("gray_transporter_body", () -> new ItemCarPart(PartRegistry.GRAY_TRANSPORTER_BODY));
    public static final RegistryObject<ItemCarPart> GREEN_TRANSPORTER_BODY = ITEM_REGISTER.register("green_transporter_body", () -> new ItemCarPart(PartRegistry.GREEN_TRANSPORTER_BODY));
    public static final RegistryObject<ItemCarPart> LIGHT_BLUE_TRANSPORTER_BODY = ITEM_REGISTER.register("light_blue_transporter_body", () -> new ItemCarPart(PartRegistry.LIGHT_BLUE_TRANSPORTER_BODY));
    public static final RegistryObject<ItemCarPart> LIME_TRANSPORTER_BODY = ITEM_REGISTER.register("lime_transporter_body", () -> new ItemCarPart(PartRegistry.LIME_TRANSPORTER_BODY));
    public static final RegistryObject<ItemCarPart> MAGENTA_TRANSPORTER_BODY = ITEM_REGISTER.register("magenta_transporter_body", () -> new ItemCarPart(PartRegistry.MAGENTA_TRANSPORTER_BODY));
    public static final RegistryObject<ItemCarPart> ORANGE_TRANSPORTER_BODY = ITEM_REGISTER.register("orange_transporter_body", () -> new ItemCarPart(PartRegistry.ORANGE_TRANSPORTER_BODY));
    public static final RegistryObject<ItemCarPart> PINK_TRANSPORTER_BODY = ITEM_REGISTER.register("pink_transporter_body", () -> new ItemCarPart(PartRegistry.PINK_TRANSPORTER_BODY));
    public static final RegistryObject<ItemCarPart> PURPLE_TRANSPORTER_BODY = ITEM_REGISTER.register("purple_transporter_body", () -> new ItemCarPart(PartRegistry.PURPLE_TRANSPORTER_BODY));
    public static final RegistryObject<ItemCarPart> RED_TRANSPORTER_BODY = ITEM_REGISTER.register("red_transporter_body", () -> new ItemCarPart(PartRegistry.RED_TRANSPORTER_BODY));
    public static final RegistryObject<ItemCarPart> LIGHT_GRAY_TRANSPORTER_BODY = ITEM_REGISTER.register("light_gray_transporter_body", () -> new ItemCarPart(PartRegistry.LIGHT_GRAY_TRANSPORTER_BODY));
    public static final RegistryObject<ItemCarPart> WHITE_TRANSPORTER_BODY = ITEM_REGISTER.register("white_transporter_body", () -> new ItemCarPart(PartRegistry.WHITE_TRANSPORTER_BODY));
    public static final RegistryObject<ItemCarPart> YELLOW_TRANSPORTER_BODY = ITEM_REGISTER.register("yellow_transporter_body", () -> new ItemCarPart(PartRegistry.YELLOW_TRANSPORTER_BODY));

    public static final RegistryObject<ItemCarPart>[] TRANSPORTER_BODIES = new RegistryObject[]{
            WHITE_TRANSPORTER_BODY, ORANGE_TRANSPORTER_BODY, MAGENTA_TRANSPORTER_BODY, LIGHT_BLUE_TRANSPORTER_BODY, YELLOW_TRANSPORTER_BODY,
            LIME_TRANSPORTER_BODY, PINK_TRANSPORTER_BODY, GRAY_TRANSPORTER_BODY, LIGHT_GRAY_TRANSPORTER_BODY, CYAN_TRANSPORTER_BODY,
            PURPLE_TRANSPORTER_BODY, BLUE_TRANSPORTER_BODY, BROWN_TRANSPORTER_BODY, GREEN_TRANSPORTER_BODY, RED_TRANSPORTER_BODY, BLACK_TRANSPORTER_BODY
    };

    public static final RegistryObject<ItemCarPart> BLACK_SUV_BODY = ITEM_REGISTER.register("black_suv_body", () -> new ItemCarPart(PartRegistry.BLACK_SUV_BODY));
    public static final RegistryObject<ItemCarPart> BLUE_SUV_BODY = ITEM_REGISTER.register("blue_suv_body", () -> new ItemCarPart(PartRegistry.BLUE_SUV_BODY));
    public static final RegistryObject<ItemCarPart> BROWN_SUV_BODY = ITEM_REGISTER.register("brown_suv_body", () -> new ItemCarPart(PartRegistry.BROWN_SUV_BODY));
    public static final RegistryObject<ItemCarPart> CYAN_SUV_BODY = ITEM_REGISTER.register("cyan_suv_body", () -> new ItemCarPart(PartRegistry.CYAN_SUV_BODY));
    public static final RegistryObject<ItemCarPart> GRAY_SUV_BODY = ITEM_REGISTER.register("gray_suv_body", () -> new ItemCarPart(PartRegistry.GRAY_SUV_BODY));
    public static final RegistryObject<ItemCarPart> GREEN_SUV_BODY = ITEM_REGISTER.register("green_suv_body", () -> new ItemCarPart(PartRegistry.GREEN_SUV_BODY));
    public static final RegistryObject<ItemCarPart> LIGHT_BLUE_SUV_BODY = ITEM_REGISTER.register("light_blue_suv_body", () -> new ItemCarPart(PartRegistry.LIGHT_BLUE_SUV_BODY));
    public static final RegistryObject<ItemCarPart> LIME_SUV_BODY = ITEM_REGISTER.register("lime_suv_body", () -> new ItemCarPart(PartRegistry.LIME_SUV_BODY));
    public static final RegistryObject<ItemCarPart> MAGENTA_SUV_BODY = ITEM_REGISTER.register("magenta_suv_body", () -> new ItemCarPart(PartRegistry.MAGENTA_SUV_BODY));
    public static final RegistryObject<ItemCarPart> ORANGE_SUV_BODY = ITEM_REGISTER.register("orange_suv_body", () -> new ItemCarPart(PartRegistry.ORANGE_SUV_BODY));
    public static final RegistryObject<ItemCarPart> PINK_SUV_BODY = ITEM_REGISTER.register("pink_suv_body", () -> new ItemCarPart(PartRegistry.PINK_SUV_BODY));
    public static final RegistryObject<ItemCarPart> PURPLE_SUV_BODY = ITEM_REGISTER.register("purple_suv_body", () -> new ItemCarPart(PartRegistry.PURPLE_SUV_BODY));
    public static final RegistryObject<ItemCarPart> RED_SUV_BODY = ITEM_REGISTER.register("red_suv_body", () -> new ItemCarPart(PartRegistry.RED_SUV_BODY));
    public static final RegistryObject<ItemCarPart> LIGHT_GRAY_SUV_BODY = ITEM_REGISTER.register("light_gray_suv_body", () -> new ItemCarPart(PartRegistry.LIGHT_GRAY_SUV_BODY));
    public static final RegistryObject<ItemCarPart> WHITE_SUV_BODY = ITEM_REGISTER.register("white_suv_body", () -> new ItemCarPart(PartRegistry.WHITE_SUV_BODY));
    public static final RegistryObject<ItemCarPart> YELLOW_SUV_BODY = ITEM_REGISTER.register("yellow_suv_body", () -> new ItemCarPart(PartRegistry.YELLOW_SUV_BODY));

    public static final RegistryObject<ItemCarPart>[] SUV_BODIES = new RegistryObject[]{
            WHITE_SUV_BODY, ORANGE_SUV_BODY, MAGENTA_SUV_BODY, LIGHT_BLUE_SUV_BODY, YELLOW_SUV_BODY,
            LIME_SUV_BODY, PINK_SUV_BODY, GRAY_SUV_BODY, LIGHT_GRAY_SUV_BODY, CYAN_SUV_BODY,
            PURPLE_SUV_BODY, BLUE_SUV_BODY, BROWN_SUV_BODY, GREEN_SUV_BODY, RED_SUV_BODY, BLACK_SUV_BODY
    };

    public static final RegistryObject<ItemCarPart> BLACK_SPORT_BODY = ITEM_REGISTER.register("black_sport_body", () -> new ItemCarPart(PartRegistry.BLACK_SPORT_BODY));
    public static final RegistryObject<ItemCarPart> BLUE_SPORT_BODY = ITEM_REGISTER.register("blue_sport_body", () -> new ItemCarPart(PartRegistry.BLUE_SPORT_BODY));
    public static final RegistryObject<ItemCarPart> BROWN_SPORT_BODY = ITEM_REGISTER.register("brown_sport_body", () -> new ItemCarPart(PartRegistry.BROWN_SPORT_BODY));
    public static final RegistryObject<ItemCarPart> CYAN_SPORT_BODY = ITEM_REGISTER.register("cyan_sport_body", () -> new ItemCarPart(PartRegistry.CYAN_SPORT_BODY));
    public static final RegistryObject<ItemCarPart> GRAY_SPORT_BODY = ITEM_REGISTER.register("gray_sport_body", () -> new ItemCarPart(PartRegistry.GRAY_SPORT_BODY));
    public static final RegistryObject<ItemCarPart> GREEN_SPORT_BODY = ITEM_REGISTER.register("green_sport_body", () -> new ItemCarPart(PartRegistry.GREEN_SPORT_BODY));
    public static final RegistryObject<ItemCarPart> LIGHT_BLUE_SPORT_BODY = ITEM_REGISTER.register("light_blue_sport_body", () -> new ItemCarPart(PartRegistry.LIGHT_BLUE_SPORT_BODY));
    public static final RegistryObject<ItemCarPart> LIME_SPORT_BODY = ITEM_REGISTER.register("lime_sport_body", () -> new ItemCarPart(PartRegistry.LIME_SPORT_BODY));
    public static final RegistryObject<ItemCarPart> MAGENTA_SPORT_BODY = ITEM_REGISTER.register("magenta_sport_body", () -> new ItemCarPart(PartRegistry.MAGENTA_SPORT_BODY));
    public static final RegistryObject<ItemCarPart> ORANGE_SPORT_BODY = ITEM_REGISTER.register("orange_sport_body", () -> new ItemCarPart(PartRegistry.ORANGE_SPORT_BODY));
    public static final RegistryObject<ItemCarPart> PINK_SPORT_BODY = ITEM_REGISTER.register("pink_sport_body", () -> new ItemCarPart(PartRegistry.PINK_SPORT_BODY));
    public static final RegistryObject<ItemCarPart> PURPLE_SPORT_BODY = ITEM_REGISTER.register("purple_sport_body", () -> new ItemCarPart(PartRegistry.PURPLE_SPORT_BODY));
    public static final RegistryObject<ItemCarPart> RED_SPORT_BODY = ITEM_REGISTER.register("red_sport_body", () -> new ItemCarPart(PartRegistry.RED_SPORT_BODY));
    public static final RegistryObject<ItemCarPart> LIGHT_GRAY_SPORT_BODY = ITEM_REGISTER.register("light_gray_sport_body", () -> new ItemCarPart(PartRegistry.LIGHT_GRAY_SPORT_BODY));
    public static final RegistryObject<ItemCarPart> WHITE_SPORT_BODY = ITEM_REGISTER.register("white_sport_body", () -> new ItemCarPart(PartRegistry.WHITE_SPORT_BODY));
    public static final RegistryObject<ItemCarPart> YELLOW_SPORT_BODY = ITEM_REGISTER.register("yellow_sport_body", () -> new ItemCarPart(PartRegistry.YELLOW_SPORT_BODY));

    public static final RegistryObject<ItemCarPart>[] SPORT_BODIES = new RegistryObject[]{
            WHITE_SPORT_BODY, ORANGE_SPORT_BODY, MAGENTA_SPORT_BODY, LIGHT_BLUE_SPORT_BODY, YELLOW_SPORT_BODY,
            LIME_SPORT_BODY, PINK_SPORT_BODY, GRAY_SPORT_BODY, LIGHT_GRAY_SPORT_BODY, CYAN_SPORT_BODY,
            PURPLE_SPORT_BODY, BLUE_SPORT_BODY, BROWN_SPORT_BODY, GREEN_SPORT_BODY, RED_SPORT_BODY, BLACK_SPORT_BODY
    };

    public static final RegistryObject<ItemCarPart> BLACK_CONTAINER = ITEM_REGISTER.register("black_container", () -> new ItemCarPart(PartRegistry.BLACK_CONTAINER));
    public static final RegistryObject<ItemCarPart> BLUE_CONTAINER = ITEM_REGISTER.register("blue_container", () -> new ItemCarPart(PartRegistry.BLUE_CONTAINER));
    public static final RegistryObject<ItemCarPart> BROWN_CONTAINER = ITEM_REGISTER.register("brown_container", () -> new ItemCarPart(PartRegistry.BROWN_CONTAINER));
    public static final RegistryObject<ItemCarPart> CYAN_CONTAINER = ITEM_REGISTER.register("cyan_container", () -> new ItemCarPart(PartRegistry.CYAN_CONTAINER));
    public static final RegistryObject<ItemCarPart> GRAY_CONTAINER = ITEM_REGISTER.register("gray_container", () -> new ItemCarPart(PartRegistry.GRAY_CONTAINER));
    public static final RegistryObject<ItemCarPart> GREEN_CONTAINER = ITEM_REGISTER.register("green_container", () -> new ItemCarPart(PartRegistry.GREEN_CONTAINER));
    public static final RegistryObject<ItemCarPart> LIGHT_BLUE_CONTAINER = ITEM_REGISTER.register("light_blue_container", () -> new ItemCarPart(PartRegistry.LIGHT_BLUE_CONTAINER));
    public static final RegistryObject<ItemCarPart> LIME_CONTAINER = ITEM_REGISTER.register("lime_container", () -> new ItemCarPart(PartRegistry.LIME_CONTAINER));
    public static final RegistryObject<ItemCarPart> MAGENTA_CONTAINER = ITEM_REGISTER.register("magenta_container", () -> new ItemCarPart(PartRegistry.MAGENTA_CONTAINER));
    public static final RegistryObject<ItemCarPart> ORANGE_CONTAINER = ITEM_REGISTER.register("orange_container", () -> new ItemCarPart(PartRegistry.ORANGE_CONTAINER));
    public static final RegistryObject<ItemCarPart> PINK_CONTAINER = ITEM_REGISTER.register("pink_container", () -> new ItemCarPart(PartRegistry.PINK_CONTAINER));
    public static final RegistryObject<ItemCarPart> PURPLE_CONTAINER = ITEM_REGISTER.register("purple_container", () -> new ItemCarPart(PartRegistry.PURPLE_CONTAINER));
    public static final RegistryObject<ItemCarPart> RED_CONTAINER = ITEM_REGISTER.register("red_container", () -> new ItemCarPart(PartRegistry.RED_CONTAINER));
    public static final RegistryObject<ItemCarPart> LIGHT_GRAY_CONTAINER = ITEM_REGISTER.register("light_gray_container", () -> new ItemCarPart(PartRegistry.LIGHT_GRAY_CONTAINER));
    public static final RegistryObject<ItemCarPart> WHITE_CONTAINER = ITEM_REGISTER.register("white_container", () -> new ItemCarPart(PartRegistry.WHITE_CONTAINER));
    public static final RegistryObject<ItemCarPart> YELLOW_CONTAINER = ITEM_REGISTER.register("yellow_container", () -> new ItemCarPart(PartRegistry.YELLOW_CONTAINER));

    public static final RegistryObject<ItemCarPart>[] CONTAINERS = new RegistryObject[]{
            WHITE_CONTAINER, ORANGE_CONTAINER, MAGENTA_CONTAINER, LIGHT_BLUE_CONTAINER, YELLOW_CONTAINER,
            LIME_CONTAINER, PINK_CONTAINER, GRAY_CONTAINER, LIGHT_GRAY_CONTAINER, CYAN_CONTAINER,
            PURPLE_CONTAINER, BLUE_CONTAINER, BROWN_CONTAINER, GREEN_CONTAINER, RED_CONTAINER, BLACK_CONTAINER
    };

    public static final RegistryObject<ItemCarPart> BLACK_TANK_CONTAINER = ITEM_REGISTER.register("black_tank_container", () -> new ItemCarPart(PartRegistry.BLACK_TANK_CONTAINER));
    public static final RegistryObject<ItemCarPart> BLUE_TANK_CONTAINER = ITEM_REGISTER.register("blue_tank_container", () -> new ItemCarPart(PartRegistry.BLUE_TANK_CONTAINER));
    public static final RegistryObject<ItemCarPart> BROWN_TANK_CONTAINER = ITEM_REGISTER.register("brown_tank_container", () -> new ItemCarPart(PartRegistry.BROWN_TANK_CONTAINER));
    public static final RegistryObject<ItemCarPart> CYAN_TANK_CONTAINER = ITEM_REGISTER.register("cyan_tank_container", () -> new ItemCarPart(PartRegistry.CYAN_TANK_CONTAINER));
    public static final RegistryObject<ItemCarPart> GRAY_TANK_CONTAINER = ITEM_REGISTER.register("gray_tank_container", () -> new ItemCarPart(PartRegistry.GRAY_TANK_CONTAINER));
    public static final RegistryObject<ItemCarPart> GREEN_TANK_CONTAINER = ITEM_REGISTER.register("green_tank_container", () -> new ItemCarPart(PartRegistry.GREEN_TANK_CONTAINER));
    public static final RegistryObject<ItemCarPart> LIGHT_BLUE_TANK_CONTAINER = ITEM_REGISTER.register("light_blue_tank_container", () -> new ItemCarPart(PartRegistry.LIGHT_BLUE_TANK_CONTAINER));
    public static final RegistryObject<ItemCarPart> LIME_TANK_CONTAINER = ITEM_REGISTER.register("lime_tank_container", () -> new ItemCarPart(PartRegistry.LIME_TANK_CONTAINER));
    public static final RegistryObject<ItemCarPart> MAGENTA_TANK_CONTAINER = ITEM_REGISTER.register("magenta_tank_container", () -> new ItemCarPart(PartRegistry.MAGENTA_TANK_CONTAINER));
    public static final RegistryObject<ItemCarPart> ORANGE_TANK_CONTAINER = ITEM_REGISTER.register("orange_tank_container", () -> new ItemCarPart(PartRegistry.ORANGE_TANK_CONTAINER));
    public static final RegistryObject<ItemCarPart> PINK_TANK_CONTAINER = ITEM_REGISTER.register("pink_tank_container", () -> new ItemCarPart(PartRegistry.PINK_TANK_CONTAINER));
    public static final RegistryObject<ItemCarPart> PURPLE_TANK_CONTAINER = ITEM_REGISTER.register("purple_tank_container", () -> new ItemCarPart(PartRegistry.PURPLE_TANK_CONTAINER));
    public static final RegistryObject<ItemCarPart> RED_TANK_CONTAINER = ITEM_REGISTER.register("red_tank_container", () -> new ItemCarPart(PartRegistry.RED_TANK_CONTAINER));
    public static final RegistryObject<ItemCarPart> LIGHT_GRAY_TANK_CONTAINER = ITEM_REGISTER.register("light_gray_tank_container", () -> new ItemCarPart(PartRegistry.LIGHT_GRAY_TANK_CONTAINER));
    public static final RegistryObject<ItemCarPart> WHITE_TANK_CONTAINER = ITEM_REGISTER.register("white_tank_container", () -> new ItemCarPart(PartRegistry.WHITE_TANK_CONTAINER));
    public static final RegistryObject<ItemCarPart> YELLOW_TANK_CONTAINER = ITEM_REGISTER.register("yellow_tank_container", () -> new ItemCarPart(PartRegistry.YELLOW_TANK_CONTAINER));

    public static final RegistryObject<ItemCarPart>[] TANK_CONTAINERS = new RegistryObject[]{
            WHITE_TANK_CONTAINER, ORANGE_TANK_CONTAINER, MAGENTA_TANK_CONTAINER, LIGHT_BLUE_TANK_CONTAINER, YELLOW_TANK_CONTAINER,
            LIME_TANK_CONTAINER, PINK_TANK_CONTAINER, GRAY_TANK_CONTAINER, LIGHT_GRAY_TANK_CONTAINER, CYAN_TANK_CONTAINER,
            PURPLE_TANK_CONTAINER, BLUE_TANK_CONTAINER, BROWN_TANK_CONTAINER, GREEN_TANK_CONTAINER, RED_TANK_CONTAINER, BLACK_TANK_CONTAINER
    };

    public static final RegistryObject<ItemCarPart> OAK_BUMPER = ITEM_REGISTER.register("oak_bumper", () -> new ItemCarPart(PartRegistry.OAK_BUMPER));
    public static final RegistryObject<ItemCarPart> ACACIA_BUMPER = ITEM_REGISTER.register("acacia_bumper", () -> new ItemCarPart(PartRegistry.ACACIA_BUMPER));
    public static final RegistryObject<ItemCarPart> DARK_OAK_BUMPER = ITEM_REGISTER.register("dark_oak_bumper", () -> new ItemCarPart(PartRegistry.DARK_OAK_BUMPER));
    public static final RegistryObject<ItemCarPart> BIRCH_BUMPER = ITEM_REGISTER.register("birch_bumper", () -> new ItemCarPart(PartRegistry.BIRCH_BUMPER));
    public static final RegistryObject<ItemCarPart> JUNGLE_BUMPER = ITEM_REGISTER.register("jungle_bumper", () -> new ItemCarPart(PartRegistry.JUNGLE_BUMPER));
    public static final RegistryObject<ItemCarPart> SPRUCE_BUMPER = ITEM_REGISTER.register("spruce_bumper", () -> new ItemCarPart(PartRegistry.SPRUCE_BUMPER));
    public static final RegistryObject<ItemCarPart> CRIMSON_BUMPER = ITEM_REGISTER.register("crimson_bumper", () -> new ItemCarPart(PartRegistry.CRIMSON_BUMPER));
    public static final RegistryObject<ItemCarPart> WARPED_BUMPER = ITEM_REGISTER.register("warped_bumper", () -> new ItemCarPart(PartRegistry.WARPED_BUMPER));

    public static final RegistryObject<ItemCarPart>[] BUMPERS = new RegistryObject[]{
            OAK_BUMPER, SPRUCE_BUMPER, BIRCH_BUMPER, JUNGLE_BUMPER, ACACIA_BUMPER, DARK_OAK_BUMPER, CRIMSON_BUMPER, WARPED_BUMPER
    };

    public static final RegistryObject<ItemCarPart> OAK_LICENSE_PLATE_HOLDER = ITEM_REGISTER.register("oak_license_plate_holder", () -> new ItemCarPart(PartRegistry.OAK_LICENSE_PLATE_HOLDER));
    public static final RegistryObject<ItemCarPart> ACACIA_LICENSE_PLATE_HOLDER = ITEM_REGISTER.register("acacia_license_plate_holder", () -> new ItemCarPart(PartRegistry.ACACIA_LICENSE_PLATE_HOLDER));
    public static final RegistryObject<ItemCarPart> DARK_OAK_LICENSE_PLATE_HOLDER = ITEM_REGISTER.register("dark_oak_license_plate_holder", () -> new ItemCarPart(PartRegistry.DARK_OAK_LICENSE_PLATE_HOLDER));
    public static final RegistryObject<ItemCarPart> BIRCH_LICENSE_PLATE_HOLDER = ITEM_REGISTER.register("birch_license_plate_holder", () -> new ItemCarPart(PartRegistry.BIRCH_LICENSE_PLATE_HOLDER));
    public static final RegistryObject<ItemCarPart> JUNGLE_LICENSE_PLATE_HOLDER = ITEM_REGISTER.register("jungle_license_plate_holder", () -> new ItemCarPart(PartRegistry.JUNGLE_LICENSE_PLATE_HOLDER));
    public static final RegistryObject<ItemCarPart> SPRUCE_LICENSE_PLATE_HOLDER = ITEM_REGISTER.register("spruce_license_plate_holder", () -> new ItemCarPart(PartRegistry.SPRUCE_LICENSE_PLATE_HOLDER));
    public static final RegistryObject<ItemCarPart> CRIMSON_LICENSE_PLATE_HOLDER = ITEM_REGISTER.register("crimson_license_plate_holder", () -> new ItemCarPart(PartRegistry.CRIMSON_LICENSE_PLATE_HOLDER));
    public static final RegistryObject<ItemCarPart> WARPED_LICENSE_PLATE_HOLDER = ITEM_REGISTER.register("warped_license_plate_holder", () -> new ItemCarPart(PartRegistry.WARPED_LICENSE_PLATE_HOLDER));

    public static final RegistryObject<ItemCarPart>[] WOODEN_LICENSE_PLATE_HOLDERS = new RegistryObject[]{
            OAK_LICENSE_PLATE_HOLDER, SPRUCE_LICENSE_PLATE_HOLDER, BIRCH_LICENSE_PLATE_HOLDER, JUNGLE_LICENSE_PLATE_HOLDER,
            ACACIA_LICENSE_PLATE_HOLDER, DARK_OAK_LICENSE_PLATE_HOLDER, CRIMSON_LICENSE_PLATE_HOLDER, WARPED_LICENSE_PLATE_HOLDER
    };

    public static final RegistryObject<ItemCarPart> IRON_LICENSE_PLATE_HOLDER = ITEM_REGISTER.register("iron_license_plate_holder", () -> new ItemCarPart(PartRegistry.IRON_LICENSE_PLATE_HOLDER));
    public static final RegistryObject<ItemCarPart> DIAMOND_LICENSE_PLATE_HOLDER = ITEM_REGISTER.register("diamond_license_plate_holder", () -> new ItemCarPart(PartRegistry.DIAMOND_LICENSE_PLATE_HOLDER));
    public static final RegistryObject<ItemCarPart> GOLD_LICENSE_PLATE_HOLDER = ITEM_REGISTER.register("gold_license_plate_holder", () -> new ItemCarPart(PartRegistry.GOLD_LICENSE_PLATE_HOLDER));
    public static final RegistryObject<ItemCarPart> EMERALD_LICENSE_PLATE_HOLDER = ITEM_REGISTER.register("emerald_license_plate_holder", () -> new ItemCarPart(PartRegistry.EMERALD_LICENSE_PLATE_HOLDER));

    public static final RegistryObject<ItemCarPart> SMALL_TANK = ITEM_REGISTER.register("small_tank", () -> new ItemCarPart(PartRegistry.SMALL_TANK));
    public static final RegistryObject<ItemCarPart> MEDIUM_TANK = ITEM_REGISTER.register("medium_tank", () -> new ItemCarPart(PartRegistry.MEDIUM_TANK));
    public static final RegistryObject<ItemCarPart> LARGE_TANK = ITEM_REGISTER.register("large_tank", () -> new ItemCarPart(PartRegistry.LARGE_TANK));

    public static final RegistryObject<CarBucketItem> CANOLA_OIL_BUCKET = ITEM_REGISTER.register("canola_oil_bucket", () -> new CarBucketItem(ModFluids.CANOLA_OIL.get()));
    public static final RegistryObject<CarBucketItem> METHANOL_BUCKET = ITEM_REGISTER.register("methanol_bucket", () -> new CarBucketItem(ModFluids.METHANOL.get()));
    public static final RegistryObject<CarBucketItem> CANOLA_METHANOL_MIX_BUCKET = ITEM_REGISTER.register("canola_methanol_mix_bucket", () -> new CarBucketItem(ModFluids.CANOLA_METHANOL_MIX.get()));
    public static final RegistryObject<CarBucketItem> GLYCERIN_BUCKET = ITEM_REGISTER.register("glycerin_bucket", () -> new CarBucketItem(ModFluids.GLYCERIN.get()));
    public static final RegistryObject<CarBucketItem> BIO_DIESEL_BUCKET = ITEM_REGISTER.register("bio_diesel_bucket", () -> new CarBucketItem(ModFluids.BIO_DIESEL.get()));

    public static final RegistryObject<Item> ASPHALT = ITEM_REGISTER.register("asphalt", () -> ModBlocks.ASPHALT.get().toItem());
    public static final RegistryObject<Item> ASPHALT_SLOPE = ITEM_REGISTER.register("asphalt_slope", () -> ModBlocks.ASPHALT_SLOPE.get().toItem());
    public static final RegistryObject<Item> ASPHALT_SLOPE_FLAT_UPPER = ITEM_REGISTER.register("asphalt_slope_flat_upper", () -> ModBlocks.ASPHALT_SLOPE_FLAT_UPPER.get().toItem());
    public static final RegistryObject<Item> ASPHALT_SLOPE_FLAT_LOWER = ITEM_REGISTER.register("asphalt_slope_flat_lower", () -> ModBlocks.ASPHALT_SLOPE_FLAT_LOWER.get().toItem());
    public static final RegistryObject<Item> ASPHALT_SLAB = ITEM_REGISTER.register("asphalt_slab", () -> ModBlocks.ASPHALT_SLAB.get().toItem());
    public static final RegistryObject<Item> GAS_STATION = ITEM_REGISTER.register("gas_station", () -> ModBlocks.GAS_STATION.get().toItem());
    public static final RegistryObject<Item> OIL_MILL = ITEM_REGISTER.register("oilmill", () -> ModBlocks.OIL_MILL.get().toItem());
    public static final RegistryObject<Item> BLAST_FURNACE = ITEM_REGISTER.register("blastfurnace", () -> ModBlocks.BLAST_FURNACE.get().toItem());
    public static final RegistryObject<Item> BACKMIX_REACTOR = ITEM_REGISTER.register("backmix_reactor", () -> ModBlocks.BACKMIX_REACTOR.get().toItem());
    public static final RegistryObject<Item> GENERATOR = ITEM_REGISTER.register("generator", () -> ModBlocks.GENERATOR.get().toItem());
    public static final RegistryObject<Item> SPLIT_TANK = ITEM_REGISTER.register("split_tank", () -> ModBlocks.SPLIT_TANK.get().toItem());
    public static final RegistryObject<Item> TANK = ITEM_REGISTER.register("tank", () -> ModBlocks.TANK.get().toItem());
    public static final RegistryObject<Item> CAR_WORKSHOP = ITEM_REGISTER.register("car_workshop", () -> ModBlocks.CAR_WORKSHOP.get().toItem());
    public static final RegistryObject<Item> CAR_WORKSHOP_OUTTER = ITEM_REGISTER.register("car_workshop_outter", () -> ModBlocks.CAR_WORKSHOP_OUTTER.get().toItem());
    public static final RegistryObject<Item> CABLE = ITEM_REGISTER.register("cable", () -> ModBlocks.CABLE.get().toItem());
    public static final RegistryObject<Item> FLUID_EXTRACTOR = ITEM_REGISTER.register("fluid_extractor", () -> ModBlocks.FLUID_EXTRACTOR.get().toItem());
    public static final RegistryObject<Item> FLUID_PIPE = ITEM_REGISTER.register("fluid_pipe", () -> ModBlocks.FLUID_PIPE.get().toItem());
    public static final RegistryObject<Item> DYNAMO = ITEM_REGISTER.register("dynamo", () -> ModBlocks.DYNAMO.get().toItem());
    public static final RegistryObject<Item> CRANK = ITEM_REGISTER.register("crank", () -> ModBlocks.CRANK.get().toItem());
    public static final RegistryObject<Item> SIGN = ITEM_REGISTER.register("sign", () -> ModBlocks.SIGN.get().toItem());
    public static final RegistryObject<Item> SIGN_POST = ITEM_REGISTER.register("sign_post", () -> ModBlocks.SIGN_POST.get().toItem());
    public static final RegistryObject<Item> CAR_PRESSURE_PLATE = ITEM_REGISTER.register("car_pressure_plate", () -> ModBlocks.CAR_PRESSURE_PLATE.get().toItem());

    public static final RegistryObject<Item>[] PAINTS;
    public static final RegistryObject<Item>[] YELLOW_PAINTS;

    static {
        PAINTS = new RegistryObject[BlockPaint.EnumPaintType.values().length];
        for (int i = 0; i < PAINTS.length; i++) {
            int paintIndex = i;
            PAINTS[i] = ITEM_REGISTER.register(BlockPaint.EnumPaintType.values()[i].getPaintName(), () -> ModBlocks.PAINTS[paintIndex].get().toItem());
        }

        YELLOW_PAINTS = new RegistryObject[BlockPaint.EnumPaintType.values().length];
        for (int i = 0; i < YELLOW_PAINTS.length; i++) {
            int paintIndex = i;
            YELLOW_PAINTS[i] = ITEM_REGISTER.register(BlockPaint.EnumPaintType.values()[i].getPaintName() + "_yellow", () -> ModBlocks.PAINTS[paintIndex].get().toItem());
        }
    }

    public static void init() {
        ITEM_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
