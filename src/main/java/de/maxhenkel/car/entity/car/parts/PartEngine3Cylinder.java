package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.util.SoundEvent;

public class PartEngine3Cylinder extends PartEngine {

    public PartEngine3Cylinder() {
        this.maxSpeed = () -> Main.SERVER_CONFIG.engine3CylinderMaxSpeed.get().floatValue();
        this.maxReverseSpeed = () -> Main.SERVER_CONFIG.engine3CylinderMaxReverseSpeed.get().floatValue();
        this.acceleration = () -> Main.SERVER_CONFIG.engine3CylinderAcceleration.get().floatValue();
        this.fuelEfficiency = () -> Main.SERVER_CONFIG.engine3CylinderFuelEfficiency.get().floatValue();
    }

    @Override
    public SoundEvent getStopSound() {
        return ModSounds.ENGINE_STOP;
    }

    @Override
    public SoundEvent getFailSound() {
        return ModSounds.ENGINE_FAIL;
    }

    @Override
    public SoundEvent getCrashSound() {
        return ModSounds.CAR_CRASH;
    }

    @Override
    public SoundEvent getStartSound() {
        return ModSounds.ENGINE_START;
    }

    @Override
    public SoundEvent getStartingSound() {
        return ModSounds.ENGINE_STARTING;
    }

    @Override
    public SoundEvent getIdleSound() {
        return ModSounds.ENGINE_IDLE;
    }

    @Override
    public SoundEvent getHighSound() {
        return ModSounds.ENGINE_HIGH;
    }

    @Override
    public SoundEvent getHornSound() {
        return ModSounds.CAR_HORN;
    }

}
