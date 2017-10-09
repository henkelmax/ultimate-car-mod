package de.maxhenkel.car.registries;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

public class CarProperties extends IForgeRegistryEntry.Impl<CarProperties>{

	public static final IForgeRegistry<CarProperties> REGISTRY=new RegistryBuilder<CarProperties>().setName(new ResourceLocation("car_properties")).setType(CarProperties.class).setIDRange(0, 8096).create();
	
	private float speed;
	private float reverseSpeed;
	private float acceleration;
	private String carID;
	
	public CarProperties(String carID, float speed, float acceleration, float reverseSpeed) {
		this.carID=carID;
		this.speed=speed;
		this.acceleration=acceleration;
		this.reverseSpeed=reverseSpeed;
	}

	public String getCarID() {
		return carID;
	}

	public float getSpeed() {
		return speed;
	}

	public float getAcceleration() {
		return acceleration;
	}

	public float getReverseSpeed() {
		return reverseSpeed;
	}
	

}
