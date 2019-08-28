package de.maxhenkel.car.sounds;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class SoundLoopIdle extends SoundLoopCar {

    private float volumeToReach;

    public SoundLoopIdle(EntityCarBase car, SoundEvent event, SoundCategory category) {
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
