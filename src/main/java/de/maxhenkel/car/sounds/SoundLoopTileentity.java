package de.maxhenkel.car.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SoundLoopTileentity extends MovingSound {

	protected World world;
	protected BlockPos pos;
	
	public SoundLoopTileentity(SoundEvent event, SoundCategory category, TileEntity tileEntity) {
		super(event, category);
		this.world=tileEntity.getWorld();
		this.pos=tileEntity.getPos();
		this.repeat = true;
		this.repeatDelay = 0;
		this.xPosF = pos.getX();
		this.yPosF = pos.getY();
		this.zPosF = pos.getZ();
		this.volume=0.15F;
		this.pitch=1.0F;
	}

	public void update() {
		if(donePlaying){
			return;
		}
		
		TileEntity tileEntity=getTileEntity();
		
		if(tileEntity==null||tileEntity.isInvalid()||tileEntity.getWorld()==null){
			stop();
		}
		
		if(tileEntity.getWorld().isRemote){
			EntityPlayerSP player=Minecraft.getMinecraft().thePlayer;
			if(player==null||player.isDead){
				stop();
				return;
			}
		}
		
		if(!(tileEntity instanceof ISoundLoopable)){
			stop();
			return;
		}
		
		ISoundLoopable loop=(ISoundLoopable) tileEntity;
		
		if(!loop.shouldSoundBePlayed()){
			stop();
			return;
		}
		
	}
	
	public TileEntity getTileEntity(){
		return world.getTileEntity(pos);
	}
	
	public void stop(){
		this.donePlaying=true;
		this.repeat=false;
	}

	public static interface ISoundLoopable{
		
		public boolean shouldSoundBePlayed();
		
		public void play();
		
	}
	
}