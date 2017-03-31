package de.maxhenkel.car;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class Reciepes {

	public static void registerReciepes(){
		//Tar
		GameRegistry.addRecipe(new ItemStack(ModBlocks.TAR, 8), new Object[] { "XXX", "XWX", "XXX",
				Character.valueOf('X'), Blocks.COBBLESTONE, Character.valueOf('W'), new ItemStack(Blocks.WOOL, 1, 15) });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.TAR, 8), new Object[] { "XXX", "XWX", "XXX",
				Character.valueOf('X'), Blocks.COBBLESTONE, Character.valueOf('W'), Items.COAL });
		
		//Tar Slab
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.TAR_SLAB, 6), new Object[] { "XXX",
				Character.valueOf('X'), ModBlocks.TAR});
		
		//Tar Slope
		GameRegistry.addRecipe(new ItemStack(ModBlocks.TAR_SLOPE, 6), new Object[] { "TXX", "TTX", "TTT",
				Character.valueOf('T'), ModBlocks.TAR});
		
		//Lower Tar Slope
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.TAR_SLOPE_FLAT_LOWER, 4), new Object[] { "TXX", "TTT",
				Character.valueOf('T'), ModBlocks.TAR});
		
		//Upper Tar Slope
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.TAR_SLOPE_FLAT_UPPER, 4), new Object[] { "TX", "TT", "TT",
				Character.valueOf('T'), ModBlocks.TAR});
		
		//Painter White
		GameRegistry.addRecipe(new ItemStack(ModItems.PAINTER, 1), new Object[] { "XBX", "XSB", "SXX",
				Character.valueOf('S'), Items.STICK, Character.valueOf('B'), new ItemStack(Items.DYE, 1, 15) });
		GameRegistry.addRecipe(new ItemStack(ModItems.PAINTER, 1), new Object[] { "XBX", "BSX", "XXS",
				Character.valueOf('S'), Items.STICK, Character.valueOf('B'), new ItemStack(Items.DYE, 1, 15) });
		
		//Painter Yellow
		GameRegistry.addRecipe(new ItemStack(ModItems.PAINTER_YELLOW, 1), new Object[] { "XBX", "XSB", "SXX",
				Character.valueOf('S'), Items.STICK, Character.valueOf('B'), new ItemStack(Items.DYE, 1, 11) });
		GameRegistry.addRecipe(new ItemStack(ModItems.PAINTER_YELLOW, 1), new Object[] { "XBX", "BSX", "XXS",
				Character.valueOf('S'), Items.STICK, Character.valueOf('B'), new ItemStack(Items.DYE, 1, 11) });
		
		//Tank
		GameRegistry.addRecipe(new ItemStack(ModBlocks.TANK, 1), new Object[] { "IGI", "GXG", "IGI",
				Character.valueOf('G'), Blocks.GLASS, Character.valueOf('I'), Items.IRON_INGOT });
		
		//Car Tank
		GameRegistry.addRecipe(new ItemStack(ModItems.CAR_TANK, 1), new Object[] { "III", "ITI", "III",
				Character.valueOf('T'), ModBlocks.TANK, Character.valueOf('I'), Items.IRON_INGOT });
		
		//Windshield
		GameRegistry.addRecipe(new ItemStack(ModItems.WINDSHIELD, 1), new Object[] { "PPP", "PPP",
				Character.valueOf('P'), Blocks.GLASS_PANE});
		
		//Car Seat
		GameRegistry.addRecipe(new ItemStack(ModItems.CAR_SEAT, 1), new Object[] { "XWP", "WWP", "PPP",
				Character.valueOf('P'), Blocks.PLANKS, Character.valueOf('W'), Blocks.WOOL });
		
		//Car Engine
		GameRegistry.addRecipe(new ItemStack(ModItems.ENGINE_3_CYLINDER, 1), new Object[] { "FFF", "PPP", "BBB",
				Character.valueOf('F'), Items.FLINT, Character.valueOf('P'), ModItems.ENGINE_PISTON, 
				Character.valueOf('B'), Blocks.IRON_BLOCK });
		
		//Wheel
		GameRegistry.addRecipe(new ItemStack(ModItems.WOODEN_WHEEL, 1), new Object[] { "XPX", "PPP", "XPX",
				Character.valueOf('P'), Blocks.PLANKS});
		
		//Control unit
		GameRegistry.addRecipe(new ItemStack(ModItems.CONTROL_UNIT, 1), new Object[] { "IRI", "RSR", "IRI",
				Character.valueOf('I'), Items.IRON_INGOT, 
				Character.valueOf('R'), Items.REDSTONE, 
				Character.valueOf('S'), Blocks.SAND});
	
		//Iron Stick
		GameRegistry.addRecipe(new ItemStack(ModItems.IRON_STICK, 2), new Object[] { "I", "I",
				Character.valueOf('I'), Items.IRON_INGOT});
		OreDictionary.registerOre("stickIron", ModItems.IRON_STICK);
		
		//Engine Piston
		GameRegistry.addRecipe(new ItemStack(ModItems.ENGINE_PISTON, 1), new Object[] { "XXI", "XSX", "SXX",
				Character.valueOf('I'), Items.IRON_INGOT, Character.valueOf('S'), ModItems.IRON_STICK});
		
		//Body Part
		GameRegistry.addRecipe(new ItemStack(ModItems.CAR_BODY_PART_WOOD, 1, 0), new Object[] { "LPL", "LPL",
				Character.valueOf('L'), new ItemStack(Blocks.LOG, 1, 0), 
				Character.valueOf('P'), new ItemStack(Blocks.PLANKS, 1, 0)});
		
		GameRegistry.addRecipe(new ItemStack(ModItems.CAR_BODY_PART_WOOD, 1, 1), new Object[] { "LPL", "LPL",
				Character.valueOf('L'), new ItemStack(Blocks.LOG, 1, 1), 
				Character.valueOf('P'), new ItemStack(Blocks.PLANKS, 1, 1)});
		
		GameRegistry.addRecipe(new ItemStack(ModItems.CAR_BODY_PART_WOOD, 1, 2), new Object[] { "LPL", "LPL",
				Character.valueOf('L'), new ItemStack(Blocks.LOG, 1, 2), 
				Character.valueOf('P'), new ItemStack(Blocks.PLANKS, 1, 2)});
		
		GameRegistry.addRecipe(new ItemStack(ModItems.CAR_BODY_PART_WOOD, 1, 3), new Object[] { "LPL", "LPL",
				Character.valueOf('L'), new ItemStack(Blocks.LOG, 1, 3), 
				Character.valueOf('P'), new ItemStack(Blocks.PLANKS, 1, 3)});
		
		GameRegistry.addRecipe(new ItemStack(ModItems.CAR_BODY_PART_WOOD, 1, 4), new Object[] { "LPL", "LPL",
				Character.valueOf('L'), new ItemStack(Blocks.LOG2, 1, 0), 
				Character.valueOf('P'), new ItemStack(Blocks.PLANKS, 1, 4)});
		
		GameRegistry.addRecipe(new ItemStack(ModItems.CAR_BODY_PART_WOOD, 1, 5), new Object[] { "LPL", "LPL",
				Character.valueOf('L'), new ItemStack(Blocks.LOG2, 1, 1), 
				Character.valueOf('P'), new ItemStack(Blocks.PLANKS, 1, 5)});
		
		//Crash Barrier
		GameRegistry.addRecipe(new ItemStack(ModBlocks.CRASH_BARRIER, 32), new Object[] { "III", "III", "IXI",
				Character.valueOf('I'), Items.IRON_INGOT});
		
		//Fuel Station
		GameRegistry.addRecipe(new ItemStack(ModBlocks.FUEL_STATION, 1), new Object[] { "IBI", "ICI", "STS",
				Character.valueOf('I'), Items.IRON_INGOT,
				Character.valueOf('B'), Blocks.IRON_BLOCK,
				Character.valueOf('C'), ModItems.CONTROL_UNIT,
				Character.valueOf('T'), ModBlocks.TANK,
				Character.valueOf('S'), new ItemStack(Blocks.STONE_SLAB, 1, 0)});
		
		//Blast Furnace
		GameRegistry.addRecipe(new ItemStack(ModBlocks.BLAST_FURNACE, 1), new Object[] { "III", "IFI", "III",
				Character.valueOf('I'), Items.IRON_INGOT,
				Character.valueOf('F'), Blocks.FURNACE});
		
		//Oil Mill
		GameRegistry.addRecipe(new ItemStack(ModBlocks.OIL_MILL, 1), new Object[] { "III", "IPI", "III",
				Character.valueOf('I'), Items.IRON_INGOT,
				Character.valueOf('P'), Blocks.PISTON});
		
		//Backmix Reactor
		GameRegistry.addRecipe(new ItemStack(ModBlocks.BACKMIX_REACTOR, 1), new Object[] { "ICI", "ITI", "III",
				Character.valueOf('I'), Items.IRON_INGOT,
				Character.valueOf('T'), ModBlocks.TANK,
				Character.valueOf('C'), ModItems.CONTROL_UNIT});
		
		//Split Tank
		GameRegistry.addRecipe(new ItemStack(ModBlocks.SPLIT_TANK, 1), new Object[] { "III", "GTG", "GTG",
				Character.valueOf('I'), Items.IRON_INGOT,
				Character.valueOf('T'), ModBlocks.TANK,
				Character.valueOf('G'), Blocks.GLASS});
		
		//Generator
		GameRegistry.addRecipe(new ItemStack(ModBlocks.GENERATOR, 1), new Object[] { "ICI", "IEI", "ITI",
				Character.valueOf('I'), Items.IRON_INGOT,
				Character.valueOf('T'), ModBlocks.TANK,
				Character.valueOf('E'), ModItems.ENGINE_3_CYLINDER,
				Character.valueOf('C'), ModItems.CONTROL_UNIT});
		
		//Car Workshop part
		GameRegistry.addRecipe(new ItemStack(ModBlocks.CAR_WORKSHOP_OUTTER, 1), new Object[] { "III", "XIX", "III",
				Character.valueOf('I'), Items.IRON_INGOT});
		
		//Car Workshop
		GameRegistry.addRecipe(new ItemStack(ModBlocks.CAR_WORKSHOP, 1), new Object[] { "III", "XPX", "III",
				Character.valueOf('I'), Items.IRON_INGOT,
				Character.valueOf('P'), Blocks.PISTON});
		
		//Canister
		GameRegistry.addRecipe(new ItemStack(ModItems.CANISTER, 1), new Object[] { "IIX", "ITI", "III",
				Character.valueOf('I'), Items.IRON_INGOT,
				Character.valueOf('T'), ModBlocks.TANK});
		
		//Wrench
		GameRegistry.addRecipe(new ItemStack(ModItems.WRENCH, 1), new Object[] { "XIX", "XSI", "SXX",
				Character.valueOf('I'), Items.IRON_INGOT,
				Character.valueOf('S'), ModItems.IRON_STICK});
		OreDictionary.registerOre("wrench", ModItems.WRENCH);
		
		//Repair Kit
		GameRegistry.addRecipe(new ItemStack(ModItems.REPAIR_KIT, 1), new Object[] { "III", "SWH", "III",
				Character.valueOf('I'), Items.IRON_INGOT,
				Character.valueOf('W'), new ItemStack(ModItems.WRENCH, 1, 0),
				Character.valueOf('S'), new ItemStack(ModItems.SCREW_DRIVER, 1, 0),
				Character.valueOf('H'), new ItemStack(ModItems.HAMMER, 1, 0)});
		
		//Screw Driver
		GameRegistry.addRecipe(new ItemStack(ModItems.SCREW_DRIVER, 1), new Object[] { "S", "S", "I",
				Character.valueOf('I'), Items.IRON_INGOT,
				Character.valueOf('S'), ModItems.IRON_STICK});
		
		//Hammer
		GameRegistry.addRecipe(new ItemStack(ModItems.HAMMER, 1), new Object[] { "III", "XSX", "XSX",
				Character.valueOf('I'), Items.IRON_INGOT,
				Character.valueOf('S'), ModItems.IRON_STICK});
		
		//Cable insulator
		GameRegistry.addRecipe(new ItemStack(ModItems.CABLE_INSULATOR, 16), new Object[] { "WWW",
				Character.valueOf('W'), Blocks.WOOL});
		
		//Cable
		GameRegistry.addRecipe(new ItemStack(ModBlocks.CABLE, 8), new Object[] { "III", "RRR", "III",
				Character.valueOf('I'), ModItems.CABLE_INSULATOR,
				Character.valueOf('R'), Items.REDSTONE});
		
		//Fluid_pipe
		GameRegistry.addRecipe(new ItemStack(ModBlocks.FLUID_PIPE, 16), new Object[] { "III", "XXX", "III",
				Character.valueOf('I'), Items.IRON_INGOT});
		
		//Fluid extractor
		GameRegistry.addRecipe(new ItemStack(ModBlocks.FLUID_EXTRACTOR, 4), new Object[] { "IXX", "IPP", "IXX",
				Character.valueOf('I'), Items.IRON_INGOT,
				Character.valueOf('P'), ModBlocks.FLUID_PIPE});
		
		//Crank
		GameRegistry.addRecipe(new ItemStack(ModBlocks.CRANK, 1), new Object[] { "SS", "XS",
				Character.valueOf('S'), Items.STICK});
		
		//Dynamo
		GameRegistry.addRecipe(new ItemStack(ModBlocks.DYNAMO, 1), new Object[] { "ICI", "IXI", "III",
				Character.valueOf('I'), Items.IRON_INGOT,
				Character.valueOf('C'), ModItems.CONTROL_UNIT});
		
		
	}
	
}
