package de.maxhenkel.car.entity.car.parts;

public class PartEngine extends Part {

    protected float maxSpeed;
    protected float maxReverseSpeed;
    protected float acceleration;

    public PartEngine(float maxSpeed, float maxReverseSpeed, float acceleration) {
        this.maxSpeed = maxSpeed;
        this.maxReverseSpeed = maxReverseSpeed;
        this.acceleration = acceleration;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getMaxReverseSpeed() {
        return maxReverseSpeed;
    }

    public float getAcceleration() {
        return acceleration;
    }
}
