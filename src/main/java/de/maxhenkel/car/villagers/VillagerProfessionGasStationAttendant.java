package de.maxhenkel.car.villagers;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.merchant.villager.VillagerProfession;

public class VillagerProfessionGasStationAttendant extends VillagerProfession {

    public VillagerProfessionGasStationAttendant() {
        super("gas_station_attendant", ModPointsOfInterests.POINT_OF_INTEREST_TYPE_GAS_STATION_ATTENDANT, ImmutableSet.of(/*ModItems.CANOLA, ModItems.CANOLA_SEEDS*/), ImmutableSet.of(/*Blocks.FARMLAND*/));
    }
}
