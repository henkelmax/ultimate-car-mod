package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.util.SoundEvent;

public class PartEngineTruck extends PartEngine {

    public PartEngineTruck() {
        this.maxSpeed = () -> Main.SERVER_CONFIG.engineTruckMaxSpeed.get().floatValue();
        this.maxReverseSpeed = () -> Main.SERVER_CONFIG.engineTruckMaxReverseSpeed.get().floatValue();
        this.acceleration = () -> Main.SERVER_CONFIG.engineTruckAcceleration.get().floatValue();
        this.fuelEfficiency = () -> Main.SERVER_CONFIG.engineTruckFuelEfficiency.get().floatValue();
    }

    @Override
    public SoundEvent getStopSound() {
        return ModSounds.TRUCK_ENGINE_STOP;
    }

    @Override
    public SoundEvent getFailSound() {
        return ModSounds.TRUCK_ENGINE_FAIL;
    }

    @Override
    public SoundEvent getCrashSound() {
        return ModSounds.CAR_CRASH;
    }

    @Override
    public SoundEvent getStartSound() {
        return ModSounds.TRUCK_ENGINE_START;
    }

    @Override
    public SoundEvent getStartingSound() {
        return ModSounds.TRUCK_ENGINE_STARTING;
    }

    @Override
    public SoundEvent getIdleSound() {
        return ModSounds.TRUCK_ENGINE_IDLE;
    }

    @Override
    public SoundEvent getHighSound() {
        return ModSounds.TRUCK_ENGINE_HIGH;
    }

    @Override
    public SoundEvent getHornSound() {
        return ModSounds.CAR_HORN;
    }

}
