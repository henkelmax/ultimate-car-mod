package de.maxhenkel.car.sounds;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityCarBatteryBase;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class SoundLoopStart extends SoundLoopCar {

	public SoundLoopStart(World world, EntityCarBase car, SoundEvent event, SoundCategory category) {
		super(world, car, event, category);
		this.repeat=true;
	}

    @Override
    public void update() {
	    //pitch-=0.01;
        if(car instanceof EntityCarBatteryBase){
            pitch=((EntityCarBatteryBase) car).getBatterySoundPitchLevel();
        }
        super.update();
    }

    @Override
	public boolean shouldStopSound() {
        if(!(car instanceof EntityCarBatteryBase)){
            return true;
        }
		return !((EntityCarBatteryBase)car).isStarting();
	}

}
