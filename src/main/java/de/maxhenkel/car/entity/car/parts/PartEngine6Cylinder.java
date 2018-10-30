package de.maxhenkel.car.entity.car.parts;

import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.util.SoundEvent;

public class PartEngine6Cylinder extends PartEngine{

    public PartEngine6Cylinder() {
        super(0.65F, 0.2F, 0.04F, 0.12F);
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
