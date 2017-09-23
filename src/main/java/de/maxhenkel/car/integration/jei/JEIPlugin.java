package de.maxhenkel.car.integration.jei;

import java.util.ArrayList;
import java.util.List;
import de.maxhenkel.tools.FileReader;
import de.maxhenkel.car.blocks.BlockPaint;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.car.reciepe.CarCraftingManager;
import de.maxhenkel.car.reciepe.ICarRecipe;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ingredients.IModIngredientRegistration;
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
		registry.addRecipeCategories(new CarRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeHandlers(new CarRecipeHandler());

		List<CarRecipeWrapper> recipes = new ArrayList<CarRecipeWrapper>();
		for (ICarRecipe recipe : CarCraftingManager.getInstance().getRecipeList()) {
			recipes.add(new CarRecipeWrapper(recipe));
		}
		registry.addRecipes(recipes);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.CAR_WORKSHOP), CATEGORY_CAR_WORKSHOP);

		registry.addDescription(new ItemStack(ModBlocks.CAR_WORKSHOP), FileReader.read("car_workshop"));
		registry.addDescription(new ItemStack(ModBlocks.CAR_WORKSHOP_OUTTER), FileReader.read("car_workshop_outter"));

		// Line Painter
		registry.addRecipeCategories(new PainterRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeHandlers(new PainterReciepeHandler());

		List<PainterRecipeWrapper> paints = new ArrayList<PainterRecipeWrapper>();
		for (BlockPaint paint : ModBlocks.PAINTS) {
			paints.add(new PainterRecipeWrapper(paint));
		}
		registry.addRecipes(paints);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModItems.PAINTER), CATEGORY_PAINTER);

		registry.addDescription(new ItemStack(ModItems.PAINTER), FileReader.read("painter"));

		// Line Painter Yellow
		registry.addRecipeCategories(new PainterRecipeCategoryYellow(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeHandlers(new PainterReciepeHandlerYellow());

		List<PainterRecipeWrapperYellow> yellowPaints = new ArrayList<PainterRecipeWrapperYellow>();
		for (BlockPaint paint : ModBlocks.YELLOW_PAINTS) {
			yellowPaints.add(new PainterRecipeWrapperYellow(paint));
		}
		registry.addRecipes(yellowPaints);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModItems.PAINTER_YELLOW), CATEGORY_PAINTER_YELLOW);

		registry.addDescription(new ItemStack(ModItems.PAINTER_YELLOW), FileReader.read("painter_yellow"));

		// Canister
		registry.addDescription(new ItemStack(ModItems.CANISTER), FileReader.read("canister"));

		// Repair Kit
		registry.addDescription(new ItemStack(ModItems.REPAIR_KIT), FileReader.read("repair_kit"));
		
		// Crank
		registry.addDescription(new ItemStack(ModBlocks.CRANK), FileReader.read("crank"));
		
		//Dynamo
		registry.addDescription(new ItemStack(ModBlocks.DYNAMO), FileReader.read("dynamo"));
		
		//Fuel Station
		registry.addDescription(new ItemStack(ModBlocks.FUEL_STATION), FileReader.read("fuel_station"));
		
		//Tank
		registry.addDescription(new ItemStack(ModBlocks.TANK), FileReader.read("tank"));
		
		//Fluid extractor
		registry.addDescription(new ItemStack(ModBlocks.FLUID_EXTRACTOR), FileReader.read("fluid_extractor"));
	}

	@Override
	public void registerIngredients(IModIngredientRegistration reg) {

	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry reg) {

	}

}
