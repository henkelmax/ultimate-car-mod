package de.maxhenkel.car;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ItemCarPart;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {

    private static final DeferredRegister<CreativeModeTab> TAB_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_CAR = TAB_REGISTER.register("car", () -> {
        return CreativeModeTab.builder()
                .icon(() -> new ItemStack(ModBlocks.ASPHALT.get()))
                .displayItems((param, output) -> {
                    output.accept(new ItemStack(ModBlocks.ASPHALT.get()));
                    output.accept(new ItemStack(ModBlocks.ASPHALT_SLOPE.get()));
                    output.accept(new ItemStack(ModBlocks.ASPHALT_SLOPE_FLAT_UPPER.get()));
                    output.accept(new ItemStack(ModBlocks.ASPHALT_SLOPE_FLAT_LOWER.get()));
                    output.accept(new ItemStack(ModBlocks.ASPHALT_SLAB.get()));
                    output.accept(new ItemStack(ModBlocks.GAS_STATION.get()));
                    output.accept(new ItemStack(ModBlocks.CANOLA_CROP.get()));
                    output.accept(new ItemStack(ModBlocks.OIL_MILL.get()));
                    output.accept(new ItemStack(ModBlocks.BLAST_FURNACE.get()));
                    output.accept(new ItemStack(ModBlocks.BACKMIX_REACTOR.get()));
                    output.accept(new ItemStack(ModBlocks.GENERATOR.get()));
                    output.accept(new ItemStack(ModBlocks.SPLIT_TANK.get()));
                    output.accept(new ItemStack(ModBlocks.TANK.get()));
                    output.accept(new ItemStack(ModBlocks.CAR_WORKSHOP.get()));
                    output.accept(new ItemStack(ModBlocks.CAR_WORKSHOP_OUTTER.get()));
                    output.accept(new ItemStack(ModBlocks.CABLE.get()));
                    output.accept(new ItemStack(ModBlocks.FLUID_EXTRACTOR.get()));
                    output.accept(new ItemStack(ModBlocks.FLUID_PIPE.get()));
                    output.accept(new ItemStack(ModBlocks.DYNAMO.get()));
                    output.accept(new ItemStack(ModBlocks.CRANK.get()));
                    output.accept(new ItemStack(ModBlocks.SIGN.get()));
                    output.accept(new ItemStack(ModBlocks.SIGN_POST.get()));
                    output.accept(new ItemStack(ModBlocks.CAR_PRESSURE_PLATE.get()));
                    output.accept(new ItemStack(ModItems.CANOLA_OIL_BUCKET.get()));
                    output.accept(new ItemStack(ModItems.METHANOL_BUCKET.get()));
                    output.accept(new ItemStack(ModItems.CANOLA_METHANOL_MIX_BUCKET.get()));
                    output.accept(new ItemStack(ModItems.GLYCERIN_BUCKET.get()));
                    output.accept(new ItemStack(ModItems.BIO_DIESEL_BUCKET.get()));

                    output.accept(new ItemStack(ModItems.PAINTER.get()));
                    output.accept(new ItemStack(ModItems.PAINTER_YELLOW.get()));
                    output.accept(new ItemStack(ModItems.CANOLA.get()));
                    output.accept(new ItemStack(ModItems.CANOLA_CAKE.get()));
                    output.accept(new ItemStack(ModItems.IRON_STICK.get()));
                    output.accept(new ItemStack(ModItems.ENGINE_PISTON.get()));
                    output.accept(new ItemStack(ModItems.CANISTER.get()));
                    output.accept(new ItemStack(ModItems.REPAIR_KIT.get()));
                    output.accept(new ItemStack(ModItems.WRENCH.get()));
                    output.accept(new ItemStack(ModItems.SCREW_DRIVER.get()));
                    output.accept(new ItemStack(ModItems.HAMMER.get()));
                    output.accept(new ItemStack(ModItems.CABLE_INSULATOR.get()));
                    output.accept(new ItemStack(ModItems.KEY.get()));
                    output.accept(new ItemStack(ModItems.BATTERY.get()));
                    output.accept(new ItemStack(ModItems.GUARD_RAIL.get()));
                    output.accept(new ItemStack(ModItems.LICENSE_PLATE.get()));
                })
                .title(Component.translatable("itemGroup.car"))
                .build();
    });

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_CAR_PARTS = TAB_REGISTER.register("car_parts", () -> {
        return CreativeModeTab.builder()
                .icon(() -> new ItemStack(ModItems.OAK_BODY.get()))
                .displayItems((param, output) -> {
                    output.accept(new ItemStack(ModItems.ENGINE_3_CYLINDER.get()));
                    output.accept(new ItemStack(ModItems.ENGINE_6_CYLINDER.get()));
                    output.accept(new ItemStack(ModItems.ENGINE_TRUCK.get()));
                    output.accept(new ItemStack(ModItems.WHEEL.get()));
                    output.accept(new ItemStack(ModItems.BIG_WHEEL.get()));
                    output.accept(new ItemStack(ModItems.SMALL_TANK.get()));
                    output.accept(new ItemStack(ModItems.MEDIUM_TANK.get()));
                    output.accept(new ItemStack(ModItems.LARGE_TANK.get()));

                    for (DeferredHolder<Item, ItemCarPart> part : ModItems.WOOD_BODIES) {
                        output.accept(new ItemStack(part.get()));
                    }
                    for (DeferredHolder<Item, ItemCarPart> part : ModItems.BIG_WOOD_BODIES) {
                        output.accept(new ItemStack(part.get()));
                    }
                    for (DeferredHolder<Item, ItemCarPart> part : ModItems.TRANSPORTER_BODIES) {
                        output.accept(new ItemStack(part.get()));
                    }
                    for (DeferredHolder<Item, ItemCarPart> part : ModItems.SUV_BODIES) {
                        output.accept(new ItemStack(part.get()));
                    }
                    for (DeferredHolder<Item, ItemCarPart> part : ModItems.SPORT_BODIES) {
                        output.accept(new ItemStack(part.get()));
                    }
                    for (DeferredHolder<Item, ItemCarPart> part : ModItems.CONTAINERS) {
                        output.accept(new ItemStack(part.get()));
                    }
                    for (DeferredHolder<Item, ItemCarPart> part : ModItems.TANK_CONTAINERS) {
                        output.accept(new ItemStack(part.get()));
                    }
                    for (DeferredHolder<Item, ItemCarPart> part : ModItems.BUMPERS) {
                        output.accept(new ItemStack(part.get()));
                    }
                    for (DeferredHolder<Item, ItemCarPart> part : ModItems.WOODEN_LICENSE_PLATE_HOLDERS) {
                        output.accept(new ItemStack(part.get()));
                    }
                    output.accept(new ItemStack(ModItems.IRON_LICENSE_PLATE_HOLDER.get()));
                    output.accept(new ItemStack(ModItems.DIAMOND_LICENSE_PLATE_HOLDER.get()));
                    output.accept(new ItemStack(ModItems.GOLD_LICENSE_PLATE_HOLDER.get()));
                    output.accept(new ItemStack(ModItems.EMERALD_LICENSE_PLATE_HOLDER.get()));
                })
                .title(Component.translatable("itemGroup.car_parts"))
                .build();
    });

    public static void init(IEventBus eventBus) {
        TAB_REGISTER.register(eventBus);
    }

}
