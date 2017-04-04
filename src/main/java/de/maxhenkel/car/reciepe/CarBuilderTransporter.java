package de.maxhenkel.car.reciepe;

import de.maxhenkel.car.entity.car.EntityCarTransporter;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.world.World;

public class CarBuilderTransporter implements ICarbuilder{
	
	private boolean hasContainer;
	private EnumDyeColor color;
	
	public CarBuilderTransporter(boolean hasContainer, EnumDyeColor color) {
		this.hasContainer=hasContainer;
		this.color=color;
	}
	
	@Override
	public EntityCarBase build(World world) {
		EntityCarTransporter car=new EntityCarTransporter(world, hasContainer, color);
		car.setFuel(100);
		return car;
	}

	@Override
	public String getName() {
		String name="car_transporter_" +color.getName();
		
		if(hasContainer){
			name=name+"_container";
		}
		return name;
	}

}
