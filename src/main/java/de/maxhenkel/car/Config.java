package de.maxhenkel.car;

import java.io.File;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.car.registries.CarFluid;
import de.maxhenkel.car.registries.FuelStationFluid;
import de.maxhenkel.car.registries.GeneratorRecipe;
import de.maxhenkel.tools.FluidSelector;
import de.maxhenkel.tools.json.JSONArray;
import de.maxhenkel.tools.json.JSONObject;
import de.maxhenkel.tools.json.JsonConfig;
import net.minecraftforge.common.config.Configuration;

public class Config {

	public static File configFolder;
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
	
	public static int generatorEnergyStorage=30000;
	public static int generatorFluidStorage=3000;
	
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
	
	public static boolean tarRecipe=true;
	public static boolean painterRecipe=true;
	public static boolean tankRecipe=true;
	public static boolean carPartsRecipe=true;
	public static boolean crashBarrierRecipe=true;
	public static boolean backmixReactorRecipe=true;
	public static boolean blastFurnaceRecipe=true;
	public static boolean oilMillRecipe=true;
	public static boolean cableRecipe=true;
	public static boolean dynamoRecipe=true;
	public static boolean fluidExtractorRecipe=true;
	public static boolean fluidPipeRecipe=true;
	public static boolean fuelStationRecipe=true;
	public static boolean generatorRecipe=true;
	public static boolean splitTankRecipe=true;
	public static boolean repairKitRecipe=true;
	public static boolean canisterRecipe=true;
	public static boolean carWorkshopRecipe=true;
	public static boolean keyRecipe=true;
	
	public static boolean canolaSeedDrop=true;

	public static void init(File configFolder){
		Config.configFolder=configFolder;
		Config.config=new Configuration(new File(configFolder, "main.cfg"));
		
		initMain();
	}
	
	public static void postInit(){
		if(configFolder==null){
			return;
		}
		initGenerator();
		initFuelStation();
		initCar();
	}
	
	private static void initGenerator(){
		JsonConfig cfg=new JsonConfig(new File(configFolder, "generator.json"));
		JSONArray arr=new JSONArray();
		arr.put(new JSONObject().put("fluid", new FluidSelector(ModFluids.BIO_DIESEL).toString()).put("energy", 500));
		JSONArray fluids=cfg.getJsonArray("generator_fluids", arr);
		
		for(int i=0; i<fluids.length(); i++){
			JSONObject obj=fluids.getJSONObject(i);
			if(obj==null){
				continue;
			}
			
			FluidSelector sel=FluidSelector.fromString(obj.getString("fluid"));
			int amount=obj.getInt("energy");
			if(sel==null||amount<=0){
				continue;
			}
			
			GeneratorRecipe.REGISTRY.register(new GeneratorRecipe(sel, amount).setRegistryName(sel.toString()));
		}
		
	}
	
	private static void initCar(){
		JsonConfig cfg=new JsonConfig(new File(configFolder, "car.json"));
		JSONArray arr=new JSONArray();
		
		arr.put(new JSONObject().put("id", "car_wood").put("fluids", new JSONArray().put(new JSONObject().put("fluid", new FluidSelector(ModFluids.BIO_DIESEL).toString()).put("efficiency", 0.3))));
		arr.put(new JSONObject().put("id", "car_big_wood").put("fluids", new JSONArray().put(new JSONObject().put("fluid", new FluidSelector(ModFluids.BIO_DIESEL).toString()).put("efficiency", 0.2))));
		arr.put(new JSONObject().put("id", "car_transporter").put("fluids", new JSONArray().put(new JSONObject().put("fluid", new FluidSelector(ModFluids.BIO_DIESEL).toString()).put("efficiency", 0.3))));
		arr.put(new JSONObject().put("id", "car_sport").put("fluids", new JSONArray().put(new JSONObject().put("fluid", new FluidSelector(ModFluids.BIO_DIESEL).toString()).put("efficiency", 0.12))));
		
		JSONArray cars=cfg.getJsonArray("car_fluids", arr);
		
		for(int i=0; i<cars.length(); i++){
			JSONObject car=cars.getJSONObject(i);
			if(car==null){
				continue;
			}
			
			String carID=car.getString("id");
			
			if(carID==null){
				continue;
			}
			
			JSONArray fluids=car.getJSONArray("fluids");
			
			if(fluids==null){
				continue;
			}
			for(int j=0; j<fluids.length(); j++){
				JSONObject jo=fluids.getJSONObject(j);
				if(jo==null){
					continue;
				}
				FluidSelector sel=FluidSelector.fromString(jo.getString("fluid"));
				double efficiency=jo.getDouble("efficiency");
				if(sel==null||efficiency<=0.0){
					continue;
				}
				
				CarFluid.REGISTRY.register(new CarFluid(carID, sel, efficiency).setRegistryName(carID +"_" +sel.getFluid().getName()));
			}
		}
		
	}
	
	private static void initFuelStation(){
		JsonConfig cfg=new JsonConfig(new File(configFolder, "fuel_station.json"));
		JSONArray arr=new JSONArray();
		arr.put(ModFluids.BIO_DIESEL.getName());
		JSONArray fluids=cfg.getJsonArray("valid_fluids", arr);
		
		for(int i=0; i<fluids.length(); i++){
			String str=fluids.getString(i);
			if(str==null){
				continue;
			}
			
			FluidSelector sel=FluidSelector.fromString(str);
			if(sel==null){
				continue;
			}
			
			FuelStationFluid.REGISTRY.register(new FuelStationFluid(sel).setRegistryName(sel.toString()));
		}
		
	}
	
	private static void initMain(){
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
		
		dynamoRecipe=config.getBoolean("dynamo_recipe", "machines.dynamo", true, "");
		dynamoEnergyStorage=config.getInt("dynamo_energy_storage", "machines.dynamo", 1000, 100, Short.MAX_VALUE, "");
		dynamoEnergyGeneration=config.getInt("dynamo_energy_generation", "machines.dynamo", 25, 1, Short.MAX_VALUE, "");
		
		fluidExtractorDrainSpeed=config.getInt("fluid_extractor_drain_speed", "machines.fluid_extractor", 25, 5, Short.MAX_VALUE, "");
		
		//Fuel Station
		fuelStationTransferRate=config.getInt("fuel_station_transfer_rate", "machines.fuel_station", 5, 1, Short.MAX_VALUE, "");
		
		//Generator
		generatorEnergyStorage=config.getInt("generator_energy_storage", "machines.generator", 30000, 1000, Short.MAX_VALUE, "");
		generatorFluidStorage=config.getInt("generator_fluid_storage", "machines.generator", 3000, 1000, Short.MAX_VALUE, "");
		
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
		
		carGroundSpeed=config.getBoolean("car_ground_speed", "car", false, "Whether the cars drive slower on non asphalt blocks");
		
		carStepHeight=config.getFloat("car_step_height", "car", 0.6F, 0.1F, 128F, "The height a car can drive up");

		//Recipes
		tarRecipe=config.getBoolean("tar_recipe", "recipes", true, "");
		painterRecipe=config.getBoolean("painter_recipe", "recipes", true, "");
		tankRecipe=config.getBoolean("painter_recipe", "recipes", true, "");
		carPartsRecipe=config.getBoolean("car_parts_recipe", "recipes", true, "");
		crashBarrierRecipe=config.getBoolean("crash_barrier_recipe", "recipes", true, "");
		backmixReactorRecipe=config.getBoolean("backmix_reactor_recipe", "recipes", true, "");
		blastFurnaceRecipe=config.getBoolean("blast_furnace_recipe", "recipes", true, "");
		oilMillRecipe=config.getBoolean("oil_mill_recipe", "recipes", true, "");
		cableRecipe=config.getBoolean("cable_recipe", "recipes", true, "");
		dynamoRecipe=config.getBoolean("dynamo_recipe", "recipes", true, "");
		fluidExtractorRecipe=config.getBoolean("fluid_extractor_recipe", "recipes", true, "");
		fluidPipeRecipe=config.getBoolean("fluid_pipe_recipe", "recipes", true, "");
		fuelStationRecipe=config.getBoolean("fuel_station_recipe", "recipes", true, "");
		generatorRecipe=config.getBoolean("generator_recipe", "recipes", true, "");
		splitTankRecipe=config.getBoolean("split_tank_recipe", "recipes", true, "");
		repairKitRecipe=config.getBoolean("repair_kit_recipe", "recipes", true, "");
		canisterRecipe=config.getBoolean("canister_recipe", "recipes", true, "");
		carWorkshopRecipe=config.getBoolean("car_workshop_recipe", "recipes", true, "");
		keyRecipe=config.getBoolean("key_clone_recipe", "recipes", true, "");
		
		canolaSeedDrop=config.getBoolean("canola_seed_drop", "drops", true, "");
		
		config.save();
	}
	
}
