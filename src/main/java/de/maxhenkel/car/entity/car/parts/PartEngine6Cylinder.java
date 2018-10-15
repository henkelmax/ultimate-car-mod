package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.util.SoundEvent;

public class PartEngine6Cylinder extends PartEngine{

    public PartEngine6Cylinder(float maxSpeed, float maxReverseSpeed, float acceleration) {
        super(maxSpeed, maxReverseSpeed, acceleration);
    }

    @Override
    public SoundEvent getStopSound() {
        return ModSounds.sport_engine_stop;
    }

    @Override
    public SoundEvent getFailSound() {
        return ModSounds.sport_engine_fail;
    }

    @Override
    public SoundEvent getCrashSound() {
        return ModSounds.car_crash;
    }

    @Override
    public SoundEvent getStartSound() {
        return ModSounds.sport_engine_start;
    }

    @Override
    public SoundEvent getStartingSound() {
        return ModSounds.sport_engine_starting;
    }

    @Override
    public SoundEvent getIdleSound() {
        return ModSounds.sport_engine_idle;
    }

    @Override
    public SoundEvent getHighSound() {
        return ModSounds.sport_engine_high;
    }

    @Override
    public SoundEvent getHornSound() {
        return ModSounds.car_horn;
    }
}
