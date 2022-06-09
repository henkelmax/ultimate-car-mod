package de.maxhenkel.car.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiPainter extends ScreenBase<ContainerPainter> {

    private static final ResourceLocation PAINTER_GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_painter.png");

    public GuiPainter(ContainerPainter containerPainter, Inventory playerInventory, Component title) {
        super(PAINTER_GUI_TEXTURE, containerPainter, playerInventory, title);
        imageWidth = 176;
        imageHeight = 114;
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);
        font.draw(matrixStack, Component.translatable("gui.painter").getVisualOrderText(), 8, 6, FONT_COLOR);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
