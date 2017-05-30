package de.maxhenkel.car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.maxhenkel.car.fluids.ModFluids;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class Config {

	public static Configuration config;
	
	public static int backmixReactorEnergyStorage=10000;
	public static int backmixReactorEnergyUsage=10;
	public static int backmixReactorFluidStorage=3000;
	public static int backmixReactorGeneratingTime=200;
	public static int backmixReactorMixGeneration=100;
	public static int backmixReactorMethanolUsage=50;
	public static int backmixReactorCanolaUsage=50;
	
	public static int blastFurnaceEnergyStorage=10000;
	public static int blastFurnaceGeneratingTime=200;
	public static int blastFurnaceFluidStorage=3000;
	public static int blastFurnaceEnergyUsage=10;
	public static int blastFurnaceFluidGeneration=100;
	
	public static int oilMillEnergyStorage=10000;
	public static int oilMillGeneratingTime=200;
	public static int oilMillFluidStorage=3000;
	public static int oilMillEnergyUsage=10;
	public static int oilMillFluidGeneration=100;
	
	public static int cableTransferRate=256;
	
	public static int dynamoEnergyStorage=1000;
	public static int dynamoEnergyGeneration=25;
	
	public static int fluidExtractorDrainSpeed=25;
	
	public static int fuelStationTransferRate=5;
	public static List<Fluid> validFuelStationFluids=new ArrayList<Fluid>();
	
	public static int generatorEnergyStorage=30000;
	public static int generatorFluidStorage=3000;
	public static Map<Fluid, Integer> generationFactors=new HashMap<Fluid, Integer>();
	
	public static int splitTankFluidStorage=3000;
	public static int splitTankGeneratingTime=800;
	public static int splitTankMixUsage=100;
	public static int splitTankGlycerinGeneration=10;
	public static int splitTankBioDieselGeneration=100;
	
	public static float repairKitRepairAmount=5F;
	
	public static int canisterMaxFuel=100;
	
	public static float carVolume=0.1F;
	
	public static boolean thirdPersonEnter;
	public static boolean carGroundSpeed;
	public static float carStepHeight;
	public static Fluid carFuel;
	
	public static boolean checkUpdates;
	
	public static void init(Configuration config){
		Config.config=config;
		
		backmixReactorEnergyStorage=config.getInt("backmix_reactor_energy_storage", "machines.backmix_reactor", 10000, 100, Short.MAX_VALUE, "");
		backmixReactorEnergyUsage=config.getInt("backmix_reactor_energy_usage", "machines.backmix_reactor", 10, 1, Short.MAX_VALUE, "");
		backmixReactorFluidStorage=config.getInt("backmix_reactor_fluid_storage", "machines.backmix_reactor", 3000, 1000, Short.MAX_VALUE, "");
		backmixReactorGeneratingTime=config.getInt("backmix_reactor_generating_time", "machines.backmix_reactor", 200, 10, Short.MAX_VALUE, "");
		backmixReactorMixGeneration=config.getInt("backmix_reactor_mix_generation", "machines.backmix_reactor", 100, 1, Short.MAX_VALUE, "");
		backmixReactorMethanolUsage=config.getInt("backmix_reactor_methanol_usage", "machines.backmix_reactor", 50, 1, Short.MAX_VALUE, "");
		backmixReactorCanolaUsage=config.getInt("backmix_reactor_canola_usage", "machines.backmix_reactor", 50, 1, Short.MAX_VALUE, "");
		
		blastFurnaceEnergyStorage=config.getInt("blast_furnace_energy_storage", "machines.blast_furnace", 10000, 1000, Short.MAX_VALUE, "");
		blastFurnaceGeneratingTime=config.getInt("blast_furnace_generating_time", "machines.blast_furnace", 200, 10, Short.MAX_VALUE, "");
		blastFurnaceFluidStorage=config.getInt("blast_furnace_fluid_storage", "machines.blast_furnace", 3000, 1000, Short.MAX_VALUE, "");
		blastFurnaceEnergyUsage=config.getInt("blast_furnace_energy_usage", "machines.blast_furnace", 10, 1, Short.MAX_VALUE, "");
		blastFurnaceFluidGeneration=config.getInt("blast_furnace_fluid_generation", "machines.blast_furnace", 100, 1, Short.MAX_VALUE, "");
		
		oilMillEnergyStorage=config.getInt("oil_mill_energy_storage", "machines.oil_mill", 10000, 1000, Short.MAX_VALUE, "");
		oilMillGeneratingTime=config.getInt("oil_mill_generating_time", "machines.oil_mill", 200, 10, Short.MAX_VALUE, "");
		oilMillFluidStorage=config.getInt("oil_mill_fluid_storage", "machines.oil_mill", 3000, 1000, Short.MAX_VALUE, "");
		oilMillEnergyUsage=config.getInt("oil_mill_energy_usage", "machines.oil_mill", 10, 1, Short.MAX_VALUE, "");
		oilMillFluidGeneration=config.getInt("oil_mill_fluid_generation", "machines.oil_mill", 100, 1, Short.MAX_VALUE, "");
		
		cableTransferRate=config.getInt("cable_transfer_rate", "machines.cable", 256, 64, Short.MAX_VALUE, "");
		
		dynamoEnergyStorage=config.getInt("dynamo_energy_storage", "machines.dynamo", 1000, 100, Short.MAX_VALUE, "");
		dynamoEnergyGeneration=config.getInt("dynamo_energy_generation", "machines.dynamo", 25, 1, Short.MAX_VALUE, "");
		
		fluidExtractorDrainSpeed=config.getInt("fluid_extractor_drain_speed", "machines.fluid_extractor", 25, 5, Short.MAX_VALUE, "");
		
		//Fuel Station
		fuelStationTransferRate=config.getInt("fuel_station_transfer_rate", "machines.fuel_station", 5, 1, Short.MAX_VALUE, "");
		String[] fluids=config.getStringList("fuel_station_valid_fluids", "machines.fuel_station", new String[]{FluidRegistry.getFluidName(ModFluids.BIO_DIESEL)}, "");
		List<Fluid> fluidList=new ArrayList<Fluid>();
		for(String f:fluids){
			Fluid fluid=FluidRegistry.getFluid(f);
			if(fluid!=null){
				fluidList.add(fluid);
			}
		}
		validFuelStationFluids=fluidList;
		
		//Generator
		generatorEnergyStorage=config.getInt("generator_energy_storage", "machines.generator", 30000, 1000, Short.MAX_VALUE, "");
		generatorFluidStorage=config.getInt("generator_fluid_storage", "machines.generator", 3000, 1000, Short.MAX_VALUE, "");
		
		Map<String, String> def=new HashMap<String, String>();
		def.put(FluidRegistry.getFluidName(ModFluids.BIO_DIESEL), "500");
		
		Map<String, String> map=getMap(config, "generator_fluid_generation_factors", "machines.generator", "", def);
		Map<Fluid, Integer> gens=new HashMap<Fluid, Integer>();
		for(Entry<String, String> entry:map.entrySet()){
			Fluid f=FluidRegistry.getFluid(entry.getKey());
			if(f!=null){
				try{
					int i=Integer.parseInt(entry.getValue());
					gens.put(f, i);
				}catch(NumberFormatException e){
					e.printStackTrace();
				}
			}
		}
		generationFactors=gens;
		
		//Split Tank
		splitTankFluidStorage=config.getInt("split_tank_fluid_storage", "machines.split_tank", 3000, 1000, Short.MAX_VALUE, "");
		splitTankGeneratingTime=config.getInt("split_tank_generating_time", "machines.split_tank", 800, 10, Short.MAX_VALUE, "");
		splitTankMixUsage=config.getInt("split_tank_mix_usage", "machines.split_tank", 100, 1, Short.MAX_VALUE, "");
		splitTankGlycerinGeneration=config.getInt("split_tank_glycerin_generation", "machines.split_tank", 10, 1, Short.MAX_VALUE, "");
		splitTankBioDieselGeneration=config.getInt("split_tank_bio_diesel_generation", "machines.split_tank", 100, 1, Short.MAX_VALUE, "");

		//Repair kit
		repairKitRepairAmount=config.getFloat("repair_kit_repair_amount", "items.repair_kit", 5F, 0.1F, 100F, "");
		
		//Canister
		canisterMaxFuel=config.getInt("canister_max_fuel", "items.canister", 100, 1, 1000, "");
		
		//Car volume
		carVolume=config.getFloat("car_volume", "car", 0.1F, 0F, 1F, "");
		
		thirdPersonEnter=config.getBoolean("third_person_when_enter_car", "car", true, "");
		
		carGroundSpeed=config.getBoolean("car_ground_speed", "car", false, "");
		
		carStepHeight=config.getFloat("car_step_height", "car", 0.6F, 0.1F, 128F, "The height a car can drive up");
		
		String carFuelStr=config.getString("car_fuel", "car", "bio_diesel", "The fuel all cars drive with");
		
		Fluid f=FluidRegistry.getFluid(carFuelStr);
		if(f==null){
			f=ModFluids.BIO_DIESEL;
		}
		
		carFuel=f;
		
		checkUpdates=config.getBoolean("check_updates", "general", true, "Check for updates");
		
		config.save();
	}
	
	public static Map<String, String> getMap(Configuration config, String name, String category, String comment, Map<String, String> def){
		try{
			String str=config.getString(name, category, getJson(def).toString(), comment);
			JsonParser parser=new JsonParser();
			
			JsonObject obj=parser.parse(str).getAsJsonObject();
			
			return getMap(obj);
		}catch(Exception e){
			e.printStackTrace();
			return def;
		}
	}
	
	public static JsonObject getJson(Map<String, String> map){
		JsonObject obj=new JsonObject();
		for(Entry<String, String> entry:map.entrySet()){
			if(entry.getKey()!=null&&entry.getValue()!=null){
				obj.addProperty(entry.getKey(), entry.getValue());
			}
		}
		return obj;
	}
	
	public static Map<String, String> getMap(JsonObject obj){
		Map<String, String> map=new HashMap<String, String>();
		
		for(Entry<String, JsonElement> entry:obj.entrySet()){
			map.put(entry.getKey(), entry.getValue().getAsString());
		}
		
		return map;
	}
	
}
