package de.maxhenkel.car.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import de.maxhenkel.car.Main;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiPainter extends GuiBase {

    private static final ResourceLocation PAINTER_GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_painter.png");
    private static final int fontColor = 4210752;

    public GuiPainter(ContainerPainter containerPainter, PlayerInventory playerInventory, boolean isYellow) {
        super(PAINTER_GUI_TEXTURE, containerPainter, playerInventory, new TranslationTextComponent("item.car.painter" + (isYellow ? "_yellow" : "")));
        xSize = 176;
        ySize = 96;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        font.drawString(new TranslationTextComponent("gui.painter").getFormattedText(), 8, 6, fontColor);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(PAINTER_GUI_TEXTURE);
        int i = this.guiLeft;
        int j = this.guiTop;
        blit(i, j, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
