package de.maxhenkel.car;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class Reciepes {

	public static void registerReciepes(){
		//Tar
		if(Config.tarRecipe){
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "tar_wool"), null, new ItemStack(ModBlocks.TAR, 8), new Object[] { "XXX", "XWX", "XXX",
					Character.valueOf('X'), Blocks.COBBLESTONE, Character.valueOf('W'), new ItemStack(Blocks.WOOL, 1, 15) });
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "tar_coal"), null, new ItemStack(ModBlocks.TAR, 8), new Object[] { "XXX", "XWX", "XXX",
					Character.valueOf('X'), Blocks.COBBLESTONE, Character.valueOf('W'), Items.COAL });
			
			//Tar Slab
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "tar_slab"), null, new ItemStack(ModBlocks.TAR_SLAB, 6), new Object[] { "XXX",
					Character.valueOf('X'), ModBlocks.TAR});
			
			//Tar Slope
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "tar_slope"), null, new ItemStack(ModBlocks.TAR_SLOPE, 6), new Object[] { "TXX", "TTX", "TTT",
					Character.valueOf('T'), ModBlocks.TAR});
			
			//Lower Tar Slope
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "tar_slope_flat_lower"), null, new ItemStack(ModBlocks.TAR_SLOPE_FLAT_LOWER, 4), new Object[] { "TXX", "TTT",
					Character.valueOf('T'), ModBlocks.TAR});
			
			//Upper Tar Slope
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "tar_slope_flat_upper"), null, new ItemStack(ModBlocks.TAR_SLOPE_FLAT_UPPER, 4), new Object[] { "TX", "TT", "TT",
					Character.valueOf('T'), ModBlocks.TAR});
		}
		
		if(Config.painterRecipe){
			//Painter White
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "painter_white"), null, new ItemStack(ModItems.PAINTER, 1), new Object[] { "XBX", "XSB", "SXX",
					Character.valueOf('S'), Items.STICK, Character.valueOf('B'), new ItemStack(Items.DYE, 1, 15) });
			
			//Painter Yellow
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "painter_yellow"), null, new ItemStack(ModItems.PAINTER_YELLOW, 1), new Object[] { "XBX", "XSB", "SXX",
					Character.valueOf('S'), Items.STICK, Character.valueOf('B'), new ItemStack(Items.DYE, 1, 11) });
		}
		
		if(Config.tankRecipe){
			//Tank
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "tank"), null, new ItemStack(ModBlocks.TANK, 1), new Object[] { "IGI", "GXG", "IGI",
					Character.valueOf('G'), Blocks.GLASS, Character.valueOf('I'), Items.IRON_INGOT });
		}
		
		if(Config.carPartsRecipe){
			//Car Tank
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "car_tank"), null, new ItemStack(ModItems.CAR_TANK, 1), new Object[] { "III", "ITI", "III",
					Character.valueOf('T'), ModBlocks.TANK, Character.valueOf('I'), Items.IRON_INGOT });
			
			//Windshield
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "windshield"), null, new ItemStack(ModItems.WINDSHIELD, 1), new Object[] { "PPP", "PPP",
					Character.valueOf('P'), Blocks.GLASS_PANE});
			
			//Car Seat
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "car_seat"), null, new ItemStack(ModItems.CAR_SEAT, 1), new Object[] { "XWP", "WWP", "PPP",
					Character.valueOf('P'), Blocks.PLANKS, Character.valueOf('W'), Blocks.WOOL });
			
			//Car Engine
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "engine_3_cylinder"), null, new ItemStack(ModItems.ENGINE_3_CYLINDER, 1), new Object[] { "FFF", "PPP", "BBB",
					Character.valueOf('F'), Items.FLINT, Character.valueOf('P'), ModItems.ENGINE_PISTON, 
					Character.valueOf('B'), Blocks.IRON_BLOCK });
			
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "engine_6_cylinder"), null, new ItemStack(ModItems.ENGINE_6_CYLINDER, 1), new Object[] { "III", "EIE", "III",
					Character.valueOf('I'), Items.IRON_INGOT, 
					Character.valueOf('E'), ModItems.ENGINE_3_CYLINDER });
			
			//Wheel
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "wheel"), null, new ItemStack(ModItems.WHEEL, 1), new Object[] { "XWX", "WIW", "XWX",
					Character.valueOf('W'), new ItemStack(Blocks.WOOL, 1, 15),
					Character.valueOf('I'), Items.IRON_INGOT});
			
			//Axle
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "axle"), null, new ItemStack(ModItems.AXLE, 1), new Object[] { "WSW",
					Character.valueOf('W'), ModItems.WHEEL,
					Character.valueOf('S'), ModItems.IRON_STICK});
			
			//Container
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "container"), null, new ItemStack(ModItems.CONTAINER, 1), new Object[] { "ICI", "CXC", "ICI",
					Character.valueOf('I'), Items.IRON_INGOT,
					Character.valueOf('C'), Blocks.CHEST});
			
			//Control unit
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "control_unit"), null, new ItemStack(ModItems.CONTROL_UNIT, 1), new Object[] { "IRI", "RSR", "IRI",
					Character.valueOf('I'), Items.IRON_INGOT, 
					Character.valueOf('R'), Items.REDSTONE, 
					Character.valueOf('S'), Blocks.SAND});
		
			//Iron Stick
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "iron_stick"), null, new ItemStack(ModItems.IRON_STICK, 2), new Object[] { "I", "I",
					Character.valueOf('I'), Items.IRON_INGOT});
			OreDictionary.registerOre("stickIron", ModItems.IRON_STICK);
			
			//Engine Piston
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "engine_piston"), null, new ItemStack(ModItems.ENGINE_PISTON, 1), new Object[] { "XXI", "XSX", "SXX",
					Character.valueOf('I'), Items.IRON_INGOT, Character.valueOf('S'), ModItems.IRON_STICK});
			
			//Body Part
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "car_body_part_wood"), null, new ItemStack(ModItems.CAR_BODY_PART_WOOD, 1, 0), new Object[] { "LPL", "LPL",
					Character.valueOf('L'), new ItemStack(Blocks.LOG, 1, 0), 
					Character.valueOf('P'), new ItemStack(Blocks.PLANKS, 1, 0)});
			
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "car_body_part_wood"), null, new ItemStack(ModItems.CAR_BODY_PART_WOOD, 1, 1), new Object[] { "LPL", "LPL",
					Character.valueOf('L'), new ItemStack(Blocks.LOG, 1, 1), 
					Character.valueOf('P'), new ItemStack(Blocks.PLANKS, 1, 1)});
			
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "car_body_part_wood"), null, new ItemStack(ModItems.CAR_BODY_PART_WOOD, 1, 2), new Object[] { "LPL", "LPL",
					Character.valueOf('L'), new ItemStack(Blocks.LOG, 1, 2), 
					Character.valueOf('P'), new ItemStack(Blocks.PLANKS, 1, 2)});
			
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "car_body_part_wood"), null, new ItemStack(ModItems.CAR_BODY_PART_WOOD, 1, 3), new Object[] { "LPL", "LPL",
					Character.valueOf('L'), new ItemStack(Blocks.LOG, 1, 3), 
					Character.valueOf('P'), new ItemStack(Blocks.PLANKS, 1, 3)});
			
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "car_body_part_wood"), null, new ItemStack(ModItems.CAR_BODY_PART_WOOD, 1, 4), new Object[] { "LPL", "LPL",
					Character.valueOf('L'), new ItemStack(Blocks.LOG2, 1, 0), 
					Character.valueOf('P'), new ItemStack(Blocks.PLANKS, 1, 4)});
			
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "car_body_part_wood"), null, new ItemStack(ModItems.CAR_BODY_PART_WOOD, 1, 5), new Object[] { "LPL", "LPL",
					Character.valueOf('L'), new ItemStack(Blocks.LOG2, 1, 1), 
					Character.valueOf('P'), new ItemStack(Blocks.PLANKS, 1, 5)});
			
		}
		
		if(Config.crashBarrierRecipe){
			//Crash Barrier
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "crash_barrier"), null, new ItemStack(ModBlocks.CRASH_BARRIER, 32), new Object[] { "III", "III", "IXI",
					Character.valueOf('I'), Items.IRON_INGOT});
		}
		
		if(Config.fuelStationRecipe){
			//Fuel Station
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "fuel_station"), null, new ItemStack(ModBlocks.FUEL_STATION, 1), new Object[] { "IBI", "ICI", "STS",
					Character.valueOf('I'), Items.IRON_INGOT,
					Character.valueOf('B'), Blocks.IRON_BLOCK,
					Character.valueOf('C'), ModItems.CONTROL_UNIT,
					Character.valueOf('T'), ModBlocks.TANK,
					Character.valueOf('S'), new ItemStack(Blocks.STONE_SLAB, 1, 0)});
		}
		
		if(Config.blastFurnaceRecipe){
			//Blast Furnace
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "blast_furnace"), null, new ItemStack(ModBlocks.BLAST_FURNACE, 1), new Object[] { "III", "IFI", "III",
					Character.valueOf('I'), Items.IRON_INGOT,
					Character.valueOf('F'), Blocks.FURNACE});
		}
		
		if(Config.oilMillRecipe){
			//Oil Mill
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "oil_mill"), null, new ItemStack(ModBlocks.OIL_MILL, 1), new Object[] { "III", "IPI", "III",
					Character.valueOf('I'), Items.IRON_INGOT,
					Character.valueOf('P'), Blocks.PISTON});
		}
		
		if(Config.backmixReactorRecipe){
			//Backmix Reactor
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "backmix_reactor"), null, new ItemStack(ModBlocks.BACKMIX_REACTOR, 1), new Object[] { "ICI", "ITI", "III",
					Character.valueOf('I'), Items.IRON_INGOT,
					Character.valueOf('T'), ModBlocks.TANK,
					Character.valueOf('C'), ModItems.CONTROL_UNIT});
		}
		
		if(Config.splitTankRecipe){
			//Split Tank
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "split_tank"), null, new ItemStack(ModBlocks.SPLIT_TANK, 1), new Object[] { "III", "GTG", "GTG",
					Character.valueOf('I'), Items.IRON_INGOT,
					Character.valueOf('T'), ModBlocks.TANK,
					Character.valueOf('G'), Blocks.GLASS});
		}
		
		if(Config.generatorRecipe){
			//Generator
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "generator"), null, new ItemStack(ModBlocks.GENERATOR, 1), new Object[] { "ICI", "IEI", "ITI",
					Character.valueOf('I'), Items.IRON_INGOT,
					Character.valueOf('T'), ModBlocks.TANK,
					Character.valueOf('E'), ModItems.ENGINE_3_CYLINDER,
					Character.valueOf('C'), ModItems.CONTROL_UNIT});
		}
		
		if(Config.carWorkshopRecipe){
			//Car Workshop part
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "car_workshop_outter"), null, new ItemStack(ModBlocks.CAR_WORKSHOP_OUTTER, 1), new Object[] { "III", "XIX", "III",
					Character.valueOf('I'), Items.IRON_INGOT});
			
			//Car Workshop
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "car_workshop"), null, new ItemStack(ModBlocks.CAR_WORKSHOP, 1), new Object[] { "III", "XPX", "III",
					Character.valueOf('I'), Items.IRON_INGOT,
					Character.valueOf('P'), Blocks.PISTON});
			
			//Wrench
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "wrench"), null, new ItemStack(ModItems.WRENCH, 1), new Object[] { "XIX", "XSI", "SXX",
					Character.valueOf('I'), Items.IRON_INGOT,
					Character.valueOf('S'), ModItems.IRON_STICK});
			OreDictionary.registerOre("wrench", ModItems.WRENCH);
			
			//Screw Driver
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "screw_driver"), null, new ItemStack(ModItems.SCREW_DRIVER, 1), new Object[] { "S", "S", "I",
					Character.valueOf('I'), Items.IRON_INGOT,
					Character.valueOf('S'), ModItems.IRON_STICK});
			
			//Hammer
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "hammer"), null, new ItemStack(ModItems.HAMMER, 1), new Object[] { "III", "XSX", "XSX",
					Character.valueOf('I'), Items.IRON_INGOT,
					Character.valueOf('S'), ModItems.IRON_STICK});
		}
		
		if(Config.canisterRecipe){
			//Canister
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "canister"), null, new ItemStack(ModItems.CANISTER, 1), new Object[] { "IIX", "ITI", "III",
					Character.valueOf('I'), Items.IRON_INGOT,
					Character.valueOf('T'), ModBlocks.TANK});
		}
		
		if(Config.repairKitRecipe){
			//Repair Kit
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "repair_kit"), null, new ItemStack(ModItems.REPAIR_KIT, 1), new Object[] { "III", "SWH", "III",
					Character.valueOf('I'), Items.IRON_INGOT,
					Character.valueOf('W'), new ItemStack(ModItems.WRENCH, 1, 0),
					Character.valueOf('S'), new ItemStack(ModItems.SCREW_DRIVER, 1, 0),
					Character.valueOf('H'), new ItemStack(ModItems.HAMMER, 1, 0)});
		}
		
		if(Config.cableRecipe){
			//Cable insulator
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "cable_insulator"), null, new ItemStack(ModItems.CABLE_INSULATOR, 16), new Object[] { "WWW",
					Character.valueOf('W'), Blocks.WOOL});
			
			//Cable
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "cable"), null, new ItemStack(ModBlocks.CABLE, 8), new Object[] { "III", "RRR", "III",
					Character.valueOf('I'), ModItems.CABLE_INSULATOR,
					Character.valueOf('R'), Items.REDSTONE});
		}
		
		if(Config.fluidExtractorRecipe){
			//Fluid extractor
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "fluid_extractor"), null, new ItemStack(ModBlocks.FLUID_EXTRACTOR, 4), new Object[] { "IXX", "IPP", "IXX",
					Character.valueOf('I'), Items.IRON_INGOT,
					Character.valueOf('P'), ModBlocks.FLUID_PIPE});
		}
		
		if(Config.fluidPipeRecipe){
			//Fluid_pipe
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "fluid_pipe"), null, new ItemStack(ModBlocks.FLUID_PIPE, 16), new Object[] { "III", "XXX", "III",
					Character.valueOf('I'), Items.IRON_INGOT});
		}
		
		if(Config.dynamoRecipe){
			//Crank
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "crank"), null, new ItemStack(ModBlocks.CRANK, 1), new Object[] { "SS", "XS",
					Character.valueOf('S'), Items.STICK});
			
			//Dynamo
			GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "dynamo"), null, new ItemStack(ModBlocks.DYNAMO, 1), new Object[] { "ICI", "IXI", "III",
					Character.valueOf('I'), Items.IRON_INGOT,
					Character.valueOf('C'), ModItems.CONTROL_UNIT});
		}
	}
	
}
