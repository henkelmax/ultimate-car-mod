package de.maxhenkel.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class Teleport {

	public static boolean teleportToDimension(Entity e, int dimension) {
		if (e.world.isRemote || e.isDead) {
			return false;
		}
		
		int oldDimension = e.getEntityWorld().provider.getDimension();
		if(dimension==oldDimension) {
			return false;
		}
		
		if (!net.minecraftforge.common.ForgeHooks.onTravelToDimension(e, dimension)) {
			return false;
		}
		
        MinecraftServer server = e.getEntityWorld().getMinecraftServer();
        WorldServer worldServer = server.getWorld(dimension);
        WorldServer oldWorldServer = server.getWorld(oldDimension);

        if(e instanceof EntityPlayerMP) {
        	worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP) e, dimension, new CustomTeleporter(worldServer));
        }else {
        	worldServer.getMinecraftServer().getPlayerList().transferEntityToWorld(e, dimension, oldWorldServer, worldServer, new CustomTeleporter(worldServer));
        }
        
        BlockPos blockpos1 = worldServer.getSpawnPoint();
        if(!worldServer.isAirBlock(blockpos1)) {
        	blockpos1=worldServer.getTopSolidOrLiquidBlock(worldServer.getSpawnPoint());
        }
        
		e.setPositionAndUpdate(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
		
		worldServer.updateEntityWithOptionalForce(e, false);
		return true;
    }
	
}
