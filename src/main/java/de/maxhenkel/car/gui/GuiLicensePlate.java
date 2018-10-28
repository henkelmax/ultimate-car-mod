package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.items.ItemLicensePlate;
import de.maxhenkel.car.net.MessageEditLicensePlate;
import de.maxhenkel.car.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

public class GuiLicensePlate extends GuiBase {

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_license_plate.png");

    private static final int TITLE_COLOR = Color.WHITE.getRGB();

	private ItemStack plate;
    private EntityPlayer player;

	protected GuiButton buttonSubmit;
	protected GuiButton buttonCancel;

	protected GuiTextField textField;

	public GuiLicensePlate(EntityPlayer player, ItemStack plate) {
		super(new ContainerLicensePlate());
		this.plate=plate;
		this.player=player;
		this.xSize = 176;
		this.ySize = 84;
	}

	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		this.buttonSubmit = addButton(new GuiButton(0, guiLeft + 20, guiTop + ySize - 25, 50, 20,
				new TextComponentTranslation("button.submit").getFormattedText()));
		this.buttonCancel = addButton(new GuiButton(1, guiLeft + xSize - 50 - 15, guiTop + ySize - 25, 50, 20,
				new TextComponentTranslation("button.cancel").getFormattedText()));

        textField = new GuiTextField(0, fontRenderer, guiLeft + 30, guiTop + 30, 116, 16);
        textField.setTextColor(-1);
        textField.setDisabledTextColour(-1);
        textField.setEnableBackgroundDrawing(true);
        textField.setMaxStringLength(10);
        textField.setText(ItemLicensePlate.getText(plate));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
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

        drawCenteredString(fontRenderer, new TextComponentTranslation("gui.license_plate").getFormattedText(),
                width / 2, guiTop + 5, TITLE_COLOR);

	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);

		if (button.equals(buttonCancel)) {
			Minecraft.getMinecraft().displayGuiScreen(null);
		} else if (button.equals(buttonSubmit)) {
			CommonProxy.simpleNetworkWrapper.sendToServer(new MessageEditLicensePlate(player, textField.getText()));
			MessageEditLicensePlate.setItemText(player, textField.getText());
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		GlStateManager.disableLighting();
		GlStateManager.disableBlend();
		textField.drawTextBox();
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (textField.textboxKeyTyped(typedChar, keyCode)) {

		} else {
			super.keyTyped(typedChar, keyCode);
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.textField.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
