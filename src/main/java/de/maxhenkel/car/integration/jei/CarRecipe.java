package de.maxhenkel.car.integration.jei;

import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class CarRecipe {

    private List<ItemStack> inputs;

    public CarRecipe(List<ItemStack> inputs) {
        this.inputs = inputs;
    }

    public CarRecipe(ItemStack... inputs) {
        this.inputs = Arrays.asList(inputs);
    }

    public List<ItemStack> getInputs() {
        return inputs;
    }

}
