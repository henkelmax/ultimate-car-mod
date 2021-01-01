package de.maxhenkel.car.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiPainter extends ScreenBase<ContainerPainter> {

    private static final ResourceLocation PAINTER_GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_painter.png");

    public GuiPainter(ContainerPainter containerPainter, PlayerInventory playerInventory, ITextComponent title) {
        super(PAINTER_GUI_TEXTURE, containerPainter, playerInventory, title);
        xSize = 176;
        ySize = 114;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
        font.func_238422_b_(matrixStack, new TranslationTextComponent("gui.painter").func_241878_f(), 8, 6, FONT_COLOR);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
