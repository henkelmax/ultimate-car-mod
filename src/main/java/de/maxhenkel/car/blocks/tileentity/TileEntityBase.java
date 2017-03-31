package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.MathTools;
import de.maxhenkel.car.net.MessageSyncTileEntity;
import de.maxhenkel.car.proxy.CommonProxy;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBase extends TileEntity{

	public void synchronize(){
		if (!worldObj.isRemote) {
			CommonProxy.simpleNetworkWrapper.sendToAllAround(new MessageSyncTileEntity(pos, this),
					MathTools.getTileEntityTargetPoint(this));
		}
	}
	
}
