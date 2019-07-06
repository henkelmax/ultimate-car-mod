package de.maxhenkel.car;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {

    public static ForgeConfigSpec.IntValue backmixReactorEnergyStorage;
    public static ForgeConfigSpec.IntValue backmixReactorEnergyUsage;
    public static ForgeConfigSpec.IntValue backmixReactorFluidStorage;
    public static ForgeConfigSpec.IntValue backmixReactorGeneratingTime;
    public static ForgeConfigSpec.IntValue backmixReactorMixGeneration;
    public static ForgeConfigSpec.IntValue backmixReactorMethanolUsage;
    public static ForgeConfigSpec.IntValue backmixReactorCanolaUsage;

    public static ForgeConfigSpec.IntValue blastFurnaceEnergyStorage;
    public static ForgeConfigSpec.IntValue blastFurnaceGeneratingTime;
    public static ForgeConfigSpec.IntValue blastFurnaceFluidStorage;
    public static ForgeConfigSpec.IntValue blastFurnaceEnergyUsage;
    public static ForgeConfigSpec.IntValue blastFurnaceFluidGeneration;

    public static ForgeConfigSpec.IntValue oilMillEnergyStorage;
    public static ForgeConfigSpec.IntValue oilMillGeneratingTime;
    public static ForgeConfigSpec.IntValue oilMillFluidStorage;
    public static ForgeConfigSpec.IntValue oilMillEnergyUsage;
    public static ForgeConfigSpec.IntValue oilMillFluidGeneration;

    public static ForgeConfigSpec.IntValue cableTransferRate;

    public static ForgeConfigSpec.IntValue dynamoEnergyStorage;
    public static ForgeConfigSpec.IntValue dynamoEnergyGeneration;

    public static ForgeConfigSpec.IntValue fluidExtractorDrainSpeed;

    public static ForgeConfigSpec.IntValue fuelStationTransferRate;

    public static ForgeConfigSpec.IntValue generatorEnergyStorage;
    public static ForgeConfigSpec.IntValue generatorFluidStorage;
    public static ForgeConfigSpec.IntValue generatorEnergyGeneration;

    public static ForgeConfigSpec.IntValue splitTankFluidStorage;
    public static ForgeConfigSpec.IntValue splitTankGeneratingTime;
    public static ForgeConfigSpec.IntValue splitTankMixUsage;
    public static ForgeConfigSpec.IntValue splitTankGlycerinGeneration;
    public static ForgeConfigSpec.IntValue splitTankBioDieselGeneration;

    public static ForgeConfigSpec.DoubleValue repairKitRepairAmount;

    public static ForgeConfigSpec.IntValue canisterMaxFuel;

    public static ForgeConfigSpec.DoubleValue carVolume;

    public static ForgeConfigSpec.BooleanValue thirdPersonEnter;
    public static ForgeConfigSpec.BooleanValue carGroundSpeed;

    //public static Block[] carDriveBlocks = new Block[0];
    //private static String[] carDriveBlocksStr = new String[0];

    public static ForgeConfigSpec.BooleanValue collideWithEntities;
    public static ForgeConfigSpec.BooleanValue damageEntities;
    public static ForgeConfigSpec.BooleanValue hornFlee;
    public static ForgeConfigSpec.BooleanValue useBattery;
    public static ForgeConfigSpec.BooleanValue tempInFarenheit;

    public static float engine6CylinderFuelEfficiency = 0.25F;
    public static float engine3CylinderFuelEfficiency = 0.6F;

    public static float engine6CylinderAcceleration = 0.04F;
    public static float engine3CylinderAcceleration = 0.032F;

    public static float engine6CylinderMaxSpeed = 0.65F;
    public static float engine3CylinderMaxSpeed = 0.5F;

    public static float engine6CylinderMaxReverseSpeed = 0.2F;
    public static float engine3CylinderMaxReverseSpeed = 0.2F;

    public static float bodyBigWoodFuelEfficiency = 0.7F;
    public static float bodyBigWoodAcceleration = 0.95F;
    public static float bodyBigWoodMaxSpeed = 0.85F;

    public static float bodyWoodFuelEfficiency = 0.8F;
    public static float bodyWoodAcceleration = 1F;
    public static float bodyWoodMaxSpeed = 0.9F;

    public static float bodySportFuelEfficiency = 0.9F;
    public static float bodySportAcceleration = 1F;
    public static float bodySportMaxSpeed = 1F;

    public static float bodySUVFuelEfficiency = 0.6F;
    public static float bodySUVAcceleration = 0.8F;
    public static float bodySUVMaxSpeed = 0.7F;

    public static float bodyTransporterFuelEfficiency = 0.6F;
    public static float bodyTransporterAcceleration = 0.8F;
    public static float bodyTransporterMaxSpeed = 0.765F;

    /*
    public static ForgeConfigSpec.DoubleValue engine6CylinderFuelEfficiency;// = 0.25F;
    public static ForgeConfigSpec.DoubleValue engine3CylinderFuelEfficiency;// = 0.6F;

    public static ForgeConfigSpec.DoubleValue engine6CylinderAcceleration;// = 0.04F;
    public static ForgeConfigSpec.DoubleValue engine3CylinderAcceleration;// = 0.032F;

    public static ForgeConfigSpec.DoubleValue engine6CylinderMaxSpeed;// = 0.65F;
    public static ForgeConfigSpec.DoubleValue engine3CylinderMaxSpeed;// = 0.5F;

    public static ForgeConfigSpec.DoubleValue engine6CylinderMaxReverseSpeed;// = 0.2F;
    public static ForgeConfigSpec.DoubleValue engine3CylinderMaxReverseSpeed;// = 0.2F;

    public static ForgeConfigSpec.DoubleValue bodyBigWoodFuelEfficiency;// = 0.7F;
    public static ForgeConfigSpec.DoubleValue bodyBigWoodAcceleration;// = 0.95F;
    public static ForgeConfigSpec.DoubleValue bodyBigWoodMaxSpeed;// = 0.85F;

    public static ForgeConfigSpec.DoubleValue bodyWoodFuelEfficiency;// = 0.8F;
    public static ForgeConfigSpec.DoubleValue bodyWoodAcceleration;// = 1F;
    public static ForgeConfigSpec.DoubleValue bodyWoodMaxSpeed;// = 0.9F;

    public static ForgeConfigSpec.DoubleValue bodySportFuelEfficiency;// = 0.9F;
    public static ForgeConfigSpec.DoubleValue bodySportAcceleration;// = 1F;
    public static ForgeConfigSpec.DoubleValue bodySportMaxSpeed;// = 1F;

    public static ForgeConfigSpec.DoubleValue bodySUVFuelEfficiency;// = 0.6F;
    public static ForgeConfigSpec.DoubleValue bodySUVAcceleration;// = 0.8F;
    public static ForgeConfigSpec.DoubleValue bodySUVMaxSpeed;// = 0.7F;

    public static ForgeConfigSpec.DoubleValue bodyTransporterFuelEfficiency;// = 0.6F;
    public static ForgeConfigSpec.DoubleValue bodyTransporterAcceleration;// = 0.8F;
    public static ForgeConfigSpec.DoubleValue bodyTransporterMaxSpeed;// = 0.765F;
    */

    public static final ServerConfig SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    public static final ClientConfig CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;

    static {
        Pair<ServerConfig, ForgeConfigSpec> specPairServer = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = specPairServer.getRight();
        SERVER = specPairServer.getLeft();

        Pair<ClientConfig, ForgeConfigSpec> specPairClient = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT_SPEC = specPairClient.getRight();
        CLIENT = specPairClient.getLeft();
    }

    public static class ServerConfig {

        public ServerConfig(ForgeConfigSpec.Builder builder) {
            backmixReactorEnergyStorage = builder.defineInRange("machines.backmix_reactor.energy_storage", 10000, 100, (int) Short.MAX_VALUE);
            backmixReactorEnergyUsage = builder.defineInRange("machines.backmix_reactor.energy_usage", 10, 1, (int) Short.MAX_VALUE);
            backmixReactorFluidStorage = builder.defineInRange("machines.backmix_reactor.fluid_storage", 3000, 1000, (int) Short.MAX_VALUE);
            backmixReactorGeneratingTime = builder.defineInRange("machines.backmix_reactor.generating_time", 200, 10, (int) Short.MAX_VALUE);
            backmixReactorMixGeneration = builder.defineInRange("machines.backmix_reactor.mix_generation", 100, 1, (int) Short.MAX_VALUE);
            backmixReactorMethanolUsage = builder.defineInRange("machines.backmix_reactor.methanol_usage", 50, 1, (int) Short.MAX_VALUE);
            backmixReactorCanolaUsage = builder.defineInRange("machines.backmix_reactor.canola_usage", 50, 1, (int) Short.MAX_VALUE);

            blastFurnaceEnergyStorage = builder.defineInRange("machines.blast_furnace.energy_storage", 10000, 1000, (int) Short.MAX_VALUE);
            blastFurnaceGeneratingTime = builder.defineInRange("machines.blast_furnace.generating_time", 200, 10, (int) Short.MAX_VALUE);
            blastFurnaceFluidStorage = builder.defineInRange("machines.blast_furnace.fluid_storage", 3000, 1000, (int) Short.MAX_VALUE);
            blastFurnaceEnergyUsage = builder.defineInRange("machines.blast_furnace.energy_usage", 10, 1, (int) Short.MAX_VALUE);
            blastFurnaceFluidGeneration = builder.defineInRange("machines.blast_furnace.fluid_generation", 100, 1, (int) Short.MAX_VALUE);

            oilMillEnergyStorage = builder.defineInRange("machines.oil_mill.energy_storage", 10000, 1000, (int) Short.MAX_VALUE);
            oilMillGeneratingTime = builder.defineInRange("machines.oil_mill.generating_time", 200, 10, (int) Short.MAX_VALUE);
            oilMillFluidStorage = builder.defineInRange("machines.oil_mill.fluid_storage", 3000, 1000, (int) Short.MAX_VALUE);
            oilMillEnergyUsage = builder.defineInRange("machines.oil_mill.energy_usage", 10, 1, (int) Short.MAX_VALUE);
            oilMillFluidGeneration = builder.defineInRange("machines.oil_mill.fluid_generation", 100, 1, (int) Short.MAX_VALUE);

            cableTransferRate = builder.defineInRange("machines.cable.transfer_rate", 256, 64, (int) Short.MAX_VALUE);

            dynamoEnergyStorage = builder.defineInRange("machines.dynamo.energy_storage", 1000, 100, (int) Short.MAX_VALUE);
            dynamoEnergyGeneration = builder.defineInRange("machines.dynamo.energy_generation", 25, 1, (int) Short.MAX_VALUE);

            fluidExtractorDrainSpeed = builder.defineInRange("machines.fluid_extractor.drain_speed", 25, 5, (int) Short.MAX_VALUE);

            fuelStationTransferRate = builder.defineInRange("machines.fuel_station.transfer_rate", 5, 1, (int) Short.MAX_VALUE);

            generatorEnergyStorage = builder.defineInRange("machines.generator.energy_storage", 30000, 1000, (int) Short.MAX_VALUE);
            generatorFluidStorage = builder.defineInRange("machines.generator.fluid_storage", 3000, 1000, (int) Short.MAX_VALUE);
            generatorEnergyGeneration = builder.defineInRange("machines.generator.energy_generation", 500, 1, (int) Short.MAX_VALUE);

            splitTankFluidStorage = builder.defineInRange("machines.split_tank.fluid_storage", 3000, 1000, (int) Short.MAX_VALUE);
            splitTankGeneratingTime = builder.defineInRange("machines.split_tank.generating_time", 800, 10, (int) Short.MAX_VALUE);
            splitTankMixUsage = builder.defineInRange("machines.split_tank.mix_usage", 100, 1, (int) Short.MAX_VALUE);
            splitTankGlycerinGeneration = builder.defineInRange("machines.split_tank.glycerin_generation", 10, 1, (int) Short.MAX_VALUE);
            splitTankBioDieselGeneration = builder.defineInRange("machines.split_tank.bio_diesel_generation", 100, 1, (int) Short.MAX_VALUE);

            repairKitRepairAmount = builder.defineInRange("items.repair_kit.repair_amount", 5F, 0.1F, 100F);

            canisterMaxFuel = builder.defineInRange("items.canister.max_fuel", 100, 1, 1000);

            carVolume = builder.defineInRange("car.car_volume", 0.1F, 0F, 1F);

            collideWithEntities = builder.comment("Whether the cars should collide with other entities (except cars)").define("car.collide_with_entities", false);
            damageEntities = builder.comment("Whether the cars should damage other entities on collision").define("car.damage_entities", true);
            hornFlee = builder.comment("Whether animals flee from the car when the horn is activted").define("car.horn_flee", true);
            useBattery = builder.comment("True if starting the car should use battery").define("car.use_battery", true);

            carGroundSpeed = builder.comment("Whether the cars drive slower on non road blocks").define("car.road_blocks_enabled", false);
            /*engine6CylinderFuelEfficiency = builder.defineInRange("car_parts.car_6_cylinder_fuel_efficiency", 0.25F, 0.001F, 10F);
            engine3CylinderFuelEfficiency = builder.defineInRange("car_parts.car_3_cylinder_fuel_efficiency", 0.6F, 0.001F, 10F);
            engine6CylinderAcceleration = builder.defineInRange("car_parts.car_6_cylinder_acceleration", 0.04F, 0.001F, 10F);
            engine3CylinderAcceleration = builder.defineInRange("car_parts.car_3_cylinder_acceleration", 0.032F, 0.001F, 10F);
            engine6CylinderMaxSpeed = builder.defineInRange("car_parts.car_6_cylinder_max_speed", 0.65F, 0.001F, 10F);
            engine3CylinderMaxSpeed = builder.defineInRange("car_parts.car_3_cylinder_max_speed", 0.5F, 0.001F, 10F);
            engine6CylinderMaxReverseSpeed = builder.defineInRange("car_parts.car_6_cylinder_max_reverse_speed", 0.2F, 0.001F, 10F);
            engine3CylinderMaxReverseSpeed = builder.defineInRange("car_parts.car_3_cylinder_max_reverse_speed", 0.2F, 0.001F, 10F);
            bodyBigWoodFuelEfficiency = builder.defineInRange("car_parts.", 0.7F, 0.001F, 10F);
            bodyBigWoodAcceleration = builder.defineInRange("car_parts.car_body_big_wood_acceleration", 0.95F, 0.001F, 10F);
            bodyBigWoodMaxSpeed = builder.defineInRange("car_parts.car_body_big_wood_max_speed", 0.85F, 0.001F, 10F);
            bodyWoodFuelEfficiency = builder.defineInRange("car_parts.car_body_wood_fuel_efficiency", 0.8F, 0.001F, 10F);
            bodyWoodAcceleration = builder.defineInRange("car_parts.car_body_wood_acceleration", 1F, 0.001F, 10F);
            bodyWoodMaxSpeed = builder.defineInRange("car_parts.car_body_wood_max_speed", 0.9F, 0.001F, 10F);
            bodySportFuelEfficiency = builder.defineInRange("car_parts.car_body_sport_fuel_efficiency", 0.9F, 0.001F, 10F);
            bodySportAcceleration = builder.defineInRange("car_parts.car_body_sport_acceleration", 1F, 0.001F, 10F);
            bodySportMaxSpeed = builder.defineInRange("car_parts.car_body_sport_max_speed", 1F, 0.001F, 10F);
            bodySUVFuelEfficiency = builder.defineInRange("car_parts.car_body_suv_fuel_efficiency", 0.6F, 0.001F, 10F);
            bodySUVAcceleration = builder.defineInRange("car_parts.car_body_suv_acceleration", 0.8F, 0.001F, 10F);
            bodySUVMaxSpeed = builder.defineInRange("car_parts.car_body_suv_max_speed", 0.7F, 0.001F, 10F);
            bodyTransporterFuelEfficiency = builder.defineInRange("car_parts.car_body_transporter_fuel_efficiency", 0.6F, 0.001F, 10F);
            bodyTransporterAcceleration = builder.defineInRange("car_parts.car_body_transporter_acceleration", 0.8F, 0.001F, 10F);
            bodyTransporterMaxSpeed = builder.defineInRange("car_parts.car_body_transporter_max_speed", 0.765F, 0.001F, 10F);
            */
        }
    }

    public static class ClientConfig {
        public ClientConfig(ForgeConfigSpec.Builder builder) {
            thirdPersonEnter = builder.define("car.third_person_when_enter_car", true);
            tempInFarenheit = builder.comment("True if the car temperature should be displayed in farenheit").define("car.temp_farenheit", false);
        }
    }

}
