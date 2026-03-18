package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class GuiPainter extends ScreenBase<ContainerPainter> {

    private static final Identifier PAINTER_GUI_TEXTURE = Identifier.fromNamespaceAndPath(CarMod.MODID, "textures/gui/gui_painter.png");

    public GuiPainter(ContainerPainter containerPainter, Inventory playerInventory, Component title) {
        super(PAINTER_GUI_TEXTURE, containerPainter, playerInventory, title, 176, 114);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        super.extractLabels(guiGraphics, mouseX, mouseY);
        guiGraphics.text(font, Component.translatable("gui.painter").getVisualOrderText(), 8, 6, FONT_COLOR, false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
