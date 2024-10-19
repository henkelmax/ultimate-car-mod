package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.BlockGasStation;
import de.maxhenkel.car.blocks.BlockPaint;
import de.maxhenkel.car.blocks.BlockSplitTank;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.entity.car.parts.PartRegistry;
import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    private static final DeferredRegister.Items ITEM_REGISTER = DeferredRegister.createItems(Main.MODID);

    public static final DeferredHolder<Item, ItemPainter> PAINTER = ITEM_REGISTER.registerItem("painter", p -> new ItemPainter(p, false));
    public static final DeferredHolder<Item, ItemPainter> PAINTER_YELLOW = ITEM_REGISTER.registerItem("painter_yellow", p -> new ItemPainter(p, true));
    public static final DeferredHolder<Item, ItemCanolaSeed> CANOLA_SEEDS = ITEM_REGISTER.registerItem("canola_seeds", ItemCanolaSeed::new);
    public static final DeferredHolder<Item, ItemCanola> CANOLA = ITEM_REGISTER.registerItem("canola", ItemCanola::new);
    public static final DeferredHolder<Item, ItemCanolaCake> CANOLA_CAKE = ITEM_REGISTER.registerItem("canola_cake", ItemCanolaCake::new);
    public static final DeferredHolder<Item, ItemCraftingComponent> IRON_STICK = ITEM_REGISTER.registerItem("iron_stick", ItemCraftingComponent::new);
    public static final DeferredHolder<Item, ItemCraftingComponent> ENGINE_PISTON = ITEM_REGISTER.registerItem("engine_piston", ItemCraftingComponent::new);
    public static final DeferredHolder<Item, ItemCanister> CANISTER = ITEM_REGISTER.registerItem("canister", ItemCanister::new);
    public static final DeferredHolder<Item, ItemRepairKit> REPAIR_KIT = ITEM_REGISTER.registerItem("repair_kit", ItemRepairKit::new);
    public static final DeferredHolder<Item, ItemRepairTool> WRENCH = ITEM_REGISTER.registerItem("wrench", ItemRepairTool::new);
    public static final DeferredHolder<Item, ItemRepairTool> SCREW_DRIVER = ITEM_REGISTER.registerItem("screw_driver", ItemRepairTool::new);
    public static final DeferredHolder<Item, ItemRepairTool> HAMMER = ITEM_REGISTER.registerItem("hammer", ItemRepairTool::new);
    public static final DeferredHolder<Item, ItemCraftingComponent> CABLE_INSULATOR = ITEM_REGISTER.registerItem("cable_insulator", ItemCraftingComponent::new);
    public static final DeferredHolder<Item, ItemKey> KEY = ITEM_REGISTER.registerItem("key", ItemKey::new);
    public static final DeferredHolder<Item, ItemBattery> BATTERY = ITEM_REGISTER.registerItem("battery", ItemBattery::new);
    public static final DeferredHolder<Item, ItemGuardRail> GUARD_RAIL = ITEM_REGISTER.registerItem("guard_rail", ItemGuardRail::new);

    public static final DeferredHolder<Item, ItemLicensePlate> LICENSE_PLATE = ITEM_REGISTER.registerItem("license_plate", ItemLicensePlate::new);

    public static final DeferredHolder<Item, ItemCarPart> ENGINE_3_CYLINDER = ITEM_REGISTER.registerItem("engine_3_cylinder", p -> new ItemCarPart(p, PartRegistry.ENGINE_3_CYLINDER));
    public static final DeferredHolder<Item, ItemCarPart> ENGINE_6_CYLINDER = ITEM_REGISTER.registerItem("engine_6_cylinder", p -> new ItemCarPart(p, PartRegistry.ENGINE_6_CYLINDER));
    public static final DeferredHolder<Item, ItemCarPart> ENGINE_TRUCK = ITEM_REGISTER.registerItem("engine_truck", p -> new ItemCarPart(p, PartRegistry.ENGINE_TRUCK));

    public static final DeferredHolder<Item, ItemCarPart> WHEEL = ITEM_REGISTER.registerItem("wheel", p -> new ItemCarPart(p, PartRegistry.WHEEL));
    public static final DeferredHolder<Item, ItemCarPart> BIG_WHEEL = ITEM_REGISTER.registerItem("big_wheel", p -> new ItemCarPart(p, PartRegistry.BIG_WHEEL));

    public static final DeferredHolder<Item, ItemCarPart> OAK_BODY = ITEM_REGISTER.registerItem("oak_body", p -> new ItemCarPart(p, PartRegistry.OAK_BODY));
    public static final DeferredHolder<Item, ItemCarPart> ACACIA_BODY = ITEM_REGISTER.registerItem("acacia_body", p -> new ItemCarPart(p, PartRegistry.ACACIA_BODY));
    public static final DeferredHolder<Item, ItemCarPart> DARK_OAK_BODY = ITEM_REGISTER.registerItem("dark_oak_body", p -> new ItemCarPart(p, PartRegistry.DARK_OAK_BODY));
    public static final DeferredHolder<Item, ItemCarPart> BIRCH_BODY = ITEM_REGISTER.registerItem("birch_body", p -> new ItemCarPart(p, PartRegistry.BIRCH_BODY));
    public static final DeferredHolder<Item, ItemCarPart> JUNGLE_BODY = ITEM_REGISTER.registerItem("jungle_body", p -> new ItemCarPart(p, PartRegistry.JUNGLE_BODY));
    public static final DeferredHolder<Item, ItemCarPart> SPRUCE_BODY = ITEM_REGISTER.registerItem("spruce_body", p -> new ItemCarPart(p, PartRegistry.SPRUCE_BODY));
    public static final DeferredHolder<Item, ItemCarPart> CRIMSON_BODY = ITEM_REGISTER.registerItem("crimson_body", p -> new ItemCarPart(p, PartRegistry.CRIMSON_BODY));
    public static final DeferredHolder<Item, ItemCarPart> WARPED_BODY = ITEM_REGISTER.registerItem("warped_body", p -> new ItemCarPart(p, PartRegistry.WARPED_BODY));

    public static final DeferredHolder<Item, ItemCarPart>[] WOOD_BODIES = new DeferredHolder[]{
            OAK_BODY, SPRUCE_BODY, BIRCH_BODY, JUNGLE_BODY, ACACIA_BODY, DARK_OAK_BODY, CRIMSON_BODY, WARPED_BODY
    };

    public static final DeferredHolder<Item, ItemCarPart> BIG_OAK_BODY = ITEM_REGISTER.registerItem("big_oak_body", p -> new ItemCarPart(p, PartRegistry.BIG_OAK_BODY));
    public static final DeferredHolder<Item, ItemCarPart> BIG_ACACIA_BODY = ITEM_REGISTER.registerItem("big_acacia_body", p -> new ItemCarPart(p, PartRegistry.BIG_ACACIA_BODY));
    public static final DeferredHolder<Item, ItemCarPart> BIG_DARK_OAK_BODY = ITEM_REGISTER.registerItem("big_dark_oak_body", p -> new ItemCarPart(p, PartRegistry.BIG_DARK_OAK_BODY));
    public static final DeferredHolder<Item, ItemCarPart> BIG_BIRCH_BODY = ITEM_REGISTER.registerItem("big_birch_body", p -> new ItemCarPart(p, PartRegistry.BIG_BIRCH_BODY));
    public static final DeferredHolder<Item, ItemCarPart> BIG_JUNGLE_BODY = ITEM_REGISTER.registerItem("big_jungle_body", p -> new ItemCarPart(p, PartRegistry.BIG_JUNGLE_BODY));
    public static final DeferredHolder<Item, ItemCarPart> BIG_SPRUCE_BODY = ITEM_REGISTER.registerItem("big_spruce_body", p -> new ItemCarPart(p, PartRegistry.BIG_SPRUCE_BODY));
    public static final DeferredHolder<Item, ItemCarPart> BIG_CRIMSON_BODY = ITEM_REGISTER.registerItem("big_crimson_body", p -> new ItemCarPart(p, PartRegistry.BIG_CRIMSON_BODY));
    public static final DeferredHolder<Item, ItemCarPart> BIG_WARPED_BODY = ITEM_REGISTER.registerItem("big_warped_body", p -> new ItemCarPart(p, PartRegistry.BIG_WARPED_BODY));

    public static final DeferredHolder<Item, ItemCarPart>[] BIG_WOOD_BODIES = new DeferredHolder[]{
            BIG_OAK_BODY, BIG_SPRUCE_BODY, BIG_BIRCH_BODY, BIG_JUNGLE_BODY, BIG_ACACIA_BODY, BIG_DARK_OAK_BODY, BIG_CRIMSON_BODY, BIG_WARPED_BODY
    };

    public static final DeferredHolder<Item, ItemCarPart> BLACK_TRANSPORTER_BODY = ITEM_REGISTER.registerItem("black_transporter_body", p -> new ItemCarPart(p, PartRegistry.BLACK_TRANSPORTER_BODY));
    public static final DeferredHolder<Item, ItemCarPart> BLUE_TRANSPORTER_BODY = ITEM_REGISTER.registerItem("blue_transporter_body", p -> new ItemCarPart(p, PartRegistry.BLUE_TRANSPORTER_BODY));
    public static final DeferredHolder<Item, ItemCarPart> BROWN_TRANSPORTER_BODY = ITEM_REGISTER.registerItem("brown_transporter_body", p -> new ItemCarPart(p, PartRegistry.BROWN_TRANSPORTER_BODY));
    public static final DeferredHolder<Item, ItemCarPart> CYAN_TRANSPORTER_BODY = ITEM_REGISTER.registerItem("cyan_transporter_body", p -> new ItemCarPart(p, PartRegistry.CYAN_TRANSPORTER_BODY));
    public static final DeferredHolder<Item, ItemCarPart> GRAY_TRANSPORTER_BODY = ITEM_REGISTER.registerItem("gray_transporter_body", p -> new ItemCarPart(p, PartRegistry.GRAY_TRANSPORTER_BODY));
    public static final DeferredHolder<Item, ItemCarPart> GREEN_TRANSPORTER_BODY = ITEM_REGISTER.registerItem("green_transporter_body", p -> new ItemCarPart(p, PartRegistry.GREEN_TRANSPORTER_BODY));
    public static final DeferredHolder<Item, ItemCarPart> LIGHT_BLUE_TRANSPORTER_BODY = ITEM_REGISTER.registerItem("light_blue_transporter_body", p -> new ItemCarPart(p, PartRegistry.LIGHT_BLUE_TRANSPORTER_BODY));
    public static final DeferredHolder<Item, ItemCarPart> LIME_TRANSPORTER_BODY = ITEM_REGISTER.registerItem("lime_transporter_body", p -> new ItemCarPart(p, PartRegistry.LIME_TRANSPORTER_BODY));
    public static final DeferredHolder<Item, ItemCarPart> MAGENTA_TRANSPORTER_BODY = ITEM_REGISTER.registerItem("magenta_transporter_body", p -> new ItemCarPart(p, PartRegistry.MAGENTA_TRANSPORTER_BODY));
    public static final DeferredHolder<Item, ItemCarPart> ORANGE_TRANSPORTER_BODY = ITEM_REGISTER.registerItem("orange_transporter_body", p -> new ItemCarPart(p, PartRegistry.ORANGE_TRANSPORTER_BODY));
    public static final DeferredHolder<Item, ItemCarPart> PINK_TRANSPORTER_BODY = ITEM_REGISTER.registerItem("pink_transporter_body", p -> new ItemCarPart(p, PartRegistry.PINK_TRANSPORTER_BODY));
    public static final DeferredHolder<Item, ItemCarPart> PURPLE_TRANSPORTER_BODY = ITEM_REGISTER.registerItem("purple_transporter_body", p -> new ItemCarPart(p, PartRegistry.PURPLE_TRANSPORTER_BODY));
    public static final DeferredHolder<Item, ItemCarPart> RED_TRANSPORTER_BODY = ITEM_REGISTER.registerItem("red_transporter_body", p -> new ItemCarPart(p, PartRegistry.RED_TRANSPORTER_BODY));
    public static final DeferredHolder<Item, ItemCarPart> LIGHT_GRAY_TRANSPORTER_BODY = ITEM_REGISTER.registerItem("light_gray_transporter_body", p -> new ItemCarPart(p, PartRegistry.LIGHT_GRAY_TRANSPORTER_BODY));
    public static final DeferredHolder<Item, ItemCarPart> WHITE_TRANSPORTER_BODY = ITEM_REGISTER.registerItem("white_transporter_body", p -> new ItemCarPart(p, PartRegistry.WHITE_TRANSPORTER_BODY));
    public static final DeferredHolder<Item, ItemCarPart> YELLOW_TRANSPORTER_BODY = ITEM_REGISTER.registerItem("yellow_transporter_body", p -> new ItemCarPart(p, PartRegistry.YELLOW_TRANSPORTER_BODY));

    public static final DeferredHolder<Item, ItemCarPart>[] TRANSPORTER_BODIES = new DeferredHolder[]{
            WHITE_TRANSPORTER_BODY, ORANGE_TRANSPORTER_BODY, MAGENTA_TRANSPORTER_BODY, LIGHT_BLUE_TRANSPORTER_BODY, YELLOW_TRANSPORTER_BODY,
            LIME_TRANSPORTER_BODY, PINK_TRANSPORTER_BODY, GRAY_TRANSPORTER_BODY, LIGHT_GRAY_TRANSPORTER_BODY, CYAN_TRANSPORTER_BODY,
            PURPLE_TRANSPORTER_BODY, BLUE_TRANSPORTER_BODY, BROWN_TRANSPORTER_BODY, GREEN_TRANSPORTER_BODY, RED_TRANSPORTER_BODY, BLACK_TRANSPORTER_BODY
    };

    public static final DeferredHolder<Item, ItemCarPart> BLACK_SUV_BODY = ITEM_REGISTER.registerItem("black_suv_body", p -> new ItemCarPart(p, PartRegistry.BLACK_SUV_BODY));
    public static final DeferredHolder<Item, ItemCarPart> BLUE_SUV_BODY = ITEM_REGISTER.registerItem("blue_suv_body", p -> new ItemCarPart(p, PartRegistry.BLUE_SUV_BODY));
    public static final DeferredHolder<Item, ItemCarPart> BROWN_SUV_BODY = ITEM_REGISTER.registerItem("brown_suv_body", p -> new ItemCarPart(p, PartRegistry.BROWN_SUV_BODY));
    public static final DeferredHolder<Item, ItemCarPart> CYAN_SUV_BODY = ITEM_REGISTER.registerItem("cyan_suv_body", p -> new ItemCarPart(p, PartRegistry.CYAN_SUV_BODY));
    public static final DeferredHolder<Item, ItemCarPart> GRAY_SUV_BODY = ITEM_REGISTER.registerItem("gray_suv_body", p -> new ItemCarPart(p, PartRegistry.GRAY_SUV_BODY));
    public static final DeferredHolder<Item, ItemCarPart> GREEN_SUV_BODY = ITEM_REGISTER.registerItem("green_suv_body", p -> new ItemCarPart(p, PartRegistry.GREEN_SUV_BODY));
    public static final DeferredHolder<Item, ItemCarPart> LIGHT_BLUE_SUV_BODY = ITEM_REGISTER.registerItem("light_blue_suv_body", p -> new ItemCarPart(p, PartRegistry.LIGHT_BLUE_SUV_BODY));
    public static final DeferredHolder<Item, ItemCarPart> LIME_SUV_BODY = ITEM_REGISTER.registerItem("lime_suv_body", p -> new ItemCarPart(p, PartRegistry.LIME_SUV_BODY));
    public static final DeferredHolder<Item, ItemCarPart> MAGENTA_SUV_BODY = ITEM_REGISTER.registerItem("magenta_suv_body", p -> new ItemCarPart(p, PartRegistry.MAGENTA_SUV_BODY));
    public static final DeferredHolder<Item, ItemCarPart> ORANGE_SUV_BODY = ITEM_REGISTER.registerItem("orange_suv_body", p -> new ItemCarPart(p, PartRegistry.ORANGE_SUV_BODY));
    public static final DeferredHolder<Item, ItemCarPart> PINK_SUV_BODY = ITEM_REGISTER.registerItem("pink_suv_body", p -> new ItemCarPart(p, PartRegistry.PINK_SUV_BODY));
    public static final DeferredHolder<Item, ItemCarPart> PURPLE_SUV_BODY = ITEM_REGISTER.registerItem("purple_suv_body", p -> new ItemCarPart(p, PartRegistry.PURPLE_SUV_BODY));
    public static final DeferredHolder<Item, ItemCarPart> RED_SUV_BODY = ITEM_REGISTER.registerItem("red_suv_body", p -> new ItemCarPart(p, PartRegistry.RED_SUV_BODY));
    public static final DeferredHolder<Item, ItemCarPart> LIGHT_GRAY_SUV_BODY = ITEM_REGISTER.registerItem("light_gray_suv_body", p -> new ItemCarPart(p, PartRegistry.LIGHT_GRAY_SUV_BODY));
    public static final DeferredHolder<Item, ItemCarPart> WHITE_SUV_BODY = ITEM_REGISTER.registerItem("white_suv_body", p -> new ItemCarPart(p, PartRegistry.WHITE_SUV_BODY));
    public static final DeferredHolder<Item, ItemCarPart> YELLOW_SUV_BODY = ITEM_REGISTER.registerItem("yellow_suv_body", p -> new ItemCarPart(p, PartRegistry.YELLOW_SUV_BODY));

    public static final DeferredHolder<Item, ItemCarPart>[] SUV_BODIES = new DeferredHolder[]{
            WHITE_SUV_BODY, ORANGE_SUV_BODY, MAGENTA_SUV_BODY, LIGHT_BLUE_SUV_BODY, YELLOW_SUV_BODY,
            LIME_SUV_BODY, PINK_SUV_BODY, GRAY_SUV_BODY, LIGHT_GRAY_SUV_BODY, CYAN_SUV_BODY,
            PURPLE_SUV_BODY, BLUE_SUV_BODY, BROWN_SUV_BODY, GREEN_SUV_BODY, RED_SUV_BODY, BLACK_SUV_BODY
    };

    public static final DeferredHolder<Item, ItemCarPart> BLACK_SPORT_BODY = ITEM_REGISTER.registerItem("black_sport_body", p -> new ItemCarPart(p, PartRegistry.BLACK_SPORT_BODY));
    public static final DeferredHolder<Item, ItemCarPart> BLUE_SPORT_BODY = ITEM_REGISTER.registerItem("blue_sport_body", p -> new ItemCarPart(p, PartRegistry.BLUE_SPORT_BODY));
    public static final DeferredHolder<Item, ItemCarPart> BROWN_SPORT_BODY = ITEM_REGISTER.registerItem("brown_sport_body", p -> new ItemCarPart(p, PartRegistry.BROWN_SPORT_BODY));
    public static final DeferredHolder<Item, ItemCarPart> CYAN_SPORT_BODY = ITEM_REGISTER.registerItem("cyan_sport_body", p -> new ItemCarPart(p, PartRegistry.CYAN_SPORT_BODY));
    public static final DeferredHolder<Item, ItemCarPart> GRAY_SPORT_BODY = ITEM_REGISTER.registerItem("gray_sport_body", p -> new ItemCarPart(p, PartRegistry.GRAY_SPORT_BODY));
    public static final DeferredHolder<Item, ItemCarPart> GREEN_SPORT_BODY = ITEM_REGISTER.registerItem("green_sport_body", p -> new ItemCarPart(p, PartRegistry.GREEN_SPORT_BODY));
    public static final DeferredHolder<Item, ItemCarPart> LIGHT_BLUE_SPORT_BODY = ITEM_REGISTER.registerItem("light_blue_sport_body", p -> new ItemCarPart(p, PartRegistry.LIGHT_BLUE_SPORT_BODY));
    public static final DeferredHolder<Item, ItemCarPart> LIME_SPORT_BODY = ITEM_REGISTER.registerItem("lime_sport_body", p -> new ItemCarPart(p, PartRegistry.LIME_SPORT_BODY));
    public static final DeferredHolder<Item, ItemCarPart> MAGENTA_SPORT_BODY = ITEM_REGISTER.registerItem("magenta_sport_body", p -> new ItemCarPart(p, PartRegistry.MAGENTA_SPORT_BODY));
    public static final DeferredHolder<Item, ItemCarPart> ORANGE_SPORT_BODY = ITEM_REGISTER.registerItem("orange_sport_body", p -> new ItemCarPart(p, PartRegistry.ORANGE_SPORT_BODY));
    public static final DeferredHolder<Item, ItemCarPart> PINK_SPORT_BODY = ITEM_REGISTER.registerItem("pink_sport_body", p -> new ItemCarPart(p, PartRegistry.PINK_SPORT_BODY));
    public static final DeferredHolder<Item, ItemCarPart> PURPLE_SPORT_BODY = ITEM_REGISTER.registerItem("purple_sport_body", p -> new ItemCarPart(p, PartRegistry.PURPLE_SPORT_BODY));
    public static final DeferredHolder<Item, ItemCarPart> RED_SPORT_BODY = ITEM_REGISTER.registerItem("red_sport_body", p -> new ItemCarPart(p, PartRegistry.RED_SPORT_BODY));
    public static final DeferredHolder<Item, ItemCarPart> LIGHT_GRAY_SPORT_BODY = ITEM_REGISTER.registerItem("light_gray_sport_body", p -> new ItemCarPart(p, PartRegistry.LIGHT_GRAY_SPORT_BODY));
    public static final DeferredHolder<Item, ItemCarPart> WHITE_SPORT_BODY = ITEM_REGISTER.registerItem("white_sport_body", p -> new ItemCarPart(p, PartRegistry.WHITE_SPORT_BODY));
    public static final DeferredHolder<Item, ItemCarPart> YELLOW_SPORT_BODY = ITEM_REGISTER.registerItem("yellow_sport_body", p -> new ItemCarPart(p, PartRegistry.YELLOW_SPORT_BODY));

    public static final DeferredHolder<Item, ItemCarPart>[] SPORT_BODIES = new DeferredHolder[]{
            WHITE_SPORT_BODY, ORANGE_SPORT_BODY, MAGENTA_SPORT_BODY, LIGHT_BLUE_SPORT_BODY, YELLOW_SPORT_BODY,
            LIME_SPORT_BODY, PINK_SPORT_BODY, GRAY_SPORT_BODY, LIGHT_GRAY_SPORT_BODY, CYAN_SPORT_BODY,
            PURPLE_SPORT_BODY, BLUE_SPORT_BODY, BROWN_SPORT_BODY, GREEN_SPORT_BODY, RED_SPORT_BODY, BLACK_SPORT_BODY
    };

    public static final DeferredHolder<Item, ItemCarPart> BLACK_CONTAINER = ITEM_REGISTER.registerItem("black_container", p -> new ItemCarPart(p, PartRegistry.BLACK_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> BLUE_CONTAINER = ITEM_REGISTER.registerItem("blue_container", p -> new ItemCarPart(p, PartRegistry.BLUE_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> BROWN_CONTAINER = ITEM_REGISTER.registerItem("brown_container", p -> new ItemCarPart(p, PartRegistry.BROWN_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> CYAN_CONTAINER = ITEM_REGISTER.registerItem("cyan_container", p -> new ItemCarPart(p, PartRegistry.CYAN_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> GRAY_CONTAINER = ITEM_REGISTER.registerItem("gray_container", p -> new ItemCarPart(p, PartRegistry.GRAY_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> GREEN_CONTAINER = ITEM_REGISTER.registerItem("green_container", p -> new ItemCarPart(p, PartRegistry.GREEN_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> LIGHT_BLUE_CONTAINER = ITEM_REGISTER.registerItem("light_blue_container", p -> new ItemCarPart(p, PartRegistry.LIGHT_BLUE_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> LIME_CONTAINER = ITEM_REGISTER.registerItem("lime_container", p -> new ItemCarPart(p, PartRegistry.LIME_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> MAGENTA_CONTAINER = ITEM_REGISTER.registerItem("magenta_container", p -> new ItemCarPart(p, PartRegistry.MAGENTA_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> ORANGE_CONTAINER = ITEM_REGISTER.registerItem("orange_container", p -> new ItemCarPart(p, PartRegistry.ORANGE_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> PINK_CONTAINER = ITEM_REGISTER.registerItem("pink_container", p -> new ItemCarPart(p, PartRegistry.PINK_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> PURPLE_CONTAINER = ITEM_REGISTER.registerItem("purple_container", p -> new ItemCarPart(p, PartRegistry.PURPLE_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> RED_CONTAINER = ITEM_REGISTER.registerItem("red_container", p -> new ItemCarPart(p, PartRegistry.RED_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> LIGHT_GRAY_CONTAINER = ITEM_REGISTER.registerItem("light_gray_container", p -> new ItemCarPart(p, PartRegistry.LIGHT_GRAY_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> WHITE_CONTAINER = ITEM_REGISTER.registerItem("white_container", p -> new ItemCarPart(p, PartRegistry.WHITE_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> YELLOW_CONTAINER = ITEM_REGISTER.registerItem("yellow_container", p -> new ItemCarPart(p, PartRegistry.YELLOW_CONTAINER));

    public static final DeferredHolder<Item, ItemCarPart>[] CONTAINERS = new DeferredHolder[]{
            WHITE_CONTAINER, ORANGE_CONTAINER, MAGENTA_CONTAINER, LIGHT_BLUE_CONTAINER, YELLOW_CONTAINER,
            LIME_CONTAINER, PINK_CONTAINER, GRAY_CONTAINER, LIGHT_GRAY_CONTAINER, CYAN_CONTAINER,
            PURPLE_CONTAINER, BLUE_CONTAINER, BROWN_CONTAINER, GREEN_CONTAINER, RED_CONTAINER, BLACK_CONTAINER
    };

    public static final DeferredHolder<Item, ItemCarPart> BLACK_TANK_CONTAINER = ITEM_REGISTER.registerItem("black_tank_container", p -> new ItemCarPart(p, PartRegistry.BLACK_TANK_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> BLUE_TANK_CONTAINER = ITEM_REGISTER.registerItem("blue_tank_container", p -> new ItemCarPart(p, PartRegistry.BLUE_TANK_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> BROWN_TANK_CONTAINER = ITEM_REGISTER.registerItem("brown_tank_container", p -> new ItemCarPart(p, PartRegistry.BROWN_TANK_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> CYAN_TANK_CONTAINER = ITEM_REGISTER.registerItem("cyan_tank_container", p -> new ItemCarPart(p, PartRegistry.CYAN_TANK_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> GRAY_TANK_CONTAINER = ITEM_REGISTER.registerItem("gray_tank_container", p -> new ItemCarPart(p, PartRegistry.GRAY_TANK_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> GREEN_TANK_CONTAINER = ITEM_REGISTER.registerItem("green_tank_container", p -> new ItemCarPart(p, PartRegistry.GREEN_TANK_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> LIGHT_BLUE_TANK_CONTAINER = ITEM_REGISTER.registerItem("light_blue_tank_container", p -> new ItemCarPart(p, PartRegistry.LIGHT_BLUE_TANK_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> LIME_TANK_CONTAINER = ITEM_REGISTER.registerItem("lime_tank_container", p -> new ItemCarPart(p, PartRegistry.LIME_TANK_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> MAGENTA_TANK_CONTAINER = ITEM_REGISTER.registerItem("magenta_tank_container", p -> new ItemCarPart(p, PartRegistry.MAGENTA_TANK_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> ORANGE_TANK_CONTAINER = ITEM_REGISTER.registerItem("orange_tank_container", p -> new ItemCarPart(p, PartRegistry.ORANGE_TANK_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> PINK_TANK_CONTAINER = ITEM_REGISTER.registerItem("pink_tank_container", p -> new ItemCarPart(p, PartRegistry.PINK_TANK_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> PURPLE_TANK_CONTAINER = ITEM_REGISTER.registerItem("purple_tank_container", p -> new ItemCarPart(p, PartRegistry.PURPLE_TANK_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> RED_TANK_CONTAINER = ITEM_REGISTER.registerItem("red_tank_container", p -> new ItemCarPart(p, PartRegistry.RED_TANK_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> LIGHT_GRAY_TANK_CONTAINER = ITEM_REGISTER.registerItem("light_gray_tank_container", p -> new ItemCarPart(p, PartRegistry.LIGHT_GRAY_TANK_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> WHITE_TANK_CONTAINER = ITEM_REGISTER.registerItem("white_tank_container", p -> new ItemCarPart(p, PartRegistry.WHITE_TANK_CONTAINER));
    public static final DeferredHolder<Item, ItemCarPart> YELLOW_TANK_CONTAINER = ITEM_REGISTER.registerItem("yellow_tank_container", p -> new ItemCarPart(p, PartRegistry.YELLOW_TANK_CONTAINER));

    public static final DeferredHolder<Item, ItemCarPart>[] TANK_CONTAINERS = new DeferredHolder[]{
            WHITE_TANK_CONTAINER, ORANGE_TANK_CONTAINER, MAGENTA_TANK_CONTAINER, LIGHT_BLUE_TANK_CONTAINER, YELLOW_TANK_CONTAINER,
            LIME_TANK_CONTAINER, PINK_TANK_CONTAINER, GRAY_TANK_CONTAINER, LIGHT_GRAY_TANK_CONTAINER, CYAN_TANK_CONTAINER,
            PURPLE_TANK_CONTAINER, BLUE_TANK_CONTAINER, BROWN_TANK_CONTAINER, GREEN_TANK_CONTAINER, RED_TANK_CONTAINER, BLACK_TANK_CONTAINER
    };

    public static final DeferredHolder<Item, ItemCarPart> OAK_BUMPER = ITEM_REGISTER.registerItem("oak_bumper", p -> new ItemCarPart(p, PartRegistry.OAK_BUMPER));
    public static final DeferredHolder<Item, ItemCarPart> ACACIA_BUMPER = ITEM_REGISTER.registerItem("acacia_bumper", p -> new ItemCarPart(p, PartRegistry.ACACIA_BUMPER));
    public static final DeferredHolder<Item, ItemCarPart> DARK_OAK_BUMPER = ITEM_REGISTER.registerItem("dark_oak_bumper", p -> new ItemCarPart(p, PartRegistry.DARK_OAK_BUMPER));
    public static final DeferredHolder<Item, ItemCarPart> BIRCH_BUMPER = ITEM_REGISTER.registerItem("birch_bumper", p -> new ItemCarPart(p, PartRegistry.BIRCH_BUMPER));
    public static final DeferredHolder<Item, ItemCarPart> JUNGLE_BUMPER = ITEM_REGISTER.registerItem("jungle_bumper", p -> new ItemCarPart(p, PartRegistry.JUNGLE_BUMPER));
    public static final DeferredHolder<Item, ItemCarPart> SPRUCE_BUMPER = ITEM_REGISTER.registerItem("spruce_bumper", p -> new ItemCarPart(p, PartRegistry.SPRUCE_BUMPER));
    public static final DeferredHolder<Item, ItemCarPart> CRIMSON_BUMPER = ITEM_REGISTER.registerItem("crimson_bumper", p -> new ItemCarPart(p, PartRegistry.CRIMSON_BUMPER));
    public static final DeferredHolder<Item, ItemCarPart> WARPED_BUMPER = ITEM_REGISTER.registerItem("warped_bumper", p -> new ItemCarPart(p, PartRegistry.WARPED_BUMPER));

    public static final DeferredHolder<Item, ItemCarPart>[] BUMPERS = new DeferredHolder[]{
            OAK_BUMPER, SPRUCE_BUMPER, BIRCH_BUMPER, JUNGLE_BUMPER, ACACIA_BUMPER, DARK_OAK_BUMPER, CRIMSON_BUMPER, WARPED_BUMPER
    };

    public static final DeferredHolder<Item, ItemCarPart> OAK_LICENSE_PLATE_HOLDER = ITEM_REGISTER.registerItem("oak_license_plate_holder", p -> new ItemCarPart(p, PartRegistry.OAK_LICENSE_PLATE_HOLDER));
    public static final DeferredHolder<Item, ItemCarPart> ACACIA_LICENSE_PLATE_HOLDER = ITEM_REGISTER.registerItem("acacia_license_plate_holder", p -> new ItemCarPart(p, PartRegistry.ACACIA_LICENSE_PLATE_HOLDER));
    public static final DeferredHolder<Item, ItemCarPart> DARK_OAK_LICENSE_PLATE_HOLDER = ITEM_REGISTER.registerItem("dark_oak_license_plate_holder", p -> new ItemCarPart(p, PartRegistry.DARK_OAK_LICENSE_PLATE_HOLDER));
    public static final DeferredHolder<Item, ItemCarPart> BIRCH_LICENSE_PLATE_HOLDER = ITEM_REGISTER.registerItem("birch_license_plate_holder", p -> new ItemCarPart(p, PartRegistry.BIRCH_LICENSE_PLATE_HOLDER));
    public static final DeferredHolder<Item, ItemCarPart> JUNGLE_LICENSE_PLATE_HOLDER = ITEM_REGISTER.registerItem("jungle_license_plate_holder", p -> new ItemCarPart(p, PartRegistry.JUNGLE_LICENSE_PLATE_HOLDER));
    public static final DeferredHolder<Item, ItemCarPart> SPRUCE_LICENSE_PLATE_HOLDER = ITEM_REGISTER.registerItem("spruce_license_plate_holder", p -> new ItemCarPart(p, PartRegistry.SPRUCE_LICENSE_PLATE_HOLDER));
    public static final DeferredHolder<Item, ItemCarPart> CRIMSON_LICENSE_PLATE_HOLDER = ITEM_REGISTER.registerItem("crimson_license_plate_holder", p -> new ItemCarPart(p, PartRegistry.CRIMSON_LICENSE_PLATE_HOLDER));
    public static final DeferredHolder<Item, ItemCarPart> WARPED_LICENSE_PLATE_HOLDER = ITEM_REGISTER.registerItem("warped_license_plate_holder", p -> new ItemCarPart(p, PartRegistry.WARPED_LICENSE_PLATE_HOLDER));

    public static final DeferredHolder<Item, ItemCarPart>[] WOODEN_LICENSE_PLATE_HOLDERS = new DeferredHolder[]{
            OAK_LICENSE_PLATE_HOLDER, SPRUCE_LICENSE_PLATE_HOLDER, BIRCH_LICENSE_PLATE_HOLDER, JUNGLE_LICENSE_PLATE_HOLDER,
            ACACIA_LICENSE_PLATE_HOLDER, DARK_OAK_LICENSE_PLATE_HOLDER, CRIMSON_LICENSE_PLATE_HOLDER, WARPED_LICENSE_PLATE_HOLDER
    };

    public static final DeferredHolder<Item, ItemCarPart> IRON_LICENSE_PLATE_HOLDER = ITEM_REGISTER.registerItem("iron_license_plate_holder", p -> new ItemCarPart(p, PartRegistry.IRON_LICENSE_PLATE_HOLDER));
    public static final DeferredHolder<Item, ItemCarPart> DIAMOND_LICENSE_PLATE_HOLDER = ITEM_REGISTER.registerItem("diamond_license_plate_holder", p -> new ItemCarPart(p, PartRegistry.DIAMOND_LICENSE_PLATE_HOLDER));
    public static final DeferredHolder<Item, ItemCarPart> GOLD_LICENSE_PLATE_HOLDER = ITEM_REGISTER.registerItem("gold_license_plate_holder", p -> new ItemCarPart(p, PartRegistry.GOLD_LICENSE_PLATE_HOLDER));
    public static final DeferredHolder<Item, ItemCarPart> EMERALD_LICENSE_PLATE_HOLDER = ITEM_REGISTER.registerItem("emerald_license_plate_holder", p -> new ItemCarPart(p, PartRegistry.EMERALD_LICENSE_PLATE_HOLDER));

    public static final DeferredHolder<Item, ItemCarPart> SMALL_TANK = ITEM_REGISTER.registerItem("small_tank", p -> new ItemCarPart(p, PartRegistry.SMALL_TANK));
    public static final DeferredHolder<Item, ItemCarPart> MEDIUM_TANK = ITEM_REGISTER.registerItem("medium_tank", p -> new ItemCarPart(p, PartRegistry.MEDIUM_TANK));
    public static final DeferredHolder<Item, ItemCarPart> LARGE_TANK = ITEM_REGISTER.registerItem("large_tank", p -> new ItemCarPart(p, PartRegistry.LARGE_TANK));

    public static final DeferredHolder<Item, CarBucketItem> CANOLA_OIL_BUCKET = ITEM_REGISTER.registerItem("canola_oil_bucket", p -> new CarBucketItem(ModFluids.CANOLA_OIL.get(), p));
    public static final DeferredHolder<Item, CarBucketItem> METHANOL_BUCKET = ITEM_REGISTER.registerItem("methanol_bucket", p -> new CarBucketItem(ModFluids.METHANOL.get(), p));
    public static final DeferredHolder<Item, CarBucketItem> CANOLA_METHANOL_MIX_BUCKET = ITEM_REGISTER.registerItem("canola_methanol_mix_bucket", p -> new CarBucketItem(ModFluids.CANOLA_METHANOL_MIX.get(), p));
    public static final DeferredHolder<Item, CarBucketItem> GLYCERIN_BUCKET = ITEM_REGISTER.registerItem("glycerin_bucket", p -> new CarBucketItem(ModFluids.GLYCERIN.get(), p));
    public static final DeferredHolder<Item, CarBucketItem> BIO_DIESEL_BUCKET = ITEM_REGISTER.registerItem("bio_diesel_bucket", p -> new CarBucketItem(ModFluids.BIO_DIESEL.get(), p));

    public static final DeferredHolder<Item, BlockItem> ASPHALT = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.ASPHALT);
    public static final DeferredHolder<Item, BlockItem> ASPHALT_SLOPE = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.ASPHALT_SLOPE);
    public static final DeferredHolder<Item, BlockItem> ASPHALT_SLOPE_FLAT_UPPER = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.ASPHALT_SLOPE_FLAT_UPPER);
    public static final DeferredHolder<Item, BlockItem> ASPHALT_SLOPE_FLAT_LOWER = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.ASPHALT_SLOPE_FLAT_LOWER);
    public static final DeferredHolder<Item, BlockItem> ASPHALT_SLAB = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.ASPHALT_SLAB);
    public static final DeferredHolder<Item, BlockItem> GAS_STATION = ITEM_REGISTER.registerItem("gas_station", p -> new BlockGasStation.GasStationItem(ModBlocks.GAS_STATION.get(), p));
    public static final DeferredHolder<Item, BlockItem> OIL_MILL = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.OIL_MILL);
    public static final DeferredHolder<Item, BlockItem> BLAST_FURNACE = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.BLAST_FURNACE);
    public static final DeferredHolder<Item, BlockItem> BACKMIX_REACTOR = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.BACKMIX_REACTOR);
    public static final DeferredHolder<Item, BlockItem> GENERATOR = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.GENERATOR);
    public static final DeferredHolder<Item, BlockItem> SPLIT_TANK = ITEM_REGISTER.registerItem("split_tank", p -> new BlockSplitTank.SplitTankItem(ModBlocks.SPLIT_TANK.get(), p));
    public static final DeferredHolder<Item, BlockItem> TANK = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.TANK);
    public static final DeferredHolder<Item, BlockItem> CAR_WORKSHOP = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.CAR_WORKSHOP);
    public static final DeferredHolder<Item, BlockItem> CAR_WORKSHOP_OUTTER = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.CAR_WORKSHOP_OUTTER);
    public static final DeferredHolder<Item, BlockItem> CABLE = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.CABLE);
    public static final DeferredHolder<Item, BlockItem> FLUID_EXTRACTOR = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.FLUID_EXTRACTOR);
    public static final DeferredHolder<Item, BlockItem> FLUID_PIPE = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.FLUID_PIPE);
    public static final DeferredHolder<Item, BlockItem> DYNAMO = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.DYNAMO);
    public static final DeferredHolder<Item, BlockItem> CRANK = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.CRANK);
    public static final DeferredHolder<Item, BlockItem> SIGN = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.SIGN);
    public static final DeferredHolder<Item, BlockItem> SIGN_POST = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.SIGN_POST);
    public static final DeferredHolder<Item, BlockItem> CAR_PRESSURE_PLATE = ITEM_REGISTER.registerSimpleBlockItem(ModBlocks.CAR_PRESSURE_PLATE);

    public static final DeferredHolder<Item, BlockItem>[] PAINTS;
    public static final DeferredHolder<Item, BlockItem>[] YELLOW_PAINTS;

    static {
        PAINTS = new DeferredHolder[BlockPaint.EnumPaintType.values().length];
        for (int i = 0; i < PAINTS.length; i++) {
            int paintIndex = i;
            PAINTS[i] = ITEM_REGISTER.registerItem(BlockPaint.EnumPaintType.values()[i].getPaintName(), p -> new BlockPaint.PaintItem(ModBlocks.PAINTS[paintIndex].get(), p));
        }

        YELLOW_PAINTS = new DeferredHolder[BlockPaint.EnumPaintType.values().length];
        for (int i = 0; i < YELLOW_PAINTS.length; i++) {
            int paintIndex = i;
            YELLOW_PAINTS[i] = ITEM_REGISTER.registerItem(BlockPaint.EnumPaintType.values()[i].getPaintName() + "_yellow", p -> new BlockPaint.PaintItem(ModBlocks.YELLOW_PAINTS[paintIndex].get(), p));
        }
    }

    public static void init(IEventBus eventBus) {
        ITEM_REGISTER.register(eventBus);
    }

}
