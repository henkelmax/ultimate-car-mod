package de.maxhenkel.car.sounds;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class SoundLoopHigh extends SoundLoopCar {

    public SoundLoopHigh(EntityCarBase car, SoundEvent event, SoundCategory category) {
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
