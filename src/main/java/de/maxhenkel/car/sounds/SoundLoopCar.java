package de.maxhenkel.car.sounds;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public abstract class SoundLoopCar extends MovingSound {

	protected World world;
	protected EntityCarBase car;
	
	public SoundLoopCar(World world, EntityCarBase car, SoundEvent event, SoundCategory category) {
		super(event, category);
		this.world=world;
		this.car=car;
		this.repeat = true;
		this.repeatDelay = 0;
		this.updatePos();
		this.volume=Config.carVolume;
		this.pitch=1.0F;
	}

	public void updatePos(){
		this.xPosF = car.getPosition().getX();
		this.yPosF = car.getPosition().getY();
		this.zPosF = car.getPosition().getZ();
	}
	
	public void update() {
		if(donePlaying){
			onFinishPlaying();
			return;
		}
		
		if(car.isDead){
			this.donePlaying=true;
			this.repeat=false;
			return;
		}
		
		if(world.isRemote){
			EntityPlayerSP player=Minecraft.getMinecraft().player;
			if(player==null||player.isDead){
				this.donePlaying=true;
				this.repeat=false;
				return;
			}
		}
		
		if(shouldStopSound()){
			this.donePlaying=true;
			this.repeat=false;
			return;
		}
		
		updatePos();
	}
	
	public void onFinishPlaying(){
		
	}

	public void setDonePlaying(){
		donePlaying=true;
	}
	
	public abstract boolean shouldStopSound();
}
