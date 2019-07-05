package de.maxhenkel.car.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import de.maxhenkel.car.net.MessageEditSign;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiSign extends GuiBase<ContainerSign> {

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

    public GuiSign(ContainerSign containerSign, ITextComponent title) {
        super(GUI_TEXTURE, containerSign, null, title);
        this.sign = containerSign.getSign();
        this.xSize = 176;
        this.ySize = 142;
        this.text = sign.getSignText();
    }

    @Override
    protected void init() {
        super.init();
        minecraft.keyboardListener.enableRepeatEvents(true);

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
                buttonSwitch.setMessage(new TranslationTextComponent("button.back").getFormattedText());
            } else {
                text1.setText(text[4]);
                text2.setText(text[5]);
                text3.setText(text[6]);
                text4.setText(text[7]);
                buttonSwitch.setMessage(new TranslationTextComponent("button.front").getFormattedText());
            }
        }));

        text1 = initTextField(0, 0);
        text2 = initTextField(1, 20);
        text3 = initTextField(2, 40);
        text4 = initTextField(3, 60);

        setFocused(text1);
    }

    private TextFieldWidget initTextField(int id, int height) {
        TextFieldWidget field = new TextFieldWidget(font, guiLeft + 54, guiTop + 30 + height, 114, 16, "");
        field.setTextColor(-1);
        field.setDisabledTextColour(-1);
        field.setEnableBackgroundDrawing(true);
        field.setMaxStringLength(20);
        field.setText(text[id]);
        children.add(field);
        return field;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);

        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        text1.render(mouseX, mouseY, partialTicks);
        text2.render(mouseX, mouseY, partialTicks);
        text3.render(mouseX, mouseY, partialTicks);
        text4.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        super.drawGuiContainerForegroundLayer(x, y);

        String s;

        if (front) {
            s = new TranslationTextComponent("gui.sign.front").getFormattedText();
        } else {
            s = new TranslationTextComponent("gui.sign.back").getFormattedText();
        }

        font.drawString(new TranslationTextComponent("gui.sign", s).getFormattedText(), 54, 10, fontColor);
    }

    @Override
    public void removed() {
        super.removed();
        minecraft.keyboardListener.enableRepeatEvents(false);
    }

    @Override
    public boolean keyPressed(int key, int a, int b) {
        if (key == 256) {
            minecraft.player.closeScreen();
        }

        return true;
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
    public void resize(Minecraft mc, int x, int y) {
        String txt1 = text1.getText();
        String txt2 = text2.getText();
        String txt3 = text3.getText();
        String txt4 = text4.getText();
        init(mc, x, y);
        text1.setText(txt1);
        text2.setText(txt2);
        text3.setText(txt3);
        text4.setText(txt4);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
