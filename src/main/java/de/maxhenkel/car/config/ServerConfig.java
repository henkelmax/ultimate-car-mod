package de.maxhenkel.car.config;

import de.maxhenkel.corelib.config.ConfigBase;
import de.maxhenkel.corelib.tag.Tag;
import de.maxhenkel.corelib.tag.TagUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ServerConfig extends ConfigBase {

    public final ModConfigSpec.IntValue backmixReactorEnergyStorage;
    public final ModConfigSpec.IntValue backmixReactorEnergyUsage;
    public final ModConfigSpec.IntValue backmixReactorFluidStorage;
    public final ModConfigSpec.IntValue backmixReactorGeneratingTime;
    public final ModConfigSpec.IntValue backmixReactorMixGeneration;
    public final ModConfigSpec.IntValue backmixReactorMethanolUsage;
    public final ModConfigSpec.IntValue backmixReactorCanolaUsage;

    public final ModConfigSpec.IntValue blastFurnaceEnergyStorage;
    public final ModConfigSpec.IntValue blastFurnaceFluidStorage;

    public final ModConfigSpec.IntValue oilMillEnergyStorage;
    public final ModConfigSpec.IntValue oilMillFluidStorage;

    public final ModConfigSpec.IntValue cableTransferRate;

    public final ModConfigSpec.IntValue dynamoEnergyStorage;
    public final ModConfigSpec.IntValue dynamoEnergyGeneration;

    public final ModConfigSpec.IntValue fluidExtractorDrainSpeed;

    public final ModConfigSpec.IntValue gasStationTransferRate;
    public final ModConfigSpec.ConfigValue<List<? extends String>> gasStationValidFuels;

    public final ModConfigSpec.IntValue generatorEnergyStorage;
    public final ModConfigSpec.IntValue generatorFluidStorage;
    public final ModConfigSpec.IntValue generatorEnergyGeneration;
    public final ModConfigSpec.ConfigValue<List<? extends String>> generatorValidFuels;

    public final ModConfigSpec.IntValue splitTankFluidStorage;
    public final ModConfigSpec.IntValue splitTankGeneratingTime;
    public final ModConfigSpec.IntValue splitTankMixUsage;
    public final ModConfigSpec.IntValue splitTankGlycerinGeneration;
    public final ModConfigSpec.IntValue splitTankBioDieselGeneration;

    public final ModConfigSpec.DoubleValue repairKitRepairAmount;

    public final ModConfigSpec.IntValue canisterMaxFuel;

    public final ModConfigSpec.DoubleValue carOffroadSpeed;
    public final ModConfigSpec.DoubleValue carOnroadSpeed;
    public final ModConfigSpec.ConfigValue<List<? extends String>> carDriveBlocks;

    public final ModConfigSpec.BooleanValue collideWithEntities;
    public final ModConfigSpec.BooleanValue damageEntities;
    public final ModConfigSpec.BooleanValue hornFlee;
    public final ModConfigSpec.BooleanValue useBattery;

    public final ModConfigSpec.IntValue tankSmallMaxFuel;
    public final ModConfigSpec.IntValue tankMediumMaxFuel;
    public final ModConfigSpec.IntValue tankLargeMaxFuel;

    public final ModConfigSpec.DoubleValue engine6CylinderFuelEfficiency;
    public final ModConfigSpec.DoubleValue engine3CylinderFuelEfficiency;
    public final ModConfigSpec.DoubleValue engineTruckFuelEfficiency;

    public final ModConfigSpec.DoubleValue engine6CylinderAcceleration;
    public final ModConfigSpec.DoubleValue engine3CylinderAcceleration;
    public final ModConfigSpec.DoubleValue engineTruckAcceleration;

    public final ModConfigSpec.DoubleValue engine6CylinderMaxSpeed;
    public final ModConfigSpec.DoubleValue engine3CylinderMaxSpeed;
    public final ModConfigSpec.DoubleValue engineTruckMaxSpeed;

    public final ModConfigSpec.DoubleValue engine6CylinderMaxReverseSpeed;
    public final ModConfigSpec.DoubleValue engine3CylinderMaxReverseSpeed;
    public final ModConfigSpec.DoubleValue engineTruckMaxReverseSpeed;

    public final ModConfigSpec.DoubleValue bodyBigWoodFuelEfficiency;
    public final ModConfigSpec.DoubleValue bodyBigWoodAcceleration;
    public final ModConfigSpec.DoubleValue bodyBigWoodMaxSpeed;

    public final ModConfigSpec.DoubleValue bodyWoodFuelEfficiency;
    public final ModConfigSpec.DoubleValue bodyWoodAcceleration;
    public final ModConfigSpec.DoubleValue bodyWoodMaxSpeed;

    public final ModConfigSpec.DoubleValue bodySportFuelEfficiency;
    public final ModConfigSpec.DoubleValue bodySportAcceleration;
    public final ModConfigSpec.DoubleValue bodySportMaxSpeed;

    public final ModConfigSpec.DoubleValue bodySUVFuelEfficiency;
    public final ModConfigSpec.DoubleValue bodySUVAcceleration;
    public final ModConfigSpec.DoubleValue bodySUVMaxSpeed;

    public final ModConfigSpec.DoubleValue bodyTransporterFuelEfficiency;
    public final ModConfigSpec.DoubleValue bodyTransporterAcceleration;
    public final ModConfigSpec.DoubleValue bodyTransporterMaxSpeed;

    public List<Tag<Fluid>> gasStationValidFuelList = new ArrayList<>();
    public List<Tag<Fluid>> generatorValidFuelList = new ArrayList<>();
    public List<Tag<Block>> carDriveBlockList = new ArrayList<>();

    public ServerConfig(ModConfigSpec.Builder builder) {
        super(builder);
        backmixReactorEnergyStorage = builder.defineInRange("machines.backmix_reactor.energy_storage", 10000, 100, Short.MAX_VALUE);
        backmixReactorEnergyUsage = builder.defineInRange("machines.backmix_reactor.energy_usage", 10, 1, Short.MAX_VALUE);
        backmixReactorFluidStorage = builder.defineInRange("machines.backmix_reactor.fluid_storage", 3000, 1000, Short.MAX_VALUE);
        backmixReactorGeneratingTime = builder.defineInRange("machines.backmix_reactor.generating_time", 200, 10, Short.MAX_VALUE);
        backmixReactorMixGeneration = builder.defineInRange("machines.backmix_reactor.mix_generation", 100, 1, Short.MAX_VALUE);
        backmixReactorMethanolUsage = builder.defineInRange("machines.backmix_reactor.methanol_usage", 50, 1, Short.MAX_VALUE);
        backmixReactorCanolaUsage = builder.defineInRange("machines.backmix_reactor.canola_usage", 50, 1, Short.MAX_VALUE);

        blastFurnaceEnergyStorage = builder.defineInRange("machines.blast_furnace.energy_storage", 10000, 1000, Short.MAX_VALUE);
        blastFurnaceFluidStorage = builder.defineInRange("machines.blast_furnace.fluid_storage", 3000, 1000, Short.MAX_VALUE);

        oilMillEnergyStorage = builder.defineInRange("machines.oil_mill.energy_storage", 10000, 1000, Short.MAX_VALUE);
        oilMillFluidStorage = builder.defineInRange("machines.oil_mill.fluid_storage", 3000, 1000, Short.MAX_VALUE);

        cableTransferRate = builder.defineInRange("machines.cable.transfer_rate", 256, 64, Short.MAX_VALUE);

        dynamoEnergyStorage = builder.defineInRange("machines.dynamo.energy_storage", 1000, 100, Short.MAX_VALUE);
        dynamoEnergyGeneration = builder.defineInRange("machines.dynamo.energy_generation", 25, 1, Short.MAX_VALUE);

        fluidExtractorDrainSpeed = builder.defineInRange("machines.fluid_extractor.drain_speed", 25, 5, Short.MAX_VALUE);

        gasStationTransferRate = builder.defineInRange("machines.gas_station.transfer_rate", 5, 1, Short.MAX_VALUE);
        gasStationValidFuels = builder.comment("If it starts with '#' it is a tag").defineList("machines.gas_station.valid_fuels", Collections.singletonList("#car:gas_station"), Objects::nonNull);

        generatorEnergyStorage = builder.defineInRange("machines.generator.energy_storage", 30000, 1000, Short.MAX_VALUE);
        generatorFluidStorage = builder.defineInRange("machines.generator.fluid_storage", 3000, 1000, Short.MAX_VALUE);
        generatorEnergyGeneration = builder.defineInRange("machines.generator.energy_generation", 500, 1, Short.MAX_VALUE);
        generatorValidFuels = builder.comment("If it starts with '#' it is a tag").defineList("machines.generator.valid_fuels", Collections.singletonList("#car:generator"), Objects::nonNull);

        splitTankFluidStorage = builder.defineInRange("machines.split_tank.fluid_storage", 3000, 1000, Short.MAX_VALUE);
        splitTankGeneratingTime = builder.defineInRange("machines.split_tank.generating_time", 800, 10, Short.MAX_VALUE);
        splitTankMixUsage = builder.defineInRange("machines.split_tank.mix_usage", 100, 1, Short.MAX_VALUE);
        splitTankGlycerinGeneration = builder.defineInRange("machines.split_tank.glycerin_generation", 10, 1, Short.MAX_VALUE);
        splitTankBioDieselGeneration = builder.defineInRange("machines.split_tank.bio_diesel_generation", 100, 1, Short.MAX_VALUE);

        repairKitRepairAmount = builder.defineInRange("items.repair_kit.repair_amount", 5F, 0.1F, 100F);

        canisterMaxFuel = builder.defineInRange("items.canister.max_fuel", 100, 1, 1000);

        collideWithEntities = builder.comment("Whether the cars should collide with other entities (except cars)").define("car.collide_with_entities", false);
        damageEntities = builder.comment("Whether the cars should damage other entities on collision").define("car.damage_entities", true);
        hornFlee = builder.comment("Whether animals flee from the car when the horn is activted").define("car.horn_flee", true);
        useBattery = builder.comment("True if starting the car should use battery").define("car.use_battery", true);

        carOffroadSpeed = builder.comment("The speed modifier for cars on non road blocks").defineInRange("car.offroad_speed_modifier", 1D, 0.001D, 10D);
        carOnroadSpeed = builder.comment("The speed modifier for cars on road blocks", "On road blocks are defined in the config section 'road_blocks'").defineInRange("car.onroad_speed_modifier", 1D, 0.001D, 10D);
        carDriveBlocks = builder.comment("If it starts with '#' it is a tag").defineList("car.road_blocks.blocks", Collections.singletonList("#car:drivable_blocks"), Objects::nonNull);

        tankSmallMaxFuel = builder.defineInRange("car.parts.small_tank.max_fuel", 500, 100, 100_000);
        tankMediumMaxFuel = builder.defineInRange("car.parts.medium_tank.max_fuel", 1000, 100, 100_000);
        tankLargeMaxFuel = builder.defineInRange("car.parts.large_tank.max_fuel", 1500, 100, 100_000);

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

    @Override
    public void onReload(ModConfigEvent.Reloading event) {
        super.onReload(event);
        onConfigChanged();
    }

    @Override
    public void onLoad(ModConfigEvent.Loading evt) {
        super.onLoad(evt);
        onConfigChanged();
    }

    private void onConfigChanged() {
        gasStationValidFuelList = gasStationValidFuels.get().stream().map(TagUtils::getFluid).filter(Objects::nonNull).collect(Collectors.toList());
        generatorValidFuelList = generatorValidFuels.get().stream().map(TagUtils::getFluid).filter(Objects::nonNull).collect(Collectors.toList());
        carDriveBlockList = carDriveBlocks.get().stream().map(TagUtils::getBlock).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
