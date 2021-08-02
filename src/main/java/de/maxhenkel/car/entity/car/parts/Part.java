package de.maxhenkel.car.entity.car.parts;

import net.minecraft.network.chat.Component;

import java.util.List;

public abstract class Part {

    public boolean validate(List<Part> parts, List<Component> messages) {
        return true;
    }

    protected static int getAmount(List<Part> modelParts, Checker checker) {
        int i = 0;
        for (Part part : modelParts) {
            if (checker.check(part)) {
                i++;
            }
        }

        return i;
    }

    protected interface Checker {
        boolean check(Part part);
    }
}
