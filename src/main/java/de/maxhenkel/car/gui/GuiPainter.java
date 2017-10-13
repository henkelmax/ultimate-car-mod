package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiPainter extends GuiBase{

	private static final ResourceLocation PAINTER_GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_painter.png");
	private static final int fontColor=4210752;
	
	public GuiPainter(EntityPlayer player, boolean isYellow) {
		super(new ContainerPainter(player, isYellow));
		xSize=176;
		ySize=96;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		fontRenderer.drawString(new TextComponentTranslation("gui.painter").getUnformattedText(), 8, 6, fontColor);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(PAINTER_GUI_TEXTURE);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
}
