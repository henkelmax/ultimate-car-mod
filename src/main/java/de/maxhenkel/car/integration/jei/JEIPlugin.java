package de.maxhenkel.car.integration.jei;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.stream.Collectors;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    public static final RecipeType<CarRecipe> CATEGORY_CAR_WORKSHOP = RecipeType.create(Main.MODID, "car_workshop", CarRecipe.class);
    public static final RecipeType<PainterRecipe> CATEGORY_PAINTER = RecipeType.create(Main.MODID, "painter", PainterRecipe.class);
    public static final RecipeType<PainterRecipe> CATEGORY_PAINTER_YELLOW = RecipeType.create(Main.MODID, "painter_yellow", PainterRecipe.class);

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(VanillaTypes.ITEM, new ItemStack(ModItems.PAINTER), JEIPlugin.CATEGORY_PAINTER);
        registration.addRecipeCatalyst(new ItemStack(ModItems.PAINTER_YELLOW), JEIPlugin.CATEGORY_PAINTER_YELLOW);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.CAR_WORKSHOP), JEIPlugin.CATEGORY_CAR_WORKSHOP);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(JEIPlugin.CATEGORY_PAINTER, Arrays.stream(ModBlocks.PAINTS).map(paint -> new PainterRecipe(ModItems.PAINTER, paint)).toList());
        registration.addRecipes(JEIPlugin.CATEGORY_PAINTER_YELLOW, Arrays.stream(ModBlocks.YELLOW_PAINTS).map(paint -> new PainterRecipe(ModItems.PAINTER_YELLOW, paint)).toList());

        registration.addRecipes(JEIPlugin.CATEGORY_CAR_WORKSHOP, CarRecipeBuilder.getAllRecipes());

        registration.addIngredientInfo(new ItemStack(ModItems.PAINTER), VanillaTypes.ITEM, new TranslatableComponent("description.painter_white"));

        registration.addIngredientInfo(new ItemStack(ModItems.PAINTER_YELLOW), VanillaTypes.ITEM, new TranslatableComponent("description.painter_yellow"));
        registration.addIngredientInfo(new ItemStack(ModItems.CANISTER), VanillaTypes.ITEM, new TranslatableComponent("description.canister"));
        registration.addIngredientInfo(new ItemStack(ModItems.REPAIR_KIT), VanillaTypes.ITEM, new TranslatableComponent("description.repair_kit"));
        registration.addIngredientInfo(new ItemStack(ModBlocks.CRANK), VanillaTypes.ITEM, new TranslatableComponent("description.crank"));
        registration.addIngredientInfo(new ItemStack(ModBlocks.DYNAMO), VanillaTypes.ITEM, new TranslatableComponent("description.dynamo"));
        registration.addIngredientInfo(new ItemStack(ModBlocks.GAS_STATION), VanillaTypes.ITEM, new TranslatableComponent("description.fuel_station"));
        registration.addIngredientInfo(new ItemStack(ModBlocks.GAS_STATION), VanillaTypes.ITEM, new TranslatableComponent("description.fuel_station_admin"));
        registration.addIngredientInfo(new ItemStack(ModBlocks.TANK), VanillaTypes.ITEM, new TranslatableComponent("description.tank"));
        registration.addIngredientInfo(new ItemStack(ModBlocks.FLUID_EXTRACTOR), VanillaTypes.ITEM, new TranslatableComponent("description.fluid_extractor"));
        registration.addIngredientInfo(new ItemStack(ModItems.BATTERY), VanillaTypes.ITEM, new TranslatableComponent("description.battery"));
        registration.addIngredientInfo(Arrays.stream(ModItems.CONTAINERS).map(ItemStack::new).collect(Collectors.toList()), VanillaTypes.ITEM, new TranslatableComponent("description.container"));
        registration.addIngredientInfo(Arrays.stream(ModItems.TANK_CONTAINERS).map(ItemStack::new).collect(Collectors.toList()), VanillaTypes.ITEM, new TranslatableComponent("description.tank_container"));
        registration.addIngredientInfo(new ItemStack(ModItems.LICENSE_PLATE), VanillaTypes.ITEM, new TranslatableComponent("description.license_plate"));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Main.MODID, "car");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new PainterRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new PainterRecipeCategoryYellow(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new CarRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

}
