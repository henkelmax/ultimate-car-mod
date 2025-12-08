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
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CarRecipeCategory implements IRecipeCategory<CarRecipe> {

    protected static final Identifier BACKGROUND = Identifier.fromNamespaceAndPath(CarMod.MODID, "textures/gui/jei_car_workshop_crafting.png");

    private static final int RECIPE_WIDTH = 175;
    private static final int RECIPE_HEIGHT = 54;

    private final IGuiHelper helper;
    private final IDrawableStatic background;

    public CarRecipeCategory(IGuiHelper helper) {
        this.helper = helper;
        background = helper.createDrawable(BACKGROUND, 0, 0, getWidth(), getHeight());
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
                    slot.add(VanillaTypes.ITEM_STACK, stack);
                }
            }
        }
    }

    @Override
    public IRecipeType<CarRecipe> getRecipeType() {
        return JEIPlugin.CATEGORY_CAR_WORKSHOP;
    }

    @Override
    public Component getTitle() {
        return ModBlocks.CAR_WORKSHOP.get().getName();
    }

    private EntityTools.SimulatedCarRenderer renderer = new EntityTools.SimulatedCarRenderer();

    @Override
    public void draw(CarRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);
        int x = (int) guiGraphics.pose().m20;
        int y = (int) guiGraphics.pose().m21;
        renderer.render(guiGraphics, getCar(recipe), x + 115, y + 5, x + 174, y + 49, 18);
    }

    public EntityGenericCar getCar(CarRecipe recipe) {
        return recipe.getCachedCar(() -> createCar(Minecraft.getInstance().level, recipe));
    }

    private EntityGenericCar createCar(Level world, CarRecipe recipe) {
        return TileEntityCarWorkshop.createCar(world, recipe.getInputs());
    }

    @Override
    public int getWidth() {
        return RECIPE_WIDTH;
    }

    @Override
    public int getHeight() {
        return RECIPE_HEIGHT;
    }
}
