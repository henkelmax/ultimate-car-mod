package de.maxhenkel.car.sounds;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SoundLoopHigh extends SoundLoopCar {

    public SoundLoopHigh(EntityCarBase car, SoundEvent event, SoundSource category) {
        super(car, event, category);
    }

    @Override
    public void tick() {
        pitch = car.getPitch();
        super.tick();
    }

    @Override
    public boolean shouldStopSound() {
        if (car.getSpeed() == 0F) {
            return true;
        } else if (!car.isStarted()) {
            return true;
        }

        return false;
    }

}
