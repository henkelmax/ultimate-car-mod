package de.maxhenkel.car.reciepe;

import de.maxhenkel.car.entity.car.EntityCarWood;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.world.World;

public class CarBuilderWoodCar implements ICarbuilder{
	
	private EnumType type;
	
	public CarBuilderWoodCar(EnumType type) {
		this.type=type;
	}
	
	@Override
	public EntityCarBase build(World world) {
		EntityCarWood car=new EntityCarWood(world, type);
		car.setFuel(100);
		return car;
	}

}
