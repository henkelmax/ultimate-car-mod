package de.maxhenkel.car.integration.jei;

import java.util.ArrayList;
import java.util.List;

import de.maxhenkel.car.blocks.BlockPaint;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.car.reciepe.ICarRecipe;
import de.maxhenkel.car.registries.CarCraftingRegistry;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    public static final String CATEGORY_CAR_WORKSHOP = "car.carworkshop";
    public static final String CATEGORY_PAINTER = "car.painter";
    public static final String CATEGORY_PAINTER_YELLOW = "car.painter_yellow";

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {

    }

    @Override
    public void register(IModRegistry registry) {
        // Car Workshop
        registry.handleRecipes(ICarRecipe.class, new CarRecipeWrapperFactory(), JEIPlugin.CATEGORY_CAR_WORKSHOP);

        List<CarRecipeWrapper> recipes = new ArrayList<CarRecipeWrapper>();
        for (ICarRecipe recipe : CarCraftingRegistry.REGISTRY) {
            recipes.add(new CarRecipeWrapper(recipe));
        }
        registry.addRecipes(recipes, JEIPlugin.CATEGORY_CAR_WORKSHOP);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.CAR_WORKSHOP), JEIPlugin.CATEGORY_CAR_WORKSHOP);
        registry.addIngredientInfo(new ItemStack(ModBlocks.CAR_WORKSHOP), VanillaTypes.ITEM, "description.car_workshop");
        registry.addIngredientInfo(new ItemStack(ModBlocks.CAR_WORKSHOP_OUTTER), VanillaTypes.ITEM,
                "description.car_workshop_outter");

        // Line Painter
        registry.handleRecipes(BlockPaint.class, new PainterRecipeWrapperFactory(), JEIPlugin.CATEGORY_PAINTER);

        List<PainterRecipeWrapper> paints = new ArrayList<PainterRecipeWrapper>();
        for (BlockPaint paint : ModBlocks.PAINTS) {
            paints.add(new PainterRecipeWrapper(paint));
        }
        registry.addRecipes(paints, JEIPlugin.CATEGORY_PAINTER);
        registry.addRecipeCatalyst(new ItemStack(ModItems.PAINTER), JEIPlugin.CATEGORY_PAINTER);

        registry.addIngredientInfo(new ItemStack(ModItems.PAINTER), VanillaTypes.ITEM, "description.painter_white");

        // Line Painter Yellow
        registry.handleRecipes(BlockPaint.class, new PainterRecipeWrapperFactoryYellow(), JEIPlugin.CATEGORY_PAINTER_YELLOW);

        List<PainterRecipeWrapperYellow> paintsY = new ArrayList<PainterRecipeWrapperYellow>();
        for (BlockPaint paint : ModBlocks.YELLOW_PAINTS) {
            paintsY.add(new PainterRecipeWrapperYellow(paint));
        }
        registry.addRecipes(paintsY, JEIPlugin.CATEGORY_PAINTER_YELLOW);
        registry.addRecipeCatalyst(new ItemStack(ModItems.PAINTER_YELLOW), JEIPlugin.CATEGORY_PAINTER_YELLOW);

        registry.addIngredientInfo(new ItemStack(ModItems.PAINTER_YELLOW), VanillaTypes.ITEM, "description.painter_yellow");


        // Canister
        registry.addIngredientInfo(new ItemStack(ModItems.CANISTER), VanillaTypes.ITEM, "description.canister");

        // Repair Kit
        registry.addIngredientInfo(new ItemStack(ModItems.REPAIR_KIT), VanillaTypes.ITEM, "description.repair_kit");

        // Crank
        registry.addIngredientInfo(new ItemStack(ModBlocks.CRANK), VanillaTypes.ITEM, "description.crank");

        // Dynamo
        registry.addIngredientInfo(new ItemStack(ModBlocks.DYNAMO), VanillaTypes.ITEM, "description.dynamo");

        // Fuel Station
        registry.addIngredientInfo(new ItemStack(ModBlocks.FUEL_STATION), VanillaTypes.ITEM, "description.fuel_station");

        // Tank
        registry.addIngredientInfo(new ItemStack(ModBlocks.TANK), VanillaTypes.ITEM, "description.tank");

        // Fluid extractor
        registry.addIngredientInfo(new ItemStack(ModBlocks.FLUID_EXTRACTOR), VanillaTypes.ITEM,
                "description.fluid_extractor");

        // Battery
        registry.addIngredientInfo(new ItemStack(ModItems.BATTERY), VanillaTypes.ITEM, "description.battery");

        // Container
        registry.addIngredientInfo(new ItemStack(ModItems.CONTAINER), VanillaTypes.ITEM, "description.container");

        // Number plate
        registry.addIngredientInfo(new ItemStack(ModItems.NUMBER_PLATE), VanillaTypes.ITEM, "description.number_plate");
    }

    @Override
    public void registerIngredients(IModIngredientRegistration reg) {

    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry reg) {

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new CarRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new PainterRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new PainterRecipeCategoryYellow(registry.getJeiHelpers().getGuiHelper()));
    }

}
