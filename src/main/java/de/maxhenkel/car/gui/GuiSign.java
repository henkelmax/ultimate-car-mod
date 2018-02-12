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

	protected GuiButton buttonSwitch;
	protected GuiButton buttonSubmit;
	protected GuiButton buttonCancel;

	protected GuiTextField text1;
	protected GuiTextField text2;
	protected GuiTextField text3;
	protected GuiTextField text4;
	
	protected String[] text;
	
	protected boolean front=true;

	public GuiSign(TileEntitySign sign) {
		super(new ContainerSign(sign));
		this.sign = sign;
		this.xSize = 176;
		this.ySize = 142;
		this.text=sign.getText();
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
		this.buttonSwitch = addButton(new GuiButton(2, guiLeft + 5, guiTop + 49 +10, 46, 20,
				new TextComponentTranslation("button.back").getFormattedText()));

		text1 = initTextField(0, 0);
		text2 = initTextField(1, 20);
		text3 = initTextField(2, 40);
		text4 = initTextField(3, 60);
	}

	private GuiTextField initTextField(int id, int height) {
		GuiTextField field = new GuiTextField(id, fontRenderer, guiLeft + 54, guiTop + 30 + height, 114, 16);
		field.setTextColor(-1);
		field.setDisabledTextColour(-1);
		field.setEnableBackgroundDrawing(true);
		field.setMaxStringLength(20);
		field.setText(text[id]);
		return field;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		String s;
		
		if(front) {
			s=new TextComponentTranslation("gui.sign.front").getFormattedText();
		}else {
			s=new TextComponentTranslation("gui.sign.back").getFormattedText();
		}
		
		fontRenderer.drawString(new TextComponentTranslation("gui.sign", s).getFormattedText(), 54, 10, fontColor);
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
			save();
			CommonProxy.simpleNetworkWrapper.sendToServer(new MessageEditSign(sign.getPos(), text));
			Minecraft.getMinecraft().displayGuiScreen(null);
		}else if (button.equals(buttonSwitch)) {
			save();
			front=!front;
			
			if(front) {
				text1.setText(text[0]);
				text2.setText(text[1]);
				text3.setText(text[2]);
				text4.setText(text[3]);
				buttonSwitch.displayString=new TextComponentTranslation("button.back").getFormattedText();
			}else {
				text1.setText(text[4]);
				text2.setText(text[5]);
				text3.setText(text[6]);
				text4.setText(text[7]);
				buttonSwitch.displayString=new TextComponentTranslation("button.front").getFormattedText();
			}
		}
	}
	
	private void save() {
		if(front) {
			text[0]=text1.getText();
			text[1]=text2.getText();
			text[2]=text3.getText();
			text[3]=text4.getText();
		}else {
			text[4]=text1.getText();
			text[5]=text2.getText();
			text[6]=text3.getText();
			text[7]=text4.getText();
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		GlStateManager.disableLighting();
		GlStateManager.disableBlend();
		text1.drawTextBox();
		text2.drawTextBox();
		text3.drawTextBox();
		text4.drawTextBox();
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (text1.textboxKeyTyped(typedChar, keyCode)) {

		} else if (text2.textboxKeyTyped(typedChar, keyCode)) {

		} else if (text3.textboxKeyTyped(typedChar, keyCode)) {

		} else if (text4.textboxKeyTyped(typedChar, keyCode)) {

		} else {
			super.keyTyped(typedChar, keyCode);
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.text1.mouseClicked(mouseX, mouseY, mouseButton);
		this.text2.mouseClicked(mouseX, mouseY, mouseButton);
		this.text3.mouseClicked(mouseX, mouseY, mouseButton);
		this.text4.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
