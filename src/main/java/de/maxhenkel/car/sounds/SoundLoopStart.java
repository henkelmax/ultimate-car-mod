package de.maxhenkel.car.sounds;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class SoundLoopStart extends SoundLoopCar {

    public SoundLoopStart(World world, EntityCarBase car, SoundEvent event, SoundCategory category) {
        super(world, car, event, category);
        this.repeat = false;
    }

    @Override
    public boolean shouldStopSound() {
        return !car.isStarted();
    }
}
