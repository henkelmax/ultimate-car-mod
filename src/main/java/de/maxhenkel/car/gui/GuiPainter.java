package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiPainter extends GuiBase<ContainerPainter> {

    private static final ResourceLocation PAINTER_GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_painter.png");

    public GuiPainter(ContainerPainter containerPainter, PlayerInventory playerInventory, ITextComponent title) {
        super(PAINTER_GUI_TEXTURE, containerPainter, playerInventory, title);
        xSize = 176;
        ySize = 96;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        font.drawString(new TranslationTextComponent("gui.painter").getFormattedText(), 8, 6, FONT_COLOR);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
