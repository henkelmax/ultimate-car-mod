package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.util.SoundEvent;

public class PartEngine6Cylinder extends PartEngine {

    public PartEngine6Cylinder() {
        this.maxSpeed = () -> Config.engine6CylinderMaxSpeed.get().floatValue();
        this.maxReverseSpeed = () -> Config.engine6CylinderMaxReverseSpeed.get().floatValue();
        this.acceleration = () -> Config.engine6CylinderAcceleration.get().floatValue();
        this.fuelEfficiency = () -> Config.engine6CylinderFuelEfficiency.get().floatValue();
    }

    @Override
    public SoundEvent getStopSound() {
        return ModSounds.SPORT_ENGINE_STOP;
    }

    @Override
    public SoundEvent getFailSound() {
        return ModSounds.SPORT_ENGINE_FAIL;
    }

    @Override
    public SoundEvent getCrashSound() {
        return ModSounds.CAR_CRASH;
    }

    @Override
    public SoundEvent getStartSound() {
        return ModSounds.SPORT_ENGINE_START;
    }

    @Override
    public SoundEvent getStartingSound() {
        return ModSounds.SPORT_ENGINE_STARTING;
    }

    @Override
    public SoundEvent getIdleSound() {
        return ModSounds.SPORT_ENGINE_IDLE;
    }

    @Override
    public SoundEvent getHighSound() {
        return ModSounds.SPORT_ENGINE_HIGH;
    }

    @Override
    public SoundEvent getHornSound() {
        return ModSounds.CAR_HORN;
    }
}
