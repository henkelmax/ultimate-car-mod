package de.maxhenkel.car.gui;

import java.util.ArrayList;
import java.util.List;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityBackmixReactor;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiBackmixReactor extends GuiContainer {

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
			"textures/gui/gui_backmix_reactor.png");
	private static final int fontColor = 4210752;

	private IInventory playerInv;
	private TileEntityBackmixReactor tile;

	public GuiBackmixReactor(IInventory playerInv, TileEntityBackmixReactor tile) {
		super(new ContainerBackmixReactor(playerInv, tile));
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

		if (mouseX >= guiLeft + 11 && mouseX <= guiLeft + 16 + 11) {
			if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
				List<String> list = new ArrayList<String>();
				list.add(new TextComponentTranslation("tooltip.energy", tile.getField(0))
						.getFormattedText());
				drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop);
			}
		}
		
		if (mouseX >= guiLeft + 33 && mouseX <= guiLeft + 16 + 33) {
			if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
				List<String> list = new ArrayList<String>();
				list.add(new TextComponentTranslation("tooltip.oil", tile.getField(1))
						.getFormattedText());
				drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop);
			}
		}
		
		if (mouseX >= guiLeft + 55 && mouseX <= guiLeft + 16 + 55) {
			if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
				List<String> list = new ArrayList<String>();
				list.add(new TextComponentTranslation("tooltip.methanol", tile.getField(2))
						.getFormattedText());
				drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop);
			}
		}
		
		if (mouseX >= guiLeft + 122 && mouseX <= guiLeft + 16 + 122) {
			if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
				List<String> list = new ArrayList<String>();
				list.add(new TextComponentTranslation("tooltip.mix", tile.getField(3))
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
		drawEnergy();
		drawCanola();
		drawMethanol();
		drawMix();
	}

	public void drawEnergy() {
		float perc = getEnergy();

		int texX = 176;
		int texY = 17;
		int texW = 16;
		int texH = 57;
		int targetX = 11;
		int targetY = 8;

		int scHeight = (int) (texH * (1 - perc));
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
	}

	public void drawCanola() {
		float perc = getCanola();

		int texX = 192;
		int texY = 17;
		int texW = 16;
		int texH = 57;
		int targetX = 33;
		int targetY = 8;

		int scHeight = (int) (texH * (1 - perc));
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
	}

	public void drawMethanol() {
		float perc = getMethanol();

		int texX = 208;
		int texY = 17;
		int texW = 16;
		int texH = 57;
		int targetX = 55;
		int targetY = 8;

		int scHeight = (int) (texH * (1 - perc));
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
	}

	public void drawMix() {
		float perc = getMix();

		int texX = 224;
		int texY = 17;
		int texW = 16;
		int texH = 57;
		int targetX = 122;
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

	public float getEnergy() {
		return ((float) tile.getField(0)) / ((float) tile.maxStorage);
	}

	public float getCanola() {
		return ((float) tile.getField(1)) / ((float) tile.maxCanola);
	}

	public float getMethanol() {
		return ((float) tile.getField(2)) / ((float) tile.maxMethanol);
	}

	public float getMix() {
		return ((float) tile.getField(3)) / ((float) tile.maxMix);
	}

	public float getProgress() {
		if (tile.getField(4) == 0) {
			return 0;
		}

		int time = tile.generatingTime - tile.getField(4);
		return ((float) time) / ((float) tile.generatingTime);
	}

}
