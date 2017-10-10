package de.maxhenkel.car.events;

import atomicstryker.dynamiclights.client.DynamicLights;
import atomicstryker.dynamiclights.client.IDynamicLightSource;
import de.maxhenkel.car.entity.car.base.EntityVehicleBase;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DynamicLightEvents {

	@SubscribeEvent
	public void onEntityJoinedWorld(EntityJoinWorldEvent event) {
		
		if (!Loader.isModLoaded("dynamiclights")) {
			return;
		}
		
		Entity e=event.getEntity();
		
		if(!(e instanceof EntityVehicleBase)){
			return;
		}
		
		DynamicLights.addLightSource(new CarLightAdapter((EntityVehicleBase) e));
		
	}

	private class CarLightAdapter implements IDynamicLightSource {
		private EntityVehicleBase vehicle;

		public CarLightAdapter(EntityVehicleBase vehicle) {
			this.vehicle = vehicle;
		}

		public Entity getAttachmentEntity() {
			return vehicle;
		}

		public int getLightLevel() {
			return 15;
		}
	}

}
