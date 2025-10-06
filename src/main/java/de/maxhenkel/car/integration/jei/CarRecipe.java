package de.maxhenkel.car.integration.jei;

import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import net.minecraft.world.item.ItemStack;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class CarRecipe {

    private final List<ItemStack> inputs;
    private WeakReference<EntityGenericCar> cachedCar;

    public CarRecipe(List<ItemStack> inputs) {
        this.inputs = inputs;
        cachedCar = new WeakReference<>(null);
    }

    public CarRecipe(ItemStack... inputs) {
        this(Arrays.asList(inputs));
    }

    public List<ItemStack> getInputs() {
        return inputs;
    }

    public EntityGenericCar getCachedCar(Supplier<EntityGenericCar> carSupplier) {
        if (cachedCar.get() == null) {
            cachedCar = new WeakReference<>(carSupplier.get());
        }
        return cachedCar.get();
    }

}
