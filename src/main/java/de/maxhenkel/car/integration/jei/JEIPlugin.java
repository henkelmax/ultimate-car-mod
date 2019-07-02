package de.maxhenkel.car.integration.jei;

import java.util.Arrays;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@mezz.jei.api.JeiPlugin
public class JEIPlugin implements IModPlugin {

    public static final ResourceLocation CATEGORY_CAR_WORKSHOP = new ResourceLocation(Main.MODID, "carworkshop");
    public static final ResourceLocation CATEGORY_PAINTER = new ResourceLocation(Main.MODID, "painter");
    public static final ResourceLocation CATEGORY_PAINTER_YELLOW = new ResourceLocation(Main.MODID, "painter_yellow");

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {

    }


    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.CAR_WORKSHOP), JEIPlugin.CATEGORY_CAR_WORKSHOP);
        registration.addRecipeCatalyst(new ItemStack(ModItems.PAINTER), JEIPlugin.CATEGORY_PAINTER);
        registration.addRecipeCatalyst(new ItemStack(ModItems.PAINTER_YELLOW), JEIPlugin.CATEGORY_PAINTER_YELLOW);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        //registration.addRecipes(Arrays.asList(ModBlocks.PAINTS), JEIPlugin.CATEGORY_PAINTER);
        //registration.addRecipes(Arrays.asList(ModBlocks.YELLOW_PAINTS), JEIPlugin.CATEGORY_PAINTER_YELLOW);

    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {
        //TODO descriptions -> https://github.com/mezz/JustEnoughItems/blob/1.14/src/main/java/mezz/jei/plugins/vanilla/VanillaPlugin.java
        /*registry.addIngredientInfo(new ItemStack(ModBlocks.CAR_WORKSHOP), VanillaTypes.ITEM, "description.car_workshop");
        registry.addIngredientInfo(new ItemStack(ModBlocks.CAR_WORKSHOP_OUTTER), VanillaTypes.ITEM,
                "description.car_workshop_outter");

        // Line Painter
        registry.handleRecipes(BlockPaint.class, new PainterRecipeWrapperFactory(), JEIPlugin.CATEGORY_PAINTER);

        registry.addIngredientInfo(new ItemStack(ModItems.PAINTER), VanillaTypes.ITEM, "description.painter_white");

        // Line Painter Yellow
        registry.handleRecipes(BlockPaint.class, new PainterRecipeWrapperFactoryYellow(), JEIPlugin.CATEGORY_PAINTER_YELLOW);

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
*/
        // Container
        //registry.addIngredientInfo(new ItemStack(ModItems.CONTAINER), VanillaTypes.ITEM, "description.container");
        //TODO containers

        // Number plate
        //registry.addIngredientInfo(new ItemStack(ModItems.NUMBER_PLATE), VanillaTypes.ITEM, "description.license_plate");
        //TODO license plate

    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Main.MODID, "car");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new PainterRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new PainterRecipeCategoryYellow(registry.getJeiHelpers().getGuiHelper()));
    }

}
