package de.maxhenkel.car.registries;

import de.maxhenkel.car.entity.car.base.EntityVehicleBase;
import de.maxhenkel.car.reciepe.ICarbuilder;

public interface ICar{

	public Class<? extends EntityVehicleBase> getCarClass();
	
	public ICarbuilder getBuilder();
	
}
