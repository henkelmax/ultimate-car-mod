package de.maxhenkel.car.items;

import java.util.ArrayList;
import java.util.List;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

	public static final ItemPainter PAINTER = new ItemPainter(false);
	public static final ItemPainter PAINTER_YELLOW = new ItemPainter(true);
	public static final ItemCanolaSeed CANOLA_SEEDS = new ItemCanolaSeed();
	public static final ItemCanola CANOLA = new ItemCanola();
	public static final ItemRapeCake RAPECAKE = new ItemRapeCake();
	public static final ItemCraftingComponent IRON_STICK = new ItemCraftingComponent("iron_stick");
	public static final ItemCraftingComponent ENGINE_PISTON = new ItemCraftingComponent("engine_piston");
	public static final ItemCraftingComponent ENGINE_3_CYLINDER = new ItemCraftingComponent("engine_3_cylinder");
	public static final ItemCraftingComponent CAR_SEAT = new ItemCraftingComponent("car_seat");
	public static final ItemCraftingComponent WINDSHIELD = new ItemCraftingComponent("windshield");
	public static final ItemCraftingComponent WOODEN_WHEEL = new ItemCraftingComponent("wooden_wheel");
	public static final ItemCarBodyPartWood CAR_BODY_PART_WOOD = new ItemCarBodyPartWood();
	public static final ItemCraftingComponent CAR_TANK = new ItemCraftingComponent("car_tank");
	public static final ItemCraftingComponent CONTROL_UNIT = new ItemCraftingComponent("control_unit");
	public static final ItemCanister CANISTER = new ItemCanister();
	public static final ItemRepairKit REPAIR_KIT = new ItemRepairKit();
	public static final ItemRepairTool WRENCH = new ItemRepairTool("wrench");
	public static final ItemRepairTool SCREW_DRIVER = new ItemRepairTool("screw_driver");
	public static final ItemRepairTool HAMMER = new ItemRepairTool("hammer");
	public static final ItemCraftingComponent CABLE_INSULATOR = new ItemCraftingComponent("cable_insulator");
	
	public static Item CANOLA_OIL_BUCKET;
	public static Item METHANOL_BUCKET;
	public static Item CANOLA_METHANOL_MIX_BUCKET;
	public static Item GLYCERIN_BUCKET;
	public static Item BIO_DIESEL_BUCKET;
	
	public static void registerItems(){
		registerItem(PAINTER);
		registerItem(PAINTER_YELLOW);
		registerItem(CANOLA_SEEDS);
		registerItem(CANOLA);
		registerItem(RAPECAKE);
		
		registerItem(IRON_STICK);
		registerItem(ENGINE_PISTON);
		registerItem(ENGINE_3_CYLINDER);
		registerItem(CAR_SEAT);
		registerItem(WINDSHIELD);
		registerItem(WOODEN_WHEEL);
		registerItem(CAR_BODY_PART_WOOD);
		registerItem(CAR_TANK);
		registerItem(CONTROL_UNIT);
		registerItem(CANISTER);
		registerItem(REPAIR_KIT);
		registerItem(WRENCH);
		registerItem(SCREW_DRIVER);
		registerItem(HAMMER);
		registerItem(CABLE_INSULATOR);
	}

	public static void registerItemsClient() {
		addRenderItem(PAINTER);
		addRenderItem(PAINTER_YELLOW);
		addRenderItem(CANOLA_SEEDS);
		addRenderItem(CANOLA);
		addRenderItem(RAPECAKE);
		
		addRenderItem(IRON_STICK);
		addRenderItem(ENGINE_PISTON);
		addRenderItem(ENGINE_3_CYLINDER);
		addRenderItem(CAR_SEAT);
		addRenderItem(WINDSHIELD);
		addRenderItem(WOODEN_WHEEL);
		addRenderItem(CAR_BODY_PART_WOOD);
		addRenderItem(CAR_TANK);
		addRenderItem(CONTROL_UNIT);
		addRenderItem(CANISTER);
		addRenderItem(REPAIR_KIT);
		addRenderItem(WRENCH);
		addRenderItem(SCREW_DRIVER);
		addRenderItem(HAMMER);
		addRenderItem(CABLE_INSULATOR);
	}
	
	private static void addRenderItem(Item item) {
		if(item.getHasSubtypes()){
			List<ItemStack> list=new ArrayList<ItemStack>();
			item.getSubItems(item, ModCreativeTabs.TAB_CAR, list);
			for(int i=0; i<list.size(); i++){
				ResourceLocation loc=new ResourceLocation(Main.MODID, item.getRegistryName().getResourcePath() +"_" +i);
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(loc, "inventory"));
			}
		}else{
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
	
	private static void registerItem(Item i) {
		GameRegistry.register(i);
	}
}
