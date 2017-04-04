package de.maxhenkel.car.reciepe;

import de.maxhenkel.car.entity.car.EntityCarBigWood;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.world.World;

public class CarBuilderWoodCarBig implements ICarbuilder{
	
	private EnumType type;
	
	public CarBuilderWoodCarBig(EnumType type) {
		this.type=type;
	}
	
	@Override
	public EntityCarBase build(World world) {
		EntityCarBigWood car=new EntityCarBigWood(world, type);
		car.setFuel(100);
		return car;
	}

	@Override
	public String getName() {
		return "car_big_wood_" +type.getName();
	}
	
}
