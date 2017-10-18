package de.maxhenkel.car.gui;

import java.io.IOException;
import org.lwjgl.input.Keyboard;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import de.maxhenkel.car.net.MessageEditSign;
import de.maxhenkel.car.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiSign extends GuiBase {

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_sign.png");

	private static final int fontColor = 4210752;
	private TileEntitySign sign;

	protected int guiLeft;
	protected int guiTop;

	protected GuiButton buttonSubmit;
	protected GuiButton buttonCancel;

	protected GuiTextField textFront;
	protected GuiTextField textFrontSmall;
	protected GuiTextField textBack;
	protected GuiTextField textBackSmall;

	public GuiSign(TileEntitySign sign) {
		super(new ContainerSign(sign));
		this.sign = sign;
		this.xSize = 176;
		this.ySize = 142;
	}

	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);

		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		this.buttonSubmit = addButton(new GuiButton(0, guiLeft + 20, guiTop + ySize - 25, 50, 20,
				new TextComponentTranslation("button.submit").getFormattedText()));
		this.buttonCancel = addButton(new GuiButton(1, guiLeft + xSize - 50 - 15, guiTop + ySize - 25, 50, 20,
				new TextComponentTranslation("button.cancel").getFormattedText()));

		textFront = initTextField(0, 0);
		textFrontSmall = initTextField(1, 20);
		textBack = initTextField(2, 40);
		textBackSmall = initTextField(3, 60);
	}

	private GuiTextField initTextField(int id, int height) {
		GuiTextField field = new GuiTextField(id, fontRenderer, guiLeft + 54, guiTop + 30 + height, 114, 16);
		field.setTextColor(-1);
		field.setDisabledTextColour(-1);
		field.setEnableBackgroundDrawing(true);
		field.setMaxStringLength(35);
		field.setText(sign.getText(id));
		return field;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		fontRenderer.drawString(new TextComponentTranslation("gui.sign").getUnformattedText(), 54, 10, fontColor);
		
		fontRenderer.drawString(new TextComponentTranslation("gui.sign.front").getUnformattedText(), 8, 47-(fontRenderer.FONT_HEIGHT/2), fontColor);
		fontRenderer.drawString(new TextComponentTranslation("gui.sign.back").getUnformattedText(), 8, 87-(fontRenderer.FONT_HEIGHT/2), fontColor);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// Background
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);

		if (button.equals(buttonCancel)) {
			Minecraft.getMinecraft().displayGuiScreen(null);
		} else if (button.equals(buttonSubmit)) {
			CommonProxy.simpleNetworkWrapper.sendToServer(new MessageEditSign(sign.getPos(), new String[] {
					textFront.getText(), textFrontSmall.getText(), textBack.getText(), textBackSmall.getText() }));
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		GlStateManager.disableLighting();
		GlStateManager.disableBlend();
		textFront.drawTextBox();
		textFrontSmall.drawTextBox();
		textBack.drawTextBox();
		textBackSmall.drawTextBox();
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (textFront.textboxKeyTyped(typedChar, keyCode)) {

		} else if (textFrontSmall.textboxKeyTyped(typedChar, keyCode)) {

		} else if (textBack.textboxKeyTyped(typedChar, keyCode)) {

		} else if (textBackSmall.textboxKeyTyped(typedChar, keyCode)) {

		} else {
			super.keyTyped(typedChar, keyCode);
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.textFront.mouseClicked(mouseX, mouseY, mouseButton);
		this.textFrontSmall.mouseClicked(mouseX, mouseY, mouseButton);
		this.textBack.mouseClicked(mouseX, mouseY, mouseButton);
		this.textBackSmall.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
