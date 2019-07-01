package de.maxhenkel.car.entity.car.parts;

import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public abstract class PartEngine extends Part {

    protected float maxSpeed;
    protected float maxReverseSpeed;
    protected float acceleration;
    protected float fuelEfficiency;

    public PartEngine(float maxSpeed, float maxReverseSpeed, float acceleration, float fuelEfficiency) {
        this.maxSpeed = maxSpeed;
        this.maxReverseSpeed = maxReverseSpeed;
        this.acceleration = acceleration;
        this.fuelEfficiency = fuelEfficiency;
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

    public float getFuelEfficiency() {
        return fuelEfficiency;
    }

    @Override
    public boolean validate(List<Part> parts, List<ITextComponent> messages) {
        if (getAmount(parts, part -> part instanceof PartTank) > 1) {
            messages.add(new TranslationTextComponent("message.parts.too_many_tanks"));
        } else if (getAmount(parts, part -> part instanceof PartTank) <= 0) {
            messages.add(new TranslationTextComponent("message.parts.no_tank"));
        }
        return super.validate(parts, messages);
    }
    /*
    Speeds

    transporter 3 -> 27.54
    transporter 6 -> 35.8

    big wood 3 -> 30.6
    big wood 6 -> 39.78

    wood 3 -> 32.4
    wood 6 -> 42.12

    sport 3 -> 36
    sport 6 -> 46.8
    */
}
