package de.maxhenkel.car.gui;

import java.io.IOException;

import com.mojang.blaze3d.platform.GlStateManager;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import de.maxhenkel.car.net.MessageEditSign;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiSign extends GuiBase {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_sign.png");

    private static final int fontColor = 4210752;
    private TileEntitySign sign;

    protected int guiLeft;
    protected int guiTop;

    protected Button buttonSwitch;
    protected Button buttonSubmit;
    protected Button buttonCancel;

    protected TextFieldWidget text1;
    protected TextFieldWidget text2;
    protected TextFieldWidget text3;
    protected TextFieldWidget text4;

    protected String[] text;

    protected boolean front = true;

    public GuiSign(ContainerSign containerSign) {
        super(GUI_TEXTURE, containerSign, null, new TranslationTextComponent("block.car.sign"));
        this.sign = containerSign.getSign();
        this.xSize = 176;
        this.ySize = 142;
        this.text = sign.getText();
    }

    @Override
    protected void init() {
        super.init();

        //Keyboard.enableRepeatEvents(true);

        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        this.buttonSubmit = addButton(new Button(guiLeft + 20, guiTop + ySize - 25, 50, 20,
                new TranslationTextComponent("button.submit").getFormattedText(), button -> {
            save();
            Main.SIMPLE_CHANNEL.sendToServer(new MessageEditSign(sign.getPos(), text));
            Minecraft.getInstance().displayGuiScreen(null);
        }));
        this.buttonCancel = addButton(new Button(guiLeft + xSize - 50 - 15, guiTop + ySize - 25, 50, 20,
                new TranslationTextComponent("button.cancel").getFormattedText(), button -> {
            Minecraft.getInstance().displayGuiScreen(null);
        }));
        this.buttonSwitch = addButton(new Button(guiLeft + 5, guiTop + 49 + 10, 46, 20,
                new TranslationTextComponent("button.back").getFormattedText(), button -> {
            save();
            front = !front;

            if (front) {
                text1.setText(text[0]);
                text2.setText(text[1]);
                text3.setText(text[2]);
                text4.setText(text[3]);
                buttonSwitch.setMessage(new TranslationTextComponent("button.back").getFormattedText()); //TODO check
            } else {
                text1.setText(text[4]);
                text2.setText(text[5]);
                text3.setText(text[6]);
                text4.setText(text[7]);
                buttonSwitch.setMessage(new TranslationTextComponent("button.front").getFormattedText()); //TODO check
            }
        }));

        text1 = initTextField(0, 0);
        text2 = initTextField(1, 20);
        text3 = initTextField(2, 40);
        text4 = initTextField(3, 60);
    }

    private TextFieldWidget initTextField(int id, int height) {
        TextFieldWidget field = new TextFieldWidget(font, guiLeft + 54, guiTop + 30 + height, 114, 16, ""); //TODO name
        field.setTextColor(-1);
        field.setDisabledTextColour(-1);
        field.setEnableBackgroundDrawing(true);
        field.setMaxStringLength(20);
        field.setText(text[id]);
        children.add(field);
        func_212928_a(field);
        return field;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        String s;

        if (front) {
            s = new TranslationTextComponent("gui.sign.front").getFormattedText();
        } else {
            s = new TranslationTextComponent("gui.sign.back").getFormattedText();
        }

        font.drawString(new TranslationTextComponent("gui.sign", s).getFormattedText(), 54, 10, fontColor);
    }

    @Override
    public void onClose() {
        super.onClose();
        //Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        // Background
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int i = this.guiLeft;
        int j = this.guiTop;
        blit(i, j, 0, 0, this.xSize, this.ySize);

    }

    private void save() {
        if (front) {
            text[0] = text1.getText();
            text[1] = text2.getText();
            text[2] = text3.getText();
            text[3] = text4.getText();
        } else {
            text[4] = text1.getText();
            text[5] = text2.getText();
            text[6] = text3.getText();
            text[7] = text4.getText();
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
