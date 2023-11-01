package de.maxhenkel.car.integration.jei;

import de.maxhenkel.car.items.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.RegistryObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CarRecipeBuilder {

    public static List<CarRecipe> getAllRecipes() {
        List<CarRecipe> recipes = new ArrayList<>();

        ItemStack wheel = new ItemStack(ModItems.WHEEL.get());
        ItemStack largeWheel = new ItemStack(ModItems.BIG_WHEEL.get());

        for (ItemStack tank : getAllTanks()) {
            for (ItemStack engine : getAllEngines()) {
                for (ItemStack plate : getAllLicensePlateHolders()) {
                    for (ItemStack bumper : getAllBumpers()) {
                        for (ItemStack body : getWoodBodies()) {
                            recipes.add(new CarRecipe(body, bumper, plate, tank, engine, wheel, wheel, wheel, wheel));
                        }
                    }

                    for (ItemStack body : getSUVBodies()) {
                        recipes.add(new CarRecipe(body, plate, tank, engine, largeWheel, largeWheel, largeWheel, largeWheel));
                    }

                    for (ItemStack body : getSportBodies()) {
                        recipes.add(new CarRecipe(body, plate, tank, engine, wheel, wheel, wheel, wheel));
                    }

                    for (ItemStack container : getAllContainers()) {
                        for (ItemStack body : getTransporters()) {
                            recipes.add(new CarRecipe(body, container, plate, tank, engine, wheel, wheel, wheel, wheel, wheel, wheel));
                        }
                    }

                    for (ItemStack container : getAllTankContainers()) {
                        for (ItemStack body : getTransporters()) {
                            recipes.add(new CarRecipe(body, container, plate, tank, engine, wheel, wheel, wheel, wheel, wheel, wheel));
                        }
                    }
                }
            }
        }

        return recipes;
    }

    public static List<ItemStack> getWoodBodies() {
        return concatItems(ModItems.BIG_WOOD_BODIES, ModItems.WOOD_BODIES);
    }

    public static List<ItemStack> getTransporters() {
        return concatItems(ModItems.TRANSPORTER_BODIES);
    }

    public static List<ItemStack> getSUVBodies() {
        return concatItems(ModItems.SUV_BODIES);
    }

    public static List<ItemStack> getSportBodies() {
        return concatItems(ModItems.SPORT_BODIES);
    }

    public static List<ItemStack> getAllBodies() {
        return concatItems(ModItems.BIG_WOOD_BODIES, ModItems.WOOD_BODIES, ModItems.SPORT_BODIES, ModItems.SUV_BODIES, ModItems.TRANSPORTER_BODIES);
    }

    public static List<ItemStack> getAllWoodBodies() {
        return concatItems(ModItems.WOOD_BODIES);
    }

    public static List<ItemStack> getAllBigWoodBodies() {
        return concatItems(ModItems.BIG_WOOD_BODIES);
    }

    public static List<ItemStack> getAllBumpers() {
        return concatItems(ModItems.BUMPERS);
    }

    public static List<ItemStack> getAllContainers() {
        return concatItems(ModItems.CONTAINERS);
    }

    public static List<ItemStack> getAllTankContainers() {
        return concatItems(ModItems.TANK_CONTAINERS);
    }

    public static List<ItemStack> getAllLicensePlateHolders() {
        List<ItemStack> bumpers = concatItems(ModItems.WOODEN_LICENSE_PLATE_HOLDERS);
        bumpers.add(new ItemStack(ModItems.IRON_LICENSE_PLATE_HOLDER.get()));
        bumpers.add(new ItemStack(ModItems.GOLD_LICENSE_PLATE_HOLDER.get()));
        bumpers.add(new ItemStack(ModItems.EMERALD_LICENSE_PLATE_HOLDER.get()));
        bumpers.add(new ItemStack(ModItems.DIAMOND_LICENSE_PLATE_HOLDER.get()));
        return bumpers;
    }

    public static List<ItemStack> getAllEngines() {
        return Arrays.asList(new ItemStack(ModItems.ENGINE_3_CYLINDER.get()), new ItemStack(ModItems.ENGINE_6_CYLINDER.get()), new ItemStack(ModItems.ENGINE_TRUCK.get()));
    }

    public static List<ItemStack> getAllTanks() {
        return Arrays.asList(new ItemStack(ModItems.SMALL_TANK.get()), new ItemStack(ModItems.MEDIUM_TANK.get()), new ItemStack(ModItems.LARGE_TANK.get()));
    }

    public static <T extends Item> List<ItemStack> concatItems(RegistryObject<T>[]... items) {
        return concatArrays(items).stream().map(RegistryObject::get).map(ItemStack::new).collect(Collectors.toList());
    }

    private static <T> List<T> concatArrays(T[]... arrays) {
        List<T> list = new ArrayList<>();

        for (T[] array : arrays) {
            Collections.addAll(list, array);
        }

        return list;
    }

}
