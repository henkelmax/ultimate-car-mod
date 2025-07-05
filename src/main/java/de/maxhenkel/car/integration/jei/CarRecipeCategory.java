package de.maxhenkel.car.integration.jei;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.tools.EntityTools;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class CarRecipeCategory implements IRecipeCategory<CarRecipe> {

    @Nullable
    private EntityGenericCar car;
    private IGuiHelper helper;

    private static final int RECIPE_WIDTH = 175;
    private static final int RECIPE_HEIGHT = 54;

    public CarRecipeCategory(IGuiHelper helper) {
        this.helper = helper;
    }

    @Override
    public RecipeType<CarRecipe> getRecipeType() {
        return JEIPlugin.CATEGORY_CAR_WORKSHOP;
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/gui/jei_car_workshop_crafting.png"), 0, 0, RECIPE_WIDTH, RECIPE_HEIGHT);
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CAR_WORKSHOP.get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CarRecipe recipe, IFocusGroup focuses) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 5; x++) {
                IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.INPUT, x * 18 + 1, y * 18 + 1);
                int index = x + y * 5;
                if (index >= recipe.getInputs().size()) {
                    continue;
                }
                ItemStack stack = recipe.getInputs().get(index);
                if (!stack.isEmpty()) {
                    slot.addIngredient(VanillaTypes.ITEM_STACK, stack);
                }
            }
        }
    }

    @Override
    public Component getTitle() {
        return ModBlocks.CAR_WORKSHOP.get().getName();
    }

    private EntityTools.SimulatedCarRenderer renderer = new EntityTools.SimulatedCarRenderer();

    @Override
    public void draw(CarRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        renderer.render(guiGraphics, getCar(recipe), RECIPE_WIDTH - 30, RECIPE_HEIGHT - 54 / 4, 18);
    }

    public EntityGenericCar getCar(CarRecipe recipe) {
        if (car == null) {
            car = createCar(Minecraft.getInstance().level, recipe);
        }
        return car;
    }

    private EntityGenericCar createCar(Level world, CarRecipe recipe) {
        return TileEntityCarWorkshop.createCar(world, recipe.getInputs());
    }

}
