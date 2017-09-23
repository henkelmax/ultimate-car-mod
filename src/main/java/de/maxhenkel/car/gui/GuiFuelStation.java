package de.maxhenkel.car.gui;

import java.awt.Color;
import java.io.IOException;

import com.mojang.realmsclient.gui.ChatFormatting;

import de.maxhenkel.car.Main;
import de.maxhenkel.tools.MathTools;
import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import de.maxhenkel.car.entity.car.base.EntityCarFuelBase;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;

public class GuiFuelStation extends GuiContainer {

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
			"textures/gui/gui_fuelstation.png");

	private TileEntityFuelStation fuelStation;

	protected int xSize = 248;
	protected int ySize = 166;

	private static final int TITLE_COLOR = Color.WHITE.getRGB();
	private static final int FONT_COLOR = Color.DARK_GRAY.getRGB();
	private static final ChatFormatting INFO_COLOR = ChatFormatting.WHITE;

	protected int guiLeft;
	protected int guiTop;

	protected GuiButton buttonStart;
	protected GuiButton buttonStop;

	public GuiFuelStation(TileEntityFuelStation fuelStation) {
		super(new ContainerFuelStation(fuelStation));
		this.fuelStation = fuelStation;

	}

	@Override
	public void initGui() {
		super.initGui();

		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		this.buttonStart = addButton(new GuiButton(0, guiLeft + 20, guiTop + ySize - 20 - 15, 75, 20,
				new TextComponentTranslation("button.start").getFormattedText()));
		this.buttonStop = addButton(new GuiButton(1, guiLeft + xSize - 75 - 15, guiTop + ySize - 20 - 15, 75, 20,
				new TextComponentTranslation("button.stop").getFormattedText()));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// Background
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		// buttons
		buttonStart.enabled = !fuelStation.isFueling();
		buttonStop.enabled = fuelStation.isFueling();

		// text
		drawCenteredString(fontRendererObj, new TextComponentTranslation("gui.fuelstation").getFormattedText(),
				width / 2, guiTop + 10, TITLE_COLOR);

		// Car name
		EntityCarFuelBase car = fuelStation.getCarInFront();

		drawCarName(car);
		drawCarFuel(car);
		drawRefueled();
		drawBuffer();
	}

	private void drawCarName(EntityCarFuelBase car) {
		String carText;
		if (car == null) {
			carText = new TextComponentTranslation("fuelstation.nocar").getFormattedText();
		} else {
			carText = new TextComponentTranslation("fuelstation.carinfo",
					INFO_COLOR + car.getCarName().getFormattedText()).getFormattedText();
		}

		fontRendererObj.drawString(carText, (width / 2) - 10, guiTop + 40, FONT_COLOR);
	}

	private void drawCarFuel(EntityCarFuelBase car) {
		String fuelText;
		if (car == null) {
			fuelText = new TextComponentTranslation("fuelstation.fuel", INFO_COLOR + "-", INFO_COLOR + "-")
					.getFormattedText();
		} else {
			fuelText = new TextComponentTranslation("fuelstation.fuel",
					INFO_COLOR + String.valueOf(MathTools.round(car.getFuel(), 2)),
					INFO_COLOR + String.valueOf(MathTools.round(car.getMaxFuel(), 2))).getFormattedText();
		}

		fontRendererObj.drawString(fuelText, (width / 2) - 10, guiTop + 50, FONT_COLOR);
	}

	private void drawRefueled() {
		String refueledText = new TextComponentTranslation("fuelstation.refueled",
				INFO_COLOR + String.valueOf(MathTools.round(fuelStation.getField(0), 2))).getFormattedText();

		fontRendererObj.drawString(refueledText, (width / 2) - 10, guiTop + 60, FONT_COLOR);
	}

	private void drawBuffer() {
		FluidStack stack = fuelStation.getStorage();

		if (stack == null) {

			String bufferText = new TextComponentTranslation("fuelstation.fuel_buffer_empty").getFormattedText();

			fontRendererObj.drawString(bufferText, (width / 2) - 10, guiTop + 70, FONT_COLOR);

			return;
		}

		int amount = fuelStation.getField(1);
		String fluidName = stack.getLocalizedName();

		String bufferText = new TextComponentTranslation("fuelstation.fuel_buffer_fuel", INFO_COLOR + fluidName)
				.getFormattedText();

		fontRendererObj.drawString(bufferText, (width / 2) - 10, guiTop + 70, FONT_COLOR);

		String amountText = new TextComponentTranslation("fuelstation.fuel_buffer_amount",
				INFO_COLOR + String.valueOf(amount)).getFormattedText();

		fontRendererObj.drawString(amountText, (width / 2) - 10, guiTop + 80, FONT_COLOR);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);

		if (button.equals(buttonStart)) {
			fuelStation.setFueling(true);
			fuelStation.sendStartFuelPacket(true);
		} else if (button.equals(buttonStop)) {
			fuelStation.setFueling(false);
			fuelStation.sendStartFuelPacket(false);
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
