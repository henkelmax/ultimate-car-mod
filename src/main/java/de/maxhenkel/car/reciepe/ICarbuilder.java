package de.maxhenkel.car.reciepe;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.world.World;

public interface ICarbuilder {

	EntityCarBase build(World world);
	
}
