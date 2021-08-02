package de.maxhenkel.car.sounds;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityCarBatteryBase;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SoundLoopStarting extends SoundLoopCar {

    public SoundLoopStarting(EntityCarBase car, SoundEvent event, SoundSource category) {
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
