package de.maxhenkel.car.reciepe;

import de.maxhenkel.car.entity.car.EntityCarSport;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.items.ItemKey;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.world.World;

public class CarBuilderSport implements ICarbuilder{
	
	private EnumDyeColor color;
	
	public CarBuilderSport(EnumDyeColor color) {
		this.color=color;
	}
	
	@Override
	public EntityCarBase build(World world) {
		EntityCarSport car=new EntityCarSport(world, color);
		car.setFuelAmount(100);
		car.setInventorySlotContents(0, ItemKey.getKeyForCar(car.getUniqueID()));
		car.setInventorySlotContents(1, ItemKey.getKeyForCar(car.getUniqueID()));
		return car;
	}

	@Override
	public String getName() {
		String name="car_sport_" +color.getName();
		return name;
	}

}
