package de.maxhenkel.car.entity.car.parts;


import java.util.List;

public abstract class Part {
    public boolean isValid(List<Part> parts) {
        return true;
    }

    protected static boolean checkAmount(List<Part> modelParts, int min, int max, Checker checker) {
        int i = 0;
        for (Part part : modelParts) {
            if (checker.check(part)) {
                i++;
            }
        }

        return i >= min && i <= max;
    }

    protected static interface Checker {
        boolean check(Part part);
    }
}
