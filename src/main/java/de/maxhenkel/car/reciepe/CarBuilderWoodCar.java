package de.maxhenkel.car.reciepe;

import de.maxhenkel.car.entity.car.EntityCarWood;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.items.ItemKey;
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
		car.setInventorySlotContents(0, ItemKey.getKeyForCar(car.getUniqueID()));
		car.setInventorySlotContents(1, ItemKey.getKeyForCar(car.getUniqueID()));
		return car;
	}

	@Override
	public String getName() {
		return "car_wood_" +type.getName();
	}

}
