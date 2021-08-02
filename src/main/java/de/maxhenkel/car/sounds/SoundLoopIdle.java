package de.maxhenkel.car.sounds;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SoundLoopIdle extends SoundLoopCar {

    private float volumeToReach;

    public SoundLoopIdle(EntityCarBase car, SoundEvent event, SoundSource category) {
        super(car, event, category);
        volumeToReach = volume;
        volume = volume / 2.5F;
    }

    @Override
    public void tick() {
        if (volume < volumeToReach) {
            volume = Math.min(volume + volumeToReach / 2.5F, volumeToReach);
        }
        super.tick();
    }

    @Override
    public boolean shouldStopSound() {
        if (car.getSpeed() != 0) {
            return true;
        } else if (!car.isStarted()) {
            return true;
        }
        return false;
    }


}
