package de.maxhenkel.car.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import de.maxhenkel.car.net.MessageEditSign;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

public class GuiSign extends ScreenBase<ContainerSign> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_sign.png");

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

        guiLeft = (width - xSize) / 2;
        guiTop = (height - ySize) / 2;

        minecraft.keyboardListener.enableRepeatEvents(true);

        text1 = initTextField(0, 0);
        text2 = initTextField(1, 20);
        text3 = initTextField(2, 40);
        text4 = initTextField(3, 60);

        setFocusedDefault(text1);

        buttonSubmit = addButton(new Button(guiLeft + 20, guiTop + ySize - 25, 50, 20, new TranslationTextComponent("button.car.submit"), button -> {
            save();
            Main.SIMPLE_CHANNEL.sendToServer(new MessageEditSign(sign.getPos(), text));
            Minecraft.getInstance().displayGuiScreen(null);
        }));
        buttonCancel = addButton(new Button(guiLeft + xSize - 50 - 15, guiTop + ySize - 25, 50, 20, new TranslationTextComponent("button.car.cancel"), button -> {
            Minecraft.getInstance().displayGuiScreen(null);
        }));
        buttonSwitch = addButton(new Button(guiLeft + 5, guiTop + 49 + 10, 46, 20, new TranslationTextComponent("button.car.back"), button -> {
            save();
            front = !front;

            if (front) {
                text1.setText(text[0]);
                text2.setText(text[1]);
                text3.setText(text[2]);
                text4.setText(text[3]);
                buttonSwitch.setMessage(new TranslationTextComponent("button.car.back"));
            } else {
                text1.setText(text[4]);
                text2.setText(text[5]);
                text3.setText(text[6]);
                text4.setText(text[7]);
                buttonSwitch.setMessage(new TranslationTextComponent("button.car.front"));
            }
        }));
    }

    private TextFieldWidget initTextField(int id, int height) {
        TextFieldWidget field = new TextFieldWidget(font, guiLeft + 54, guiTop + 30 + height, 114, 16, new StringTextComponent(""));
        field.setTextColor(-1);
        field.setDisabledTextColour(-1);
        field.setEnableBackgroundDrawing(true);
        field.setMaxStringLength(20);
        field.setText(text[id]);
        addButton(field);
        return field;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);

        String s;
        if (front) {
            s = new TranslationTextComponent("gui.sign.front").getString();
        } else {
            s = new TranslationTextComponent("gui.sign.back").getString();
        }

        font.func_238422_b_(matrixStack, new TranslationTextComponent("gui.sign", s).func_241878_f(), 54, 10, FONT_COLOR);
    }

    @Override
    public void onClose() {
        super.onClose();
        minecraft.keyboardListener.enableRepeatEvents(false);
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers) {
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            minecraft.player.closeScreen();
            return true;
        }

        return text1.keyPressed(key, scanCode, modifiers) ||
                text1.canWrite() ||
                text2.keyPressed(key, scanCode, modifiers) ||
                text2.canWrite() ||
                text3.keyPressed(key, scanCode, modifiers) ||
                text3.canWrite() ||
                text4.keyPressed(key, scanCode, modifiers) ||
                text4.canWrite() || super.keyPressed(key, scanCode, modifiers);
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
