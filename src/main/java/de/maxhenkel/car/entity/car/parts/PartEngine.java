package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.tools.FloatSupplier;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public abstract class PartEngine extends Part {

    protected FloatSupplier maxSpeed;
    protected FloatSupplier maxReverseSpeed;
    protected FloatSupplier acceleration;
    protected FloatSupplier fuelEfficiency;

    public PartEngine() {

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
        return maxSpeed.getAsFloat();
    }

    public float getMaxReverseSpeed() {
        return maxReverseSpeed.getAsFloat();
    }

    public float getAcceleration() {
        return acceleration.getAsFloat();
    }

    public float getFuelEfficiency() {
        return fuelEfficiency.getAsFloat();
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
