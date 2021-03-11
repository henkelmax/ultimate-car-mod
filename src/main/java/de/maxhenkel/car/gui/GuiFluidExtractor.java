package de.maxhenkel.car.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraftforge.fluids.FluidStack;

public class GuiFluidExtractor extends ScreenBase<ContainerFluidExtractor> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_fluid_extractor.png");

    private PlayerInventory playerInv;
    private TileEntityFluidExtractor tile;

    public GuiFluidExtractor(ContainerFluidExtractor container, PlayerInventory player, ITextComponent title) {
        super(GUI_TEXTURE, container, player, title);
        this.playerInv = player;
        this.tile = container.getTile();

        imageWidth = 176;
        imageHeight = 139;
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);

        // Title
        font.draw(matrixStack, playerInv.getDisplayName().getVisualOrderText(), 8, this.imageHeight - 96 + 2, FONT_COLOR);
        font.draw(matrixStack, tile.getDisplayName().getVisualOrderText(), 8, 6, FONT_COLOR);

        drawFilter(matrixStack);
    }

    private void drawFilter(MatrixStack matrixStack) {
        IFormattableTextComponent name;

        Fluid f = tile.getFilterFluid();

        if (f == null) {
            name = new StringTextComponent("-");
        } else {
            name = new StringTextComponent(new FluidStack(f, 1).getDisplayName().getString());
        }

        font.draw(matrixStack, new TranslationTextComponent("filter.fluid", name.withStyle(TextFormatting.WHITE)).getVisualOrderText(), 46, 28, FONT_COLOR);
    }

}
