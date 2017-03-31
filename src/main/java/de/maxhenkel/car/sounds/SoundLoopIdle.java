package de.maxhenkel.car.sounds;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class SoundLoopIdle extends SoundLoopCar{

	public SoundLoopIdle(World world, EntityCarBase car, SoundEvent event, SoundCategory category) {
		super(world, car, event, category);;
	}

	@Override
	public boolean shouldStopSound() {
		if(car.getSpeed()!=0){
			return true;
		}else if(!car.isStarted()){
			return true;
		}
		
		return false;
	}

}
