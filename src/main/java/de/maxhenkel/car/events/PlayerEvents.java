package de.maxhenkel.car.events;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.entity.car.base.EntityVehicleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@OnlyIn(Dist.CLIENT)
public class PlayerEvents {

	private Minecraft minecraft;
	private EntityVehicleBase lastVehicle;

	public PlayerEvents() {
		this.minecraft = Minecraft.getInstance();
		this.lastVehicle = null;
	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent evt) {
		if (evt.side.equals(Dist.DEDICATED_SERVER)) {
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
		if (!Config.thirdPersonEnter.get()) {
			return;
		}

		if (third) {
			minecraft.gameSettings.thirdPersonView = 1;
		} else {
			minecraft.gameSettings.thirdPersonView = 0;
		}

	}

	private ClientPlayerEntity getPlayer() {
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
