package de.maxhenkel.car.sounds;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class SoundLoopStart extends SoundLoopCar {

	private long timeStarted;
	
	public SoundLoopStart(World world, EntityCarBase car, SoundEvent event, SoundCategory category) {
		super(world, car, event, category);
		this.repeat=false;
		timeStarted=System.currentTimeMillis();
	}
	
	@Override
	public void update() {
		super.update();
		
		if(!car.isStarted()){
			this.donePlaying=true;
			this.repeat=false;
			this.volume=0;
		}
	}
	
	@Override
	public boolean isDonePlaying() {
		this.donePlaying=(System.currentTimeMillis()-timeStarted)>car.getStartSoundTime();
		return donePlaying;
	}
	
	@Override
	public boolean shouldStopSound() {
		return false;
	}

}
