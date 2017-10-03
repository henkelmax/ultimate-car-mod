package de.maxhenkel.car.events;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.entity.car.base.EntityVehicleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerEvents {

	private Minecraft minecraft;
	private EntityVehicleBase lastVehicle;

	public PlayerEvents() {
		this.minecraft = Minecraft.getMinecraft();
		this.lastVehicle = null;
	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent evt) {
		if (evt.side.equals(Side.SERVER)) {
			return;
		}
		
		if(!evt.player.equals(getPlayer())){
			return;
		}

		EntityVehicleBase vehicle = getRidingVehicle();
		
		if (vehicle!=null && lastVehicle==null) {
			if(vehicle.doesEnterThirdPerson()){
				setThirdPerson(true);
			}
		} else if (vehicle==null && lastVehicle!=null) {
			if(lastVehicle.doesEnterThirdPerson()){
				setThirdPerson(false);
			}
		}
		lastVehicle = vehicle;
	}

	private void setThirdPerson(boolean third) {
		if (!Config.thirdPersonEnter) {
			return;
		}

		if (third) {
			minecraft.gameSettings.thirdPersonView = 1;
		} else {
			minecraft.gameSettings.thirdPersonView = 0;
		}

	}

	private EntityPlayerSP getPlayer() {
		return minecraft.player;
	}

	private Entity getRidingEntity() {
		return getPlayer().getRidingEntity();
	}

	private EntityVehicleBase getRidingVehicle() {
		Entity e=getRidingEntity();
		if(e instanceof EntityVehicleBase){
			return (EntityVehicleBase) e;
		}
		
		return null;
	}
}
