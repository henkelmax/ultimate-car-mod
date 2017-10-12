package de.maxhenkel.car.registries;

import de.maxhenkel.car.entity.car.base.EntityVehicleBase;
import de.maxhenkel.car.reciepe.ICarbuilder;

public class CarImpl implements ICar{

	private Class<? extends EntityVehicleBase> carClass;
	private ICarbuilder builder;
	
	public CarImpl(Class<? extends EntityVehicleBase> carClass, ICarbuilder builder) {
		this.carClass = carClass;
		this.builder = builder;
	}

	@Override
	public Class<? extends EntityVehicleBase> getCarClass() {
		return carClass;
	}

	@Override
	public ICarbuilder getBuilder() {
		return builder;
	}

}
