package de.maxhenkel.car.items;

import de.maxhenkel.car.entity.car.parts.PartRegistry;
import de.maxhenkel.tools.NoRegister;
import net.minecraft.item.Item;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static final ItemPainter PAINTER = new ItemPainter(false);
    public static final ItemPainter PAINTER_YELLOW = new ItemPainter(true);
    public static final ItemCanolaSeed CANOLA_SEEDS = new ItemCanolaSeed();
    public static final ItemCanola CANOLA = new ItemCanola();
    public static final ItemRapeCake RAPECAKE = new ItemRapeCake();
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

    //public static final ItemCraftingComponent WINDSHIELD = new ItemCraftingComponent("windshield");
    //public static final ItemCraftingComponent CONTROL_UNIT = new ItemCraftingComponent("control_unit");
    //public static final ItemCraftingComponent AXLE = new ItemCraftingComponent("axle");

    /** ---- CAR PARTS ---- **/

    public static final ItemCarPart ENGINE_3_CYLINDER = new ItemCarPart("engine_3_cylinder", PartRegistry.ENGINE_3_CYLINDER);
    public static final ItemCarPart ENGINE_6_CYLINDER = new ItemCarPart("engine_6_cylinder", PartRegistry.ENGINE_6_CYLINDER);

    public static final ItemCarPart CAR_SEAT = new ItemCarPart("car_seat", null);

    public static final ItemCarPart WHEEL = new ItemCarPart("wheel", PartRegistry.WHEEL);

    public static final ItemCraftingComponent CAR_TANK = new ItemCraftingComponent("car_tank");


    public static final ItemCarPart OAK_BODY = new ItemCarPart("oak_body", PartRegistry.OAK_BODY);
    public static final ItemCarPart ACACIA_BODY = new ItemCarPart("acacia_body", PartRegistry.ACACIA_BODY);
    public static final ItemCarPart DARK_OAK_BODY = new ItemCarPart("dark_oak_body", PartRegistry.DARK_OAK_BODY);
    public static final ItemCarPart BIRCH_BODY = new ItemCarPart("birch_body", PartRegistry.BIRCH_BODY);
    public static final ItemCarPart JUNGLE_BODY = new ItemCarPart("jungle_body", PartRegistry.JUNGLE_BODY);
    public static final ItemCarPart SPRUCE_BODY = new ItemCarPart("spruce_body", PartRegistry.SPRUCE_BODY);

    public static final ItemCarPart BIG_OAK_BODY = new ItemCarPart("big_oak_body", PartRegistry.BIG_OAK_BODY);
    public static final ItemCarPart BIG_ACACIA_BODY = new ItemCarPart("big_acacia_body", PartRegistry.BIG_ACACIA_BODY);
    public static final ItemCarPart BIG_DARK_OAK_BODY = new ItemCarPart("big_dark_oak_body", PartRegistry.BIG_DARK_OAK_BODY);
    public static final ItemCarPart BIG_BIRCH_BODY = new ItemCarPart("big_birch_body", PartRegistry.BIG_BIRCH_BODY);
    public static final ItemCarPart BIG_JUNGLE_BODY = new ItemCarPart("big_jungle_body", PartRegistry.BIG_JUNGLE_BODY);
    public static final ItemCarPart BIG_SPRUCE_BODY = new ItemCarPart("big_spruce_body", PartRegistry.BIG_SPRUCE_BODY);

    public static final ItemCarPart WHITE_TRANSPORTER_BODY = new ItemCarPart("white_transporter_body", PartRegistry.WHITE_TRANSPORTER_BODY);

    public static final ItemCarPart WHITE_SPORT_BODY = new ItemCarPart("white_sport_body", PartRegistry.WHITE_SPORT_BODY);

    public static final ItemCarPart OAK_BUMPER = new ItemCarPart("oak_bumper", PartRegistry.OAK_BUMPER);

    public static final ItemCarPart CONTAINER = new ItemCarPart("container", null);

    public static final ItemNumberPlate NUMBER_PLATE = new ItemNumberPlate("number_plate", PartRegistry.NUMBER_PLATE_OAK);//TODO


    public static List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        for (Field field : ModItems.class.getFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof NoRegister) {
                    continue;
                }
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
