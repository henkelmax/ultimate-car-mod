package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;

public class PartEngine6Cylinder extends PartEngine {

    public PartEngine6Cylinder() {
        this.maxSpeed = () -> CarMod.SERVER_CONFIG.engine6CylinderMaxSpeed.get().floatValue();
        this.maxReverseSpeed = () -> CarMod.SERVER_CONFIG.engine6CylinderMaxReverseSpeed.get().floatValue();
        this.acceleration = () -> CarMod.SERVER_CONFIG.engine6CylinderAcceleration.get().floatValue();
        this.fuelEfficiency = () -> CarMod.SERVER_CONFIG.engine6CylinderFuelEfficiency.get().floatValue();
    }

    @Override
    public SoundEvent getStopSound() {
        return ModSounds.SPORT_ENGINE_STOP.get();
    }

    @Override
    public SoundEvent getFailSound() {
        return ModSounds.SPORT_ENGINE_FAIL.get();
    }

    @Override
    public SoundEvent getCrashSound() {
        return ModSounds.CAR_CRASH.get();
    }

    @Override
    public SoundEvent getStartSound() {
        return ModSounds.SPORT_ENGINE_START.get();
    }

    @Override
    public SoundEvent getStartingSound() {
        return ModSounds.SPORT_ENGINE_STARTING.get();
    }

    @Override
    public SoundEvent getIdleSound() {
        return ModSounds.SPORT_ENGINE_IDLE.get();
    }

    @Override
    public SoundEvent getHighSound() {
        return ModSounds.SPORT_ENGINE_HIGH.get();
    }

    @Override
    public SoundEvent getHornSound() {
        return ModSounds.CAR_HORN.get();
    }

}
