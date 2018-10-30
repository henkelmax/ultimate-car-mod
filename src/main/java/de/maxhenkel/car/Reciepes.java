package de.maxhenkel.car;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class Reciepes {

    public static void registerReciepes() {

        //Tar
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "tar_wool"), null,
                new ItemStack(ModBlocks.TAR, 8),
                "XXX", "XWX", "XXX",
                'X', new ItemStack(Blocks.COBBLESTONE),
                'W', new ItemStack(Blocks.WOOL, 1, 15)
        );

        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "tar_coal"), null,
                new ItemStack(ModBlocks.TAR, 8),
                "XXX", "XWX", "XXX",
                'X', new ItemStack(Blocks.COBBLESTONE),
                'W', new ItemStack(Items.COAL, 1, 0)
        );

        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "tar_charcoal"), null,
                new ItemStack(ModBlocks.TAR, 8),
                "XXX", "XWX", "XXX",
                'X', new ItemStack(Blocks.COBBLESTONE),
                'W', new ItemStack(Items.COAL, 1, 1)
        );

        //Tar Slab
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "tar_slab"), null,
                new ItemStack(ModBlocks.TAR_SLAB, 6),
                "XXX",
                'X', new ItemStack(ModBlocks.TAR)
        );

        //Tar Slope
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "tar_slope"), null,
                new ItemStack(ModBlocks.TAR_SLOPE, 6),
                "T  ", "TT ", "TTT",
                'T', new ItemStack(ModBlocks.TAR)
        );

        //Lower Tar Slope
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "tar_slope_flat_lower"), null,
                new ItemStack(ModBlocks.TAR_SLOPE_FLAT_LOWER, 4),
                "T  ", "TTT",
                'T', new ItemStack(ModBlocks.TAR)
        );

        //Upper Tar Slope
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "tar_slope_flat_upper"), null,
                new ItemStack(ModBlocks.TAR_SLOPE_FLAT_UPPER, 4),
                "T ", "TT", "TT",
                'T', new ItemStack(ModBlocks.TAR)
        );

        //Painter White
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "painter_white"), null,
                new ItemStack(ModItems.PAINTER, 1),
                " B ", " SB", "S  ",
                'S', new ItemStack(Items.STICK),
                'B', new ItemStack(Items.DYE, 1, 15)
        );

        //Painter Yellow
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "painter_yellow"), null,
                new ItemStack(ModItems.PAINTER_YELLOW, 1),
                " B ", " SB", "S  ",
                'S', new ItemStack(Items.STICK),
                'B', new ItemStack(Items.DYE, 1, 11)
        );

        //Tank
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "tank"), null,
                new ItemStack(ModBlocks.TANK, 1),
                "IGI", "G G", "IGI",
                'G', new ItemStack(Blocks.GLASS),
                'I', new ItemStack(Items.IRON_INGOT)
        );

        //Iron Stick
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "iron_stick"), null,
                new ItemStack(ModItems.IRON_STICK, 2),
                "I", "I",
                'I', new ItemStack(Items.IRON_INGOT)
        );
        OreDictionary.registerOre("stickIron", new ItemStack(ModItems.IRON_STICK));

        //Engine Piston
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "engine_piston"), null,
                new ItemStack(ModItems.ENGINE_PISTON, 1),
                "  I", " S ", "S  ",
                'I', new ItemStack(Items.IRON_INGOT),
                'S', new ItemStack(ModItems.IRON_STICK)
        );

        //Crash Barrier
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "crash_barrier"), null,
                new ItemStack(ModBlocks.CRASH_BARRIER, 32),
                "III", "III", "I I",
                'I', new ItemStack(Items.IRON_INGOT)
        );

        //Fuel Station
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "fuel_station"), null,
                new ItemStack(ModBlocks.FUEL_STATION, 1),
                "IBI", "ICI", "STS",
                'I', new ItemStack(Items.IRON_INGOT),
                'B', new ItemStack(Blocks.IRON_BLOCK),
                'C', new ItemStack(Items.REDSTONE),
                'T', new ItemStack(ModBlocks.TANK),
                'S', new ItemStack(Blocks.STONE_SLAB, 1, 0)
        );

        //Blast Furnace
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "blast_furnace"), null,
                new ItemStack(ModBlocks.BLAST_FURNACE, 1),
                "III", "IFI", "III",
                'I', new ItemStack(Items.IRON_INGOT),
                'F', new ItemStack(Blocks.FURNACE)
        );

        //Oil Mill
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "oil_mill"), null,
                new ItemStack(ModBlocks.OIL_MILL, 1),
                "III", "IPI", "III",
                'I', new ItemStack(Items.IRON_INGOT),
                'P', new ItemStack(Blocks.PISTON)
        );

        //Backmix Reactor
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "backmix_reactor"), null,
                new ItemStack(ModBlocks.BACKMIX_REACTOR, 1),
                "ICI", "ITI", "III",
                'I', new ItemStack(Items.IRON_INGOT),
                'T', new ItemStack(ModBlocks.TANK),
                'C', new ItemStack(Items.REDSTONE)
        );

        //Split Tank
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "split_tank"), null,
                new ItemStack(ModBlocks.SPLIT_TANK, 1),
                "III", "GTG", "GTG",
                'I', new ItemStack(Items.IRON_INGOT),
                'T', new ItemStack(ModBlocks.TANK),
                'G', new ItemStack(Blocks.GLASS)
        );

        //Generator
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "GENERATOR"), null,
                new ItemStack(ModBlocks.GENERATOR, 1),
                "ICI", "IEI", "ITI",
                'I', new ItemStack(Items.IRON_INGOT),
                'T', new ItemStack(ModBlocks.TANK),
                'E', new ItemStack(ModItems.ENGINE_3_CYLINDER),
                'C', new ItemStack(Items.REDSTONE)
        );

        //Car Workshop part
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "car_workshop_outter"), null,
                new ItemStack(ModBlocks.CAR_WORKSHOP_OUTTER, 1),
                "III", " I ", "III",
                'I', new ItemStack(Items.IRON_INGOT)
        );

        //Car Workshop
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "car_workshop"), null,
                new ItemStack(ModBlocks.CAR_WORKSHOP, 1),
                "III", " P ", "III",
                'I', new ItemStack(Items.IRON_INGOT),
                'P', new ItemStack(Blocks.PISTON)
        );

        //Wrench
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "wrench"), null,
                new ItemStack(ModItems.WRENCH, 1), " I ", " SI", "S  ",
                'I', new ItemStack(Items.IRON_INGOT),
                'S', new ItemStack(ModItems.IRON_STICK)
        );
        OreDictionary.registerOre("wrench", new ItemStack(ModItems.WRENCH));

        //Screw Driver
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "screw_driver"), null,
                new ItemStack(ModItems.SCREW_DRIVER, 1),
                "S", "S", "I",
                'I', new ItemStack(Items.IRON_INGOT),
                'S', new ItemStack(ModItems.IRON_STICK)
        );

        //Hammer
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "hammer"), null,
                new ItemStack(ModItems.HAMMER, 1),
                "III", " S ", " S ",
                'I', new ItemStack(Items.IRON_INGOT),
                'S', new ItemStack(ModItems.IRON_STICK)
        );

        //Canister
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "canister"), null,
                new ItemStack(ModItems.CANISTER, 1),
                "II ", "ITI", "III",
                'I', new ItemStack(Items.IRON_INGOT),
                'T', new ItemStack(ModBlocks.TANK)
        );

        //Repair Kit
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "repair_kit"), null,
                new ItemStack(ModItems.REPAIR_KIT, 1),
                "III", "SWH", "III",
                'I', new ItemStack(Items.IRON_INGOT),
                'W', new ItemStack(ModItems.WRENCH, 1, 0),
                'S', new ItemStack(ModItems.SCREW_DRIVER, 1, 0),
                'H', new ItemStack(ModItems.HAMMER, 1, 0)
        );

        //Battery
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "battery"), null,
                new ItemStack(ModItems.BATTERY, 1, 500),
                "S S", "III", "III",
                'I', new ItemStack(Items.IRON_INGOT),
                'S', new ItemStack(ModItems.IRON_STICK)
        );

        //Cable insulator
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "cable_insulator"), null,
                new ItemStack(ModItems.CABLE_INSULATOR, 16),
                "WWW",
                'W', Blocks.WOOL
        );

        //Cable
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "cable"), null,
                new ItemStack(ModBlocks.CABLE, 8),
                "III", "RRR", "III",
                'I', new ItemStack(ModItems.CABLE_INSULATOR),
                'R', new ItemStack(Items.REDSTONE)
        );

        //Fluid extractor
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "fluid_extractor"), null,
                new ItemStack(ModBlocks.FLUID_EXTRACTOR, 4),
                "I  ", "IPP", "I  ",
                'I', new ItemStack(Items.IRON_INGOT),
                'P', new ItemStack(ModBlocks.FLUID_PIPE)
        );

        //Fluid_pipe
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "fluid_pipe"), null,
                new ItemStack(ModBlocks.FLUID_PIPE, 16),
                "III", "   ", "III",
                'I', new ItemStack(Items.IRON_INGOT)
        );

        //Crank
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "crank"), null,
                new ItemStack(ModBlocks.CRANK, 1),
                "SS", " S",
                'S', new ItemStack(Items.STICK)
        );

        //Dynamo
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "dynamo"), null,
                new ItemStack(ModBlocks.DYNAMO, 1),
                "ICI", "I I", "III",
                'I', new ItemStack(Items.IRON_INGOT),
                'C', new ItemStack(Items.REDSTONE)
        );

        //Sign
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "sign"), null,
                new ItemStack(ModBlocks.SIGN, 3),
                "III", "III", " S ",
                'I', new ItemStack(Items.IRON_INGOT),
                'S', new ItemStack(ModItems.IRON_STICK)
        );

        //Signpost
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "sign_post"), null,
                new ItemStack(ModBlocks.SIGN_POST, 1),
                "I", "I",
                'I', new ItemStack(ModItems.IRON_STICK)
        );

        //Number Plate
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "number_plate"), null,
                new ItemStack(ModItems.LICENSE_PLATE, 4),
                "III", "ISI", "III",
                'S', new ItemStack(ModItems.IRON_STICK),
                'I', new ItemStack(Items.IRON_INGOT)
        );

        OreDictionary.registerOre("cropCanola", new ItemStack(ModItems.CANOLA));


        //Car Tank
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "small_tank"), null,
                new ItemStack(ModItems.SMALL_TANK, 1),
                "III", "ITI", "III",
                'T', new ItemStack(ModBlocks.TANK),
                'I', new ItemStack(Items.IRON_INGOT)
        );

        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "medium_tank"), null,
                new ItemStack(ModItems.MEDIUM_TANK, 1),
                "III", "TTT", "III",
                'T', new ItemStack(ModBlocks.TANK),
                'I', new ItemStack(Items.IRON_INGOT)
        );

        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "large_tank"), null,
                new ItemStack(ModItems.LARGE_TANK, 1),
                "III", "TTT", "III",
                'T', new ItemStack(ModBlocks.TANK),
                'I', new ItemStack(Blocks.IRON_BLOCK)
        );

        //Car Engines
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "engine_3_cylinder"), null,
                new ItemStack(ModItems.ENGINE_3_CYLINDER, 1),
                "FFF", "PPP", "BBB",
                'F', new ItemStack(Items.FLINT),
                'P', new ItemStack(ModItems.ENGINE_PISTON),
                'B', new ItemStack(Blocks.IRON_BLOCK)
        );

        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "engine_6_cylinder"), null,
                new ItemStack(ModItems.ENGINE_6_CYLINDER, 1),
                "III", "EIE", "III",
                'I', new ItemStack(Items.IRON_INGOT),
                'E', new ItemStack(ModItems.ENGINE_3_CYLINDER)
        );

        //Wheel
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "wheel"), null,
                new ItemStack(ModItems.WHEEL, 1),
                " W ", "WIW", " W ",
                'W', new ItemStack(Blocks.WOOL, 1, 15),
                'I', new ItemStack(Items.IRON_INGOT)
        );

        //Big Wheel
        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "big_wheel"), null,
                new ItemStack(ModItems.BIG_WHEEL, 1),
                "WWW", "WIW", "WWW",
                'W', new ItemStack(Blocks.WOOL, 1, 15),
                'I', new ItemStack(Items.IRON_INGOT)
        );

        for(EnumDyeColor color:EnumDyeColor.values()){
            GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, color.getName() +"_transporter_body"), null,
                    new ItemStack(ModItems.TRANSPORTER_BODIES[color.getMetadata()], 1),
                    "CC ", "CC ", "CCC",
                    'C', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, color.getMetadata())
            );
        }

        for(EnumDyeColor color:EnumDyeColor.values()){
            GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, color.getName() +"_suv_body"), null,
                    new ItemStack(ModItems.SUV_BODIES[color.getMetadata()], 1),
                    "CC ", "CCC", "CCC",
                    'C', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, color.getMetadata())
            );
        }

        for(EnumDyeColor color:EnumDyeColor.values()){
            GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, color.getName() +"_sport_body"), null,
                    new ItemStack(ModItems.SPORT_BODIES[color.getMetadata()], 1),
                    "   ", "C  ", "CCC",
                    'C', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, color.getMetadata())
            );
        }

        for(EnumDyeColor color:EnumDyeColor.values()){
            GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, color.getName() +"_container"), null,
                    new ItemStack(ModItems.CONTAINERS[color.getMetadata()], 1),
                    "CIC", "IHI", "CIC",
                    'C', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, color.getMetadata()),
                    'H', new ItemStack(Blocks.CHEST, 1),
                    'I', new ItemStack(Items.IRON_INGOT, 1)
            );
        }

        for(EnumDyeColor color:EnumDyeColor.values()){
            GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, color.getName() +"_tank_container"), null,
                    new ItemStack(ModItems.TANK_CONTAINERS[color.getMetadata()], 1),
                    "CTC", "TTT", "CTC",
                    'C', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, color.getMetadata()),
                    'T', new ItemStack(ModBlocks.TANK, 1)
            );
        }

        for(BlockPlanks.EnumType wood: BlockPlanks.EnumType.values()){
            GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, wood.getName() +"_wood_body"), null,
                    new ItemStack(ModItems.WOOD_BODIES[wood.getMetadata()], 1),
                    "   ", "WW ", "WWW",
                    'W', new ItemStack(Blocks.PLANKS, 1, wood.getMetadata())
            );
        }

        for(BlockPlanks.EnumType wood: BlockPlanks.EnumType.values()){
            GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, wood.getName() +"_big_wood_body"), null,
                    new ItemStack(ModItems.BIG_WOOD_BODIES[wood.getMetadata()], 1),
                    "WW ", "WW ", "WWW",
                    'W', new ItemStack(Blocks.PLANKS, 1, wood.getMetadata())
            );
        }

        for(BlockPlanks.EnumType wood: BlockPlanks.EnumType.values()){
            GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, wood.getName() +"_bumper"), null,
                    new ItemStack(ModItems.BUMPERS[wood.getMetadata()], 1),
                    " I ", "WWW", "   ",
                    'W', new ItemStack(Blocks.PLANKS, 1, wood.getMetadata()),
                    'I', new ItemStack(Items.IRON_INGOT, 1)
            );
        }

        for(BlockPlanks.EnumType wood: BlockPlanks.EnumType.values()){
            GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, wood.getName() +"_license_plate_holder"), null,
                    new ItemStack(ModItems.WOODEN_LICENSE_PLATE_HOLDERS[wood.getMetadata()], 1),
                    "   ", "WIW", "WWW",
                    'W', new ItemStack(Blocks.PLANKS, 1, wood.getMetadata()),
                    'I', new ItemStack(ModItems.IRON_STICK, 1)
            );
        }

        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "iron_license_plate_holder"), null,
                new ItemStack(ModItems.IRON_LICENSE_PLATE_HOLDER, 1),
                "   ", "WIW", "WWW",
                'W', new ItemStack(Items.IRON_INGOT, 1),
                'I', new ItemStack(ModItems.IRON_STICK, 1)
        );

        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "gold_license_plate_holder"), null,
                new ItemStack(ModItems.GOLD_LICENSE_PLATE_HOLDER, 1),
                "   ", "WIW", "WWW",
                'W', new ItemStack(Items.GOLD_INGOT, 1),
                'I', new ItemStack(ModItems.IRON_STICK, 1)
        );

        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "diamond_license_plate_holder"), null,
                new ItemStack(ModItems.DIAMOND_LICENSE_PLATE_HOLDER, 1),
                "   ", "WIW", "WWW",
                'W', new ItemStack(Items.DIAMOND, 1),
                'I', new ItemStack(ModItems.IRON_STICK, 1)
        );

        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "emerald_license_plate_holder"), null,
                new ItemStack(ModItems.EMERALD_LICENSE_PLATE_HOLDER, 1),
                "   ", "WIW", "WWW",
                'W', new ItemStack(Items.EMERALD, 1),
                'I', new ItemStack(ModItems.IRON_STICK, 1)
        );
    }

}
