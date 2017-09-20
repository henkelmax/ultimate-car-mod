package de.maxhenkel.car.gui;

import java.util.ArrayList;
import java.util.List;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntitySplitTank;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiSplitTank extends GuiContainer {

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
			"textures/gui/gui_split_tank.png");
	private static final int fontColor = 4210752;

	private IInventory playerInv;
	private TileEntitySplitTank tile;

	public GuiSplitTank(TileEntitySplitTank tile, IInventory playerInv) {
		super(new ContainerSplitTank(tile, playerInv));
		this.playerInv = playerInv;
		this.tile = tile;

		xSize = 176;
		ySize = 166;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		// Title
		this.fontRendererObj.drawString(playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2,
				fontColor);
		
		if (mouseX >= guiLeft + 50 && mouseX <= guiLeft + 16 + 50) {
			if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
				List<String> list = new ArrayList<String>();
				list.add(new TextComponentTranslation("tooltip.mix", tile.getField(0))
						.getFormattedText());
				drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop);
			}
		}
		
		if (mouseX >= guiLeft + 120 && mouseX <= guiLeft + 16 + 120) {
			if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
				List<String> list = new ArrayList<String>();
				list.add(new TextComponentTranslation("tooltip.glycerin", tile.getField(2))
						.getFormattedText());
				drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop);
			}
		}
		
		if (mouseX >= guiLeft + 141 && mouseX <= guiLeft + 16 + 141) {
			if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
				List<String> list = new ArrayList<String>();
				list.add(new TextComponentTranslation("tooltip.bio_diesel", tile.getField(1))
						.getFormattedText());
				drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop);
			}
		}
		
		if (mouseX >= guiLeft + 79 && mouseX <= guiLeft + 24 + 79) {
			if (mouseY >= guiTop + 34 && mouseY <= guiTop + 17 + 34) {
				List<String> list = new ArrayList<String>();
				list.add(new TextComponentTranslation("tooltip.progress", ((int)(getProgress()*100F))).getFormattedText());
				drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		drawProgress();
		drawMix();
		drawBioDiesel();
		drawGlycerin();
		
	}
	
	public void drawGlycerin() {
		float perc = getGlycerin();

		int texX = 192;
		int texY = 17;
		int texW = 16;
		int texH = 57;
		int targetX = 120;
		int targetY = 8;

		int scHeight = (int) (texH * (1 - perc));
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
	}
	
	public void drawBioDiesel() {
		float perc = getBioDiesel();

		int texX = 208;
		int texY = 17;
		int texW = 16;
		int texH = 57;
		int targetX = 141;
		int targetY = 8;

		int scHeight = (int) (texH * (1 - perc));
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
	}

	public void drawMix() {
		float perc = getMix();

		int texX = 176;
		int texY = 17;
		int texW = 16;
		int texH = 57;
		int targetX = 50;
		int targetY = 8;

		int scHeight = (int) (texH * (1 - perc));
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
	}

	public void drawProgress() {
		float perc = getProgress();

		int texX = 176;
		int texY = 0;
		int texW = 24;
		int texH = 17;
		int targetX = 79;
		int targetY = 34;

		int scWidth = (int) (texW * perc);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i + targetX, j + targetY, texX, texY, scWidth, texH);
	}

	public float getMix() {
		return ((float) tile.getField(0)) / ((float) tile.maxMix);
	}

	public float getBioDiesel() {
		return ((float) tile.getField(1)) / ((float) tile.maxBioDiesel);
	}

	public float getGlycerin() {
		return ((float) tile.getField(2)) / ((float) tile.maxGlycerin);
	}

	public float getProgress() {
		if (tile.getField(3) == 0) {
			return 0;
		}

		int time = tile.generatingTime - tile.getField(3);
		return ((float) time) / ((float) tile.generatingTime);
	}

}
