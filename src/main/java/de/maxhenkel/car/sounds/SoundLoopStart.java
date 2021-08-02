package de.maxhenkel.car.sounds;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SoundLoopStart extends SoundLoopCar {

    public SoundLoopStart(EntityCarBase car, SoundEvent event, SoundSource category) {
        super(car, event, category);
        this.looping = false;
    }

    @Override
    public boolean shouldStopSound() {
        return !car.isStarted();
    }
}
