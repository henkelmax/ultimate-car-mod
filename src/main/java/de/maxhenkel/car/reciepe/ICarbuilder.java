package de.maxhenkel.car.reciepe;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.world.World;

public interface ICarbuilder {

	public EntityCarBase build(World world);
	
	public String getName();
	
}
