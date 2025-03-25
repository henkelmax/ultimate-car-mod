package de.maxhenkel.car.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.integration.jei.CarRecipeBuilder;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CommandCarDemo {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("cardemo")
                .requires((source) -> source.hasPermission(2))
                .then(Commands.argument("pos", Vec3Argument.vec3()).executes((context) -> spawnCars(context.getSource().getLevel(), Vec3Argument.getVec3(context, "pos")))));
    }

    public static int spawnCars(Level world, Vec3 pos) {
        ItemStack wheel = new ItemStack(ModItems.WHEEL.get());
        ItemStack engine = new ItemStack(ModItems.ENGINE_6_CYLINDER.get());
        ItemStack tank = new ItemStack(ModItems.LARGE_TANK.get());
        ItemStack largeWheel = new ItemStack(ModItems.BIG_WHEEL.get());
        double posZ = pos.z;
        List<ItemStack> transporters = CarRecipeBuilder.getTransporters();
        List<ItemStack> containers = CarRecipeBuilder.getAllContainers();
        List<ItemStack> tankContainers = CarRecipeBuilder.getAllTankContainers();
        List<ItemStack> sportBodies = CarRecipeBuilder.getSportBodies();
        List<ItemStack> suvBodies = CarRecipeBuilder.getSUVBodies();
        List<ItemStack> bigWoodBodies = CarRecipeBuilder.getAllBigWoodBodies();
        List<ItemStack> woodBodies = CarRecipeBuilder.getAllWoodBodies();

        for (int i = 0; i < woodBodies.size(); i++) {
            double xStart = pos.x() - woodBodies.size();
            double posX = xStart + i * 3D;
            createCar(world, posX, pos.y, posZ, woodBodies.get(i), wheel, wheel, wheel, wheel, engine, tank);
        }
        posZ += 3D;

        for (int i = 0; i < bigWoodBodies.size(); i++) {
            double xStart = pos.x() - bigWoodBodies.size();
            double posX = xStart + i * 3D;
            createCar(world, posX, pos.y, posZ, bigWoodBodies.get(i), wheel, wheel, wheel, wheel, engine, tank);
        }

        posZ += 3D;

        for (int i = 0; i < sportBodies.size(); i++) {
            double xStart = pos.x() - sportBodies.size();
            double posX = xStart + i * 3D;
            createCar(world, posX, pos.y, posZ, sportBodies.get(i), largeWheel, largeWheel, largeWheel, largeWheel, engine, tank);
        }

        posZ += 3D;

        for (int i = 0; i < suvBodies.size(); i++) {
            double xStart = pos.x() - suvBodies.size();
            double posX = xStart + i * 3D;
            createCar(world, posX, pos.y, posZ, suvBodies.get(i), largeWheel, largeWheel, largeWheel, largeWheel, engine, tank);
        }

        posZ += 3D;


        for (int i = 0; i < transporters.size(); i++) {
            double xStart = pos.x() - transporters.size();
            double posX = xStart + i * 3D;
            createCar(world, posX, pos.y, posZ, transporters.get(i), wheel, wheel, wheel, wheel, wheel, wheel, engine, tank);
        }

        posZ += 3D;

        for (ItemStack tankContainer : tankContainers) {
            for (int j = 0; j < transporters.size(); j++) {
                double xStart = pos.x() - transporters.size();
                double posX = xStart + j * 3D;
                createCar(world, posX, pos.y, posZ, transporters.get(j), wheel, wheel, wheel, wheel, wheel, wheel, engine, tank, tankContainer);
            }
            posZ += 3D;
        }

        for (ItemStack container : containers) {
            for (int j = 0; j < transporters.size(); j++) {
                double xStart = pos.x() - transporters.size();
                double posX = xStart + j * 3D;
                createCar(world, posX, pos.y, posZ, transporters.get(j), wheel, wheel, wheel, wheel, wheel, wheel, engine, tank, container);
            }
            posZ += 3D;
        }


        return Command.SINGLE_SUCCESS;
    }

    private static void createCar(Level world, double posX, double posY, double posZ, ItemStack... items) {
        EntityGenericCar car = new EntityGenericCar(world);
        car.absSnapTo(posX, posY, posZ, 180F, 0F);
        for (int i = 0; i < items.length; i++) {
            car.getPartInventory().setItem(i, items[i]);
        }
        car.setPartSerializer();
        car.tryInitPartsAndModel();
        world.addFreshEntity(car);
    }

}
