package de.maxhenkel.car;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public static ForgeConfigSpec.IntValue gasStationTransferRate;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> gasStationValidFuels;

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
    public static ForgeConfigSpec.DoubleValue carOffroadSpeed;
    public static ForgeConfigSpec.DoubleValue carOnroadSpeed;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> carDriveBlocks;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> carValidFuels;

    public static ForgeConfigSpec.BooleanValue collideWithEntities;
    public static ForgeConfigSpec.BooleanValue damageEntities;
    public static ForgeConfigSpec.BooleanValue hornFlee;
    public static ForgeConfigSpec.BooleanValue useBattery;
    public static ForgeConfigSpec.BooleanValue tempInFarenheit;

    public static ForgeConfigSpec.DoubleValue engine6CylinderFuelEfficiency;
    public static ForgeConfigSpec.DoubleValue engine3CylinderFuelEfficiency;
    public static ForgeConfigSpec.DoubleValue engineTruckFuelEfficiency;

    public static ForgeConfigSpec.DoubleValue engine6CylinderAcceleration;
    public static ForgeConfigSpec.DoubleValue engine3CylinderAcceleration;
    public static ForgeConfigSpec.DoubleValue engineTruckAcceleration;

    public static ForgeConfigSpec.DoubleValue engine6CylinderMaxSpeed;
    public static ForgeConfigSpec.DoubleValue engine3CylinderMaxSpeed;
    public static ForgeConfigSpec.DoubleValue engineTruckMaxSpeed;

    public static ForgeConfigSpec.DoubleValue engine6CylinderMaxReverseSpeed;
    public static ForgeConfigSpec.DoubleValue engine3CylinderMaxReverseSpeed;
    public static ForgeConfigSpec.DoubleValue engineTruckMaxReverseSpeed;

    public static ForgeConfigSpec.DoubleValue bodyBigWoodFuelEfficiency;
    public static ForgeConfigSpec.DoubleValue bodyBigWoodAcceleration;
    public static ForgeConfigSpec.DoubleValue bodyBigWoodMaxSpeed;

    public static ForgeConfigSpec.DoubleValue bodyWoodFuelEfficiency;
    public static ForgeConfigSpec.DoubleValue bodyWoodAcceleration;
    public static ForgeConfigSpec.DoubleValue bodyWoodMaxSpeed;

    public static ForgeConfigSpec.DoubleValue bodySportFuelEfficiency;
    public static ForgeConfigSpec.DoubleValue bodySportAcceleration;
    public static ForgeConfigSpec.DoubleValue bodySportMaxSpeed;

    public static ForgeConfigSpec.DoubleValue bodySUVFuelEfficiency;
    public static ForgeConfigSpec.DoubleValue bodySUVAcceleration;
    public static ForgeConfigSpec.DoubleValue bodySUVMaxSpeed;

    public static ForgeConfigSpec.DoubleValue bodyTransporterFuelEfficiency;
    public static ForgeConfigSpec.DoubleValue bodyTransporterAcceleration;
    public static ForgeConfigSpec.DoubleValue bodyTransporterMaxSpeed;

    public static List<Fluid> gasStationValidFuelList = new ArrayList<>();
    public static List<Block> carDriveBlockList = new ArrayList<>();
    public static List<Fluid> carValidFuelList = new ArrayList<>();

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

    public static void loadServer() {
        gasStationValidFuelList = gasStationValidFuels.get().stream().map(ResourceLocation::new).map(ForgeRegistries.FLUIDS::getValue).filter(Objects::nonNull).collect(Collectors.toList());
        carDriveBlockList = carDriveBlocks.get().stream().map(ResourceLocation::new).map(ForgeRegistries.BLOCKS::getValue).filter(Objects::nonNull).collect(Collectors.toList());
        carValidFuelList = carValidFuels.get().stream().map(ResourceLocation::new).map(ForgeRegistries.FLUIDS::getValue).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static void loadClient() {

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

            gasStationTransferRate = builder.defineInRange("machines.gas_station.transfer_rate", 5, 1, (int) Short.MAX_VALUE);
            gasStationValidFuels = builder.defineList("machines.gas_station.valid_fuels", Arrays.asList(ModFluids.BIO_DIESEL.getRegistryName().toString()), Objects::nonNull);

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

            collideWithEntities = builder.comment("Whether the cars should collide with other entities (except cars)").define("car.collide_with_entities", false);
            damageEntities = builder.comment("Whether the cars should damage other entities on collision").define("car.damage_entities", true);
            hornFlee = builder.comment("Whether animals flee from the car when the horn is activted").define("car.horn_flee", true);
            useBattery = builder.comment("True if starting the car should use battery").define("car.use_battery", true);

            carOffroadSpeed = builder.comment("The speed modifier for cars on non road blocks").defineInRange("car.offroad_speed_modifier", 1D, 0.001D, 10D);
            carOnroadSpeed = builder.comment("The speed modifier for cars on road blocks").defineInRange("car.onroad_speed_modifier", 1D, 0.001D, 10D);
            carDriveBlocks = builder.defineList("car.road_blocks.blocks", Arrays.asList(
                    ModBlocks.ASPHALT.getRegistryName().toString(),
                    ModBlocks.ASPHALT_SLAB.getRegistryName().toString(),
                    ModBlocks.ASPHALT_SLOPE.getRegistryName().toString(),
                    ModBlocks.ASPHALT_SLOPE_FLAT_LOWER.getRegistryName().toString(),
                    ModBlocks.ASPHALT_SLOPE_FLAT_UPPER.getRegistryName().toString()
            ), Objects::nonNull);
            carValidFuels = builder.defineList("car.valid_fuels", Arrays.asList(ModFluids.BIO_DIESEL.getRegistryName().toString()), Objects::nonNull);


            engine6CylinderFuelEfficiency = builder.defineInRange("car.parts.engine_6_cylinder.fuel_efficiency", 0.25D, 0.001D, 10D);
            engine3CylinderFuelEfficiency = builder.defineInRange("car.parts.engine_3_cylinder.fuel_efficiency", 0.5D, 0.001D, 10D);
            engineTruckFuelEfficiency = builder.defineInRange("car.parts.engine_truck.fuel_efficiency", 0.7D, 0.001D, 10D);
            engine6CylinderAcceleration = builder.defineInRange("car.parts.engine_6_cylinder.acceleration", 0.04D, 0.001D, 10D);
            engine3CylinderAcceleration = builder.defineInRange("car.parts.engine_3_cylinder.acceleration", 0.035D, 0.001D, 10D);
            engineTruckAcceleration = builder.defineInRange("car.parts.engine_truck.acceleration", 0.032D, 0.001D, 10D);
            engine6CylinderMaxSpeed = builder.defineInRange("car.parts.engine_6_cylinder.max_speed", 0.75D, 0.001D, 10D);
            engine3CylinderMaxSpeed = builder.defineInRange("car.parts.engine_3_cylinder.max_speed", 0.65D, 0.001D, 10D);
            engineTruckMaxSpeed = builder.defineInRange("car.parts.engine_truck.max_speed", 0.6D, 0.001D, 10D);
            engine6CylinderMaxReverseSpeed = builder.defineInRange("car.parts.engine_6_cylinder.max_reverse_speed", 0.2D, 0.001D, 10D);
            engine3CylinderMaxReverseSpeed = builder.defineInRange("car.parts.engine_3_cylinder.max_reverse_speed", 0.2D, 0.001D, 10D);
            engineTruckMaxReverseSpeed = builder.defineInRange("car.parts.engine_truck.max_reverse_speed", 0.15D, 0.001D, 10D);

            bodyBigWoodFuelEfficiency = builder.defineInRange("car.parts.body_big_wood.fuel_efficiency", 0.7D, 0.001D, 10D);
            bodyBigWoodAcceleration = builder.defineInRange("car.parts.body_big_wood.acceleration", 0.95D, 0.001D, 10D);
            bodyBigWoodMaxSpeed = builder.defineInRange("car.parts.body_big_wood.max_speed", 0.85D, 0.001D, 10D);
            bodyWoodFuelEfficiency = builder.defineInRange("car.parts.body_wood.fuel_efficiency", 0.8D, 0.001D, 10D);
            bodyWoodAcceleration = builder.defineInRange("car.parts.body_wood.acceleration", 1D, 0.001D, 10D);
            bodyWoodMaxSpeed = builder.defineInRange("car.parts.body_wood.max_speed", 0.9D, 0.001D, 10D);
            bodySportFuelEfficiency = builder.defineInRange("car.parts.body_sport.fuel_efficiency", 0.9D, 0.001D, 10D);
            bodySportAcceleration = builder.defineInRange("car.parts.body_sport.acceleration", 1D, 0.001D, 10D);
            bodySportMaxSpeed = builder.defineInRange("car.parts.body_sport.max_speed", 1D, 0.001D, 10D);
            bodySUVFuelEfficiency = builder.defineInRange("car.parts.body_suv.fuel_efficiency", 0.6D, 0.001D, 10D);
            bodySUVAcceleration = builder.defineInRange("car.parts.body_suv.acceleration", 0.8D, 0.001D, 10D);
            bodySUVMaxSpeed = builder.defineInRange("car.parts.body_suv.max_speed", 0.7D, 0.001D, 10D);
            bodyTransporterFuelEfficiency = builder.defineInRange("car.parts.body_transporter.fuel_efficiency", 0.6D, 0.001D, 10D);
            bodyTransporterAcceleration = builder.defineInRange("car.parts.body_transporter.acceleration", 0.8D, 0.001D, 10D);
            bodyTransporterMaxSpeed = builder.defineInRange("car.parts.body_transporter.max_speed", 0.765D, 0.001D, 10D);
        }
    }

    public static class ClientConfig {
        public ClientConfig(ForgeConfigSpec.Builder builder) {
            thirdPersonEnter = builder.define("car.third_person_when_enter_car", true);
            tempInFarenheit = builder.comment("True if the car temperature should be displayed in farenheit").define("car.temp_farenheit", false);
            carVolume = builder.defineInRange("car.car_volume", 0.25F, 0F, 1F);
        }
    }

}
