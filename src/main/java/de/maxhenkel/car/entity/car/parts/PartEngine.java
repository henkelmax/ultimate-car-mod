package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.util.SoundEvent;

public abstract class PartEngine extends Part {

    protected float maxSpeed;
    protected float maxReverseSpeed;
    protected float acceleration;

    public PartEngine(float maxSpeed, float maxReverseSpeed, float acceleration) {
        this.maxSpeed = maxSpeed;
        this.maxReverseSpeed = maxReverseSpeed;
        this.acceleration = acceleration;
    }

    public abstract SoundEvent getStopSound();

    public abstract SoundEvent getFailSound();

    public abstract SoundEvent getCrashSound();

    public abstract SoundEvent getStartSound();

    public abstract SoundEvent getStartingSound();

    public abstract SoundEvent getIdleSound();

    public abstract SoundEvent getHighSound();

    public abstract SoundEvent getHornSound();

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getMaxReverseSpeed() {
        return maxReverseSpeed;
    }

    public float getAcceleration() {
        return acceleration;
    }
}
