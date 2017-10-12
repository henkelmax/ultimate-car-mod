package de.maxhenkel.car.registries;

public class CarProperties{

	public static final StringRegistry<CarProperties> REGISTRY=new StringRegistry<CarProperties>();
	
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
