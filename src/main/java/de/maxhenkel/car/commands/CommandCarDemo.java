package de.maxhenkel.car.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.integration.jei.CarRecipeBuilder;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class CommandCarDemo {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("cardemo")
                .requires((source) -> source.hasPermissionLevel(2))
                .then(Commands.argument("pos", Vec3Argument.vec3()).executes((context) -> spawnCars(context.getSource().getWorld(), Vec3Argument.getVec3(context, "pos")))));
    }

    public static int spawnCars(World world, Vec3d pos) {
        ItemStack wheel = new ItemStack(ModItems.WHEEL);
        ItemStack engine = new ItemStack(ModItems.ENGINE_6_CYLINDER);
        ItemStack tank = new ItemStack(ModItems.LARGE_TANK);
        ItemStack largeWheel = new ItemStack(ModItems.BIG_WHEEL);
        double posZ = pos.z;
        List<ItemStack> transporters = CarRecipeBuilder.getTransporters();
        List<ItemStack> containers = CarRecipeBuilder.getAllContainers();
        List<ItemStack> tankContainers = CarRecipeBuilder.getAllTankContainers();
        List<ItemStack> suvBodies = CarRecipeBuilder.getSUVBodies();
        List<ItemStack> bigWoodBodies = CarRecipeBuilder.getAllBigWoodBodies();
        List<ItemStack> woodBodies = CarRecipeBuilder.getAllWoodBodies();

        for (int i = 0; i < woodBodies.size(); i++) {
            double xStart = pos.getX() - woodBodies.size();
            double posX = xStart + i * 3D;
            createCar(world, posX, pos.y, posZ, woodBodies.get(i), wheel, wheel, wheel, wheel, engine, tank);
        }
        posZ += 3D;

        for (int i = 0; i < bigWoodBodies.size(); i++) {
            double xStart = pos.getX() - bigWoodBodies.size();
            double posX = xStart + i * 3D;
            createCar(world, posX, pos.y, posZ, bigWoodBodies.get(i), wheel, wheel, wheel, wheel, engine, tank);
        }

        posZ += 3D;

        for (int i = 0; i < suvBodies.size(); i++) {
            double xStart = pos.getX() - suvBodies.size();
            double posX = xStart + i * 3D;
            createCar(world, posX, pos.y, posZ, suvBodies.get(i), largeWheel, largeWheel, largeWheel, largeWheel, engine, tank);
        }

        posZ += 3D;


        for (int i = 0; i < transporters.size(); i++) {
            double xStart = pos.getX() - transporters.size();
            double posX = xStart + i * 3D;
            createCar(world, posX, pos.y, posZ, transporters.get(i), wheel, wheel, wheel, wheel, wheel, wheel, engine, tank);
        }

        posZ += 3D;

        for (int i = 0; i < tankContainers.size(); i++) {
            for (int j = 0; j < transporters.size(); j++) {
                double xStart = pos.getX() - transporters.size();
                double posX = xStart + j * 3D;
                createCar(world, posX, pos.y, posZ, transporters.get(j), wheel, wheel, wheel, wheel, wheel, wheel, engine, tank, tankContainers.get(i));
            }
            posZ += 3D;
        }

        for (int i = 0; i < containers.size(); i++) {
            for (int j = 0; j < transporters.size(); j++) {
                double xStart = pos.getX() - transporters.size();
                double posX = xStart + j * 3D;
                createCar(world, posX, pos.y, posZ, transporters.get(j), wheel, wheel, wheel, wheel, wheel, wheel, engine, tank, containers.get(i));
            }
            posZ += 3D;
        }


        return Command.SINGLE_SUCCESS;
    }

    private static void createCar(World world, double posX, double posY, double posZ, ItemStack... items) {
        EntityGenericCar car = new EntityGenericCar(world);
        car.setPositionAndRotation(posX, posY, posZ, 180F, 0F);
        for (int i = 0; i < items.length; i++) {
            car.getPartInventory().setInventorySlotContents(i, items[i]);
        }
        car.setPartSerializer();
        car.tryInitPartsAndModel();
        world.addEntity(car);
    }

}
