package de.maxhenkel.tools;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class CustomTeleporter extends Teleporter{

	public CustomTeleporter(WorldServer worldIn) {
		super(worldIn);
		
	}
	
	@Override
	public boolean makePortal(Entity entityIn) {
		return true;
	}
	
	@Override
	public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
		return true;
	}
	
	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw) {
		
	}
	
	@Override
	public void removeStalePortalLocations(long worldTime) {
		
	}

}
