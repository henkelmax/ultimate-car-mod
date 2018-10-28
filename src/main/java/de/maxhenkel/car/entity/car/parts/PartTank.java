package de.maxhenkel.car.entity.car.parts;

public class PartTank extends Part {

    protected int size;

    public PartTank(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
