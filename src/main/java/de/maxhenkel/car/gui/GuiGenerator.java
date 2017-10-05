package de.maxhenkel.car.gui;

import java.util.ArrayList;
import java.util.List;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiGenerator extends GuiBase {

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
			"textures/gui/gui_generator.png");
	private static final int fontColor = 4210752;

	private IInventory playerInv;
	private TileEntityGenerator tile;

	public GuiGenerator(TileEntityGenerator tile, IInventory playerInv) {
		super(new ContainerGenerator(tile, playerInv));
		this.playerInv = playerInv;
		this.tile = tile;

		xSize = 176;
		ySize = 166;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		// Title
		fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2,
				fontColor);
		fontRenderer.drawString(tile.getDisplayName().getUnformattedText(), 62, 6, fontColor);

		if (mouseX >= guiLeft + 122 && mouseX <= guiLeft + 16 + 122) {
			if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
				List<String> list = new ArrayList<String>();
				list.add(new TextComponentTranslation("tooltip.energy", tile.getField(0))
						.getFormattedText());
				drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop);
			}
		}
		
		if (mouseX >= guiLeft + 39 && mouseX <= guiLeft + 16 + 39) {
			if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
				List<String> list = new ArrayList<String>();
				list.add(new TextComponentTranslation("tooltip.fuel", tile.getField(1))
						.getFormattedText());
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

		drawEnergy();
		drawFluid();
	}

	public void drawEnergy() {
		float perc = getEnergy();

		int texX = 176;
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

	public void drawFluid() {
		float perc = getFluid();

		int texX = 192;
		int texY = 17;
		int texW = 16;
		int texH = 57;
		int targetX = 39;
		int targetY = 8;

		int scHeight = (int) (texH * (1 - perc));
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
	}

	public float getEnergy() {
		return ((float) tile.getField(0)) / ((float) tile.maxStorage);
	}

	public float getFluid() {
		return ((float) tile.getField(1)) / ((float) tile.maxMillibuckets);
	}

}
