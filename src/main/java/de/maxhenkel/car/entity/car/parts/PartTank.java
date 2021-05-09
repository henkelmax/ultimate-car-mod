package de.maxhenkel.car.entity.car.parts;

import java.util.function.Supplier;

public class PartTank extends Part {

    protected Supplier<Integer> size;

    public PartTank(Supplier<Integer> size) {
        this.size = size;
    }

    public int getSize() {
        return size.get();
    }
}
