package de.maxhenkel.car.events;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.net.MessageSyncConfig;
import de.maxhenkel.car.proxy.CommonProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class ConfigEvents {
	
	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent evt) {
		if(evt.getWorld().isRemote){
			return;
		}
		
		Entity e=evt.getEntity();
		
		if(!(e instanceof EntityPlayerMP)){
			return;
		}
		
		EntityPlayerMP player=(EntityPlayerMP) e;
		
		if(player.mcServer.isSinglePlayer()){
			Config.init(Config.config);
		}
		
		CommonProxy.simpleNetworkWrapper.sendTo(new MessageSyncConfig(Config.carGroundSpeed), player);
	}
}
