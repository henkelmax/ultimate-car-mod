package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.util.SoundEvent;

public class PartEngine3Cylinder extends PartEngine{

    public PartEngine3Cylinder(float maxSpeed, float maxReverseSpeed, float acceleration) {
        super(maxSpeed, maxReverseSpeed, acceleration);
    }

    @Override
    public SoundEvent getStopSound() {
        return ModSounds.engine_stop;
    }

    @Override
    public SoundEvent getFailSound() {
        return ModSounds.engine_fail;
    }

    @Override
    public SoundEvent getCrashSound() {
        return ModSounds.car_crash;
    }

    @Override
    public SoundEvent getStartSound() {
        return ModSounds.engine_start;
    }

    @Override
    public SoundEvent getStartingSound() {
        return ModSounds.engine_starting;
    }

    @Override
    public SoundEvent getIdleSound() {
        return ModSounds.engine_idle;
    }

    @Override
    public SoundEvent getHighSound() {
        return ModSounds.engine_high;
    }

    @Override
    public SoundEvent getHornSound() {
        return ModSounds.car_horn;
    }
}
