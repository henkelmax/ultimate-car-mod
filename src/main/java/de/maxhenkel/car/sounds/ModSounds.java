package de.maxhenkel.car.sounds;

import de.maxhenkel.car.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModSounds {
	public static SoundEvent engine_stop=registerSound("engine_stop");
	public static SoundEvent engine_start= registerSound("engine_start");
	public static SoundEvent engine_idle= registerSound("engine_idle");
	public static SoundEvent engine_high= registerSound("engine_high");
	public static SoundEvent engine_fail = registerSound("engine_fail");
	public static SoundEvent sport_engine_stop= registerSound("sport_engine_stop");
	public static SoundEvent sport_engine_start= registerSound("sport_engine_start");
	public static SoundEvent sport_engine_idle= registerSound("sport_engine_idle");
	public static SoundEvent sport_engine_high= registerSound("sport_engine_high");
	public static SoundEvent sport_engine_fail = registerSound("sport_engine_fail");
	public static SoundEvent car_crash= registerSound("car_crash");
	public static SoundEvent gas_ststion= registerSound("gas_station");
	public static SoundEvent generator= registerSound("generator");
	public static SoundEvent car_horn = registerSound("car_horn");
	public static SoundEvent car_lock = registerSound("car_lock");
	public static SoundEvent car_unlock = registerSound("car_unlock");
	public static SoundEvent ratchet = registerSound("ratchet");
	
	public static SoundEvent registerSound(String soundName) {
		SoundEvent event=new SoundEvent(new ResourceLocation(Main.MODID, soundName));
		event.setRegistryName(new ResourceLocation(Main.MODID, soundName));
		return event;
	}

	public static void playSound(SoundEvent evt, World world, BlockPos pos, EntityPlayer entity, SoundCategory category, float volume) {
		if (entity != null) {
			world.playSound(entity, pos, evt, category, volume, 1.0F);
		} else {
			if (!world.isRemote) {
				world.playSound(entity, pos, evt, category, volume, 1.0F);
			}
		}
	}
	
	public static void playSound(SoundEvent evt, World world, BlockPos pos, EntityPlayer entity, SoundCategory category) {
		playSound(evt, world, pos, entity, category, 0.15F);
	}

	@SideOnly(Side.CLIENT)
	public static void playSoundLoop(MovingSound loop, World world) {
		if (world.isRemote) {
			Minecraft.getMinecraft().getSoundHandler().playSound(loop);
		}
	}
}
