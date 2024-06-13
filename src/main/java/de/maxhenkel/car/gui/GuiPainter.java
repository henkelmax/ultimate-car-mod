package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiPainter extends ScreenBase<ContainerPainter> {

    private static final ResourceLocation PAINTER_GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(Main.MODID, "textures/gui/gui_painter.png");

    public GuiPainter(ContainerPainter containerPainter, Inventory playerInventory, Component title) {
        super(PAINTER_GUI_TEXTURE, containerPainter, playerInventory, title);
        imageWidth = 176;
        imageHeight = 114;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        guiGraphics.drawString(font, Component.translatable("gui.painter").getVisualOrderText(), 8, 6, FONT_COLOR, false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
