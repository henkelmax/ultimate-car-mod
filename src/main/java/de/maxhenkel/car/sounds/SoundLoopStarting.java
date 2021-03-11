package de.maxhenkel.car.sounds;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityCarBatteryBase;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class SoundLoopStarting extends SoundLoopCar {

    public SoundLoopStarting(EntityCarBase car, SoundEvent event, SoundCategory category) {
        super(car, event, category);
        this.looping = true;
    }

    @Override
    public void tick() {
        if (car instanceof EntityCarBatteryBase) {
            pitch = ((EntityCarBatteryBase) car).getBatterySoundPitchLevel();
        }
        super.tick();
    }

    @Override
    public boolean shouldStopSound() {
        if (!(car instanceof EntityCarBatteryBase)) {
            return true;
        }
        return !((EntityCarBatteryBase) car).isStarting();
    }

}
