package de.maxhenkel.car.integration.jei;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.stream.Collectors;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    public static Identifier PLUGIN_UID = Identifier.fromNamespaceAndPath(CarMod.MODID, "car");

    public static final IRecipeType<CarRecipe> CATEGORY_CAR_WORKSHOP = IRecipeType.create(CarMod.MODID, "car_workshop", CarRecipe.class);
    public static final IRecipeType<PainterRecipe> CATEGORY_PAINTER = IRecipeType.create(CarMod.MODID, "painter", PainterRecipe.class);
    public static final IRecipeType<PainterRecipe> CATEGORY_PAINTER_YELLOW = IRecipeType.create(CarMod.MODID, "painter_yellow", PainterRecipe.class);

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(JEIPlugin.CATEGORY_PAINTER, new ItemStack(ModItems.PAINTER.get()));
        registration.addCraftingStation(JEIPlugin.CATEGORY_PAINTER_YELLOW, new ItemStack(ModItems.PAINTER_YELLOW.get()));
        registration.addCraftingStation(JEIPlugin.CATEGORY_CAR_WORKSHOP, new ItemStack(ModBlocks.CAR_WORKSHOP.get()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(JEIPlugin.CATEGORY_PAINTER, Arrays.stream(ModBlocks.PAINTS).map(paint -> new PainterRecipe(ModItems.PAINTER.get(), paint.get())).toList());
        registration.addRecipes(JEIPlugin.CATEGORY_PAINTER_YELLOW, Arrays.stream(ModBlocks.YELLOW_PAINTS).map(paint -> new PainterRecipe(ModItems.PAINTER_YELLOW.get(), paint.get())).toList());

        registration.addRecipes(JEIPlugin.CATEGORY_CAR_WORKSHOP, CarRecipeBuilder.getAllRecipes());

        registration.addIngredientInfo(new ItemStack(ModItems.PAINTER.get()), VanillaTypes.ITEM_STACK, Component.translatable("description.painter_white"));

        registration.addIngredientInfo(new ItemStack(ModItems.PAINTER_YELLOW.get()), VanillaTypes.ITEM_STACK, Component.translatable("description.painter_yellow"));
        registration.addIngredientInfo(new ItemStack(ModItems.CANISTER.get()), VanillaTypes.ITEM_STACK, Component.translatable("description.canister"));
        registration.addIngredientInfo(new ItemStack(ModItems.REPAIR_KIT.get()), VanillaTypes.ITEM_STACK, Component.translatable("description.repair_kit"));
        registration.addIngredientInfo(new ItemStack(ModBlocks.CRANK.get()), VanillaTypes.ITEM_STACK, Component.translatable("description.crank"));
        registration.addIngredientInfo(new ItemStack(ModBlocks.DYNAMO.get()), VanillaTypes.ITEM_STACK, Component.translatable("description.dynamo"));
        registration.addIngredientInfo(new ItemStack(ModBlocks.GAS_STATION.get()), VanillaTypes.ITEM_STACK, Component.translatable("description.fuel_station"));
        registration.addIngredientInfo(new ItemStack(ModBlocks.GAS_STATION.get()), VanillaTypes.ITEM_STACK, Component.translatable("description.fuel_station_admin"));
        registration.addIngredientInfo(new ItemStack(ModBlocks.TANK.get()), VanillaTypes.ITEM_STACK, Component.translatable("description.tank"));
        registration.addIngredientInfo(new ItemStack(ModBlocks.FLUID_EXTRACTOR.get()), VanillaTypes.ITEM_STACK, Component.translatable("description.fluid_extractor"));
        registration.addIngredientInfo(new ItemStack(ModItems.BATTERY.get()), VanillaTypes.ITEM_STACK, Component.translatable("description.battery"));
        registration.addIngredientInfo(Arrays.stream(ModItems.CONTAINERS).map(e -> new ItemStack(e.get())).collect(Collectors.toList()), VanillaTypes.ITEM_STACK, Component.translatable("description.container"));
        registration.addIngredientInfo(Arrays.stream(ModItems.TANK_CONTAINERS).map(e -> new ItemStack(e.get())).collect(Collectors.toList()), VanillaTypes.ITEM_STACK, Component.translatable("description.tank_container"));
        registration.addIngredientInfo(new ItemStack(ModItems.LICENSE_PLATE.get()), VanillaTypes.ITEM_STACK, Component.translatable("description.license_plate"));
    }

    @Override
    public Identifier getPluginUid() {
        return PLUGIN_UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new PainterRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new PainterRecipeCategoryYellow(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new CarRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

}
