package de.maxhenkel.car.sounds;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class SoundLoopStart extends SoundLoopCar {

    public SoundLoopStart(EntityCarBase car, SoundEvent event, SoundCategory category) {
        super(car, event, category);
        this.looping = false;
    }

    @Override
    public boolean shouldStopSound() {
        return !car.isStarted();
    }
}
